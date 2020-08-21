package eon.hg.fap.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.model.task.TaskType;
import com.bstek.uflo.query.HistoryTaskQuery;
import com.bstek.uflo.query.ProcessInstanceQuery;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.*;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.exception.ResultException;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.UfloCurrentTasks;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.IMenuService;
import eon.hg.fap.db.service.IUfloCurrentTasksService;
import eon.hg.fap.flow.meta.*;
import eon.hg.fap.uflo.command.LeaveTaskCommand;
import eon.hg.fap.uflo.command.RecallTaskCommand;
import eon.hg.fap.uflo.interfaces.UserTaskListener;
import eon.hg.fap.uflo.util.UfloUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用流程处理入口
 */
@Service("ufloFlowProviderImpl")
@Transactional
public class FlowProviderImpl implements FlowProvider {
    @Resource(name = ProcessService.BEAN_ID)
    private ProcessService processService;

    @Resource(name = TaskService.BEAN_ID)
    private TaskService taskService;

    @Resource(name = HistoryService.BEAN_ID)
    private HistoryService historyService;

    @Resource(name = CommandService.BEAN_ID)
    private CommandService commandService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUfloCurrentTasksService currentTasksService;

    public ProcessService getProcessService() {
        return processService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public String getTaskSqlCondition(Long menu_id) {
        return null;
    }

    @Override
    public SqlRelation getTaskSqlCondition(CurrentNode node, String alias, String id_field) {
        ProcessDefinition pd = getProcessDefinition(node);
        if (StrUtil.isBlank(node.getNodeName())) {
            throw new ResultException("未找到对应的流程节点");
        }
        SqlRelation sqlRelation = new SqlRelation();
        StringBuilder sqlBuilder = new StringBuilder();
        if (NodeState.ALL.equals(node.getState())) {
            sqlBuilder.append(" and ct.process_id=").append(pd.getId()).append(" and ct.current_node_name='").append(node.getNodeName()).append("' and exists(select 1 from UfloNodeUsers unu where unu.process_instance_id=ct.process_instance_id and unu.node_name=ct.current_node_name and unu.user_id='").append(node.getUserId()).append("')");

            sqlRelation.setColumns(",ct.current_task_id task_id");
            sqlRelation.setJoins(StrUtil.concat(true, " inner join sys_uflo_current_tasks ct on ct.business_id_=", alias, ".", id_field));
            sqlRelation.setConditions(sqlBuilder.toString());
        } else if (NodeState.CHECK.equals(node.getState()) || NodeState.BACK.equals(node.getState()) || NodeState.DISCARD.equals(node.getState())) {
            sqlBuilder.append(" and ct.process_id=").append(pd.getId()).append(" and ct.current_node_name='").append(node.getNodeName()).append("' and ct.current_status_code='")
                    .append(node.getState().getCode()).append("' and exists(select 1 from UfloNodeUsers unu where unu.process_instance_id=ct.process_instance_id and unu.node_name=ct.current_node_name and unu.user_id='").append(node.getUserId()).append("')");

            sqlRelation.setColumns(",ct.current_task_id task_id");
            sqlRelation.setJoins(StrUtil.concat(true, " inner join sys_uflo_current_tasks ct on ct.business_id_=", alias, ".", id_field));
            sqlRelation.setConditions(sqlBuilder.toString());
        } else if (NodeState.UN_CHECK.equals(node.getState()) || NodeState.FROM_BACK.equals(node.getState())) {
            sqlBuilder.append(" and ct.process_id=").append(pd.getId()).append(" and ct.next_node_name='").append(node.getNodeName()).append("' and ct.next_status_code='")
                    .append(node.getState().getCode()).append("' and exists(select 1 from UfloNodeUsers unu where unu.process_instance_id=ct.process_instance_id and unu.node_name=ct.next_node_name and unu.user_id='").append(node.getUserId()).append("')");

            sqlRelation.setColumns(",ct.next_task_id task_id");
            sqlRelation.setJoins(StrUtil.concat(true, " inner join sys_uflo_current_tasks ct on ct.business_id_=", alias, ".", id_field));
            sqlRelation.setConditions(sqlBuilder.toString());
        } else {
            if (NodeState.UN_CHECK.equals(node.getState())) {
                sqlBuilder.append(" and exists(select 1 from uflo_process_instance pi where pi.business_id_=").append(alias).append(".").append(id_field).append(" and task.process_id_=").append(pd.getId()).append("");
                sqlBuilder.append(" and pi.current_node_='").append(node.getNodeName()).append("')");
                sqlBuilder.append(" and task.business_id_=").append(alias).append(".").append(id_field).append(" and task.process_id_=").append(pd.getId()).append("");
                sqlBuilder.append(" and task.node_name_='").append(node.getNodeName()).append("'");

                List<TaskState> states = UfloUtil.getTaskStates(node.getState());
                int size = states.size();
                if (size == 1) {
                    sqlBuilder.append(" and task.state_=").append(states.get(0));
                } else if (size > 1) {
                    int i = 0;
                    sqlBuilder.append(" and (");
                    while (i < size) {
                        TaskState state = states.get(i);
                        if (i == 0) {
                            sqlBuilder.append("task.state_=").append(state);
                        } else {
                            sqlBuilder.append(" or task.state_=").append(state);
                        }
                        i++;
                    }
                    sqlBuilder.append(")");
                }

                sqlBuilder.append(" and exists(select 1 from uflo_task_participator tp where tp.task_id_=task.id_ and tp.user_='").append(node.getUserId()).append("'");

                sqlBuilder.append(" and ct.process_id=").append(pd.getId()).append(" and ct.next_node_name='").append(node.getNodeName()).append("' and ct.next_status_code='")
                        .append(node.getState().getCode()).append("' and exists(select 1 from UfloNodeUsers unu where unu.process_instance_id=ct.process_instance_id and unu.node_name=ct.next_node_name and unu.user_id='").append(node.getUserId()).append("')");

                sqlRelation.setColumns(",ct.next_task_id task_id");
                sqlRelation.setJoins(StrUtil.concat(true, " inner join sys_uflo_current_tasks ct on ct.business_id_=", alias, ".", id_field));
                sqlRelation.setConditions(sqlBuilder.toString());
            } else if (NodeState.CHECK.equals(node.getState())) {
                HistoryTaskQuery historyTaskQuery = historyService.createHistoryTaskQuery();
                historyTaskQuery.nodeName(node.getNodeName());
                historyTaskQuery.assignee(node.getUserId());
                historyTaskQuery.processId(pd.getId());

                ProcessInstanceQuery piQuery = processService.createProcessInstanceQuery();
                piQuery.processId(pd.getId());

                sqlBuilder.append(" and exists(select 1 from sys_uflo_current_tasks ct where ct.business_id_=").append(alias).append(".").append(id_field)
                        .append(" and ct.process_id_=").append(pd.getId())
                        .append(" and ct.current_node_name='").append(node.getNodeName()).append("'")
                        .append(" and ct.current_status_code='").append(node.getState().getCode()).append("'");

//            sqlBuilder.append(" and exists(select 1 from uflo_process_instance pi where pi.business_id_=").append(alias).append(".").append(id_field).append(" and task.process_id_='").append(pd.getId()).append("'");
//            sqlBuilder.append(" and pi.current_node_='").append(node.getNodeName()).append("')");
//            sqlBuilder.append(" and exists(select 1 from uflo_his_task task where task.business_id_=").append(alias).append(".").append(id_field).append(" and task.process_id_='").append(pd.getId()).append("'");
//            sqlBuilder.append(" and task.node_name_='").append(node.getNodeName()).append("'");

                sqlBuilder.append(" and exists(select 1 from uflo_task_participator tp where tp.task_id_=ct.old_task_id and tp.user_='").append(node.getUserId()).append("')");
                sqlRelation.setColumns(",task.task_id");
                sqlRelation.setJoins(StrUtil.concat(true, " inner join uflo_tasks task on task.business_id_=", alias, ".", id_field));
                sqlRelation.setConditions(sqlBuilder.toString());
            }
        }
        return sqlRelation;
    }

    @Override
    public IQueryObject getTaskHqlCondition(Long menu_id, NodeState state, IQueryObject qo) {
        return getTaskHqlCondition(CurrentNode.menuInstance(menuService.getObjById(menu_id)).addState(state), qo);
    }

    @Override
    public IQueryObject getTaskHqlCondition(CurrentNode node, IQueryObject qo) {
        ProcessDefinition pd = getProcessDefinition(node);
        if (StrUtil.isBlank(node.getNodeName())) {
            throw new ResultException("未找到对应的流程节点");
        }
        Dto params = new HashDto();
        params.put("process_id", pd.getId());
        if (NodeState.ALL.equals(node.getState())) {
            params.put("current_node_name", node.getNodeName());
            params.put("user_id", node.getUserId());
            qo.addQuery("exists(select 1 from UfloCurrentTasks ct where obj.id=ct.business_id and ct.process_id=:process_id and ct.current_node_name=:current_node_name and exists(select 1 from UfloNodeUsers unu where unu.process_instance_id=ct.process_instance_id and unu.node_name=ct.current_node_name and unu.user_id=:user_id))", params);
        } else if (NodeState.CHECK.equals(node.getState()) || NodeState.BACK.equals(node.getState()) || NodeState.DISCARD.equals(node.getState())) {
            params.put("current_node_name", node.getNodeName());
            params.put("current_status_code", node.getState().getCode());
            params.put("user_id", node.getUserId());
            qo.addQuery("exists(select 1 from UfloCurrentTasks ct where obj.id=ct.business_id and ct.process_id=:process_id and ct.current_node_name=:current_node_name and ct.current_status_code=:current_status_code and exists(select 1 from UfloNodeUsers unu where unu.process_instance_id=ct.process_instance_id and unu.node_name=ct.current_node_name and unu.user_id=:user_id))", params);
        } else if (NodeState.UN_CHECK.equals(node.getState()) || NodeState.FROM_BACK.equals(node.getState())) {
            params.put("next_node_name", node.getNodeName());
            params.put("next_status_code", node.getState().getCode());
            params.put("user_id", node.getUserId());
            qo.addQuery("exists(select 1 from UfloCurrentTasks ct where obj.id=ct.business_id and ct.process_id=:process_id and ct.next_node_name=:next_node_name and ct.next_status_code=:next_status_code and exists(select 1 from UfloNodeUsers unu where unu.process_instance_id=ct.process_instance_id and unu.node_name=ct.next_node_name and unu.user_id=:user_id))", params);
        } else {
            if (NodeState.UN_CHECK.equals(node.getState())) {
                TaskQuery taskQuery = taskService.createTaskQuery();
                for (TaskState taskState : UfloUtil.getTaskStates(node.getState())) {
                    taskQuery.addTaskState(taskState);
                }
                List<Task> tasks = taskQuery.nodeName(node.getNodeName()).addParticipator(node.getUserId()).addProcessId(pd.getId()).list();
                if (tasks.size() <= 500) {
                    StringBuilder sbIds = new StringBuilder();
                    sbIds.append(tasks.get(0).getBusinessId());
                    for (int i = 1; i < tasks.size(); i++) {
                        sbIds.append(",").append(tasks.get(i).getBusinessId());

                    }
                    qo.addQuery(" and id in (" + sbIds.toString() + ")", null);
                } else {
                    StringBuilder sbOr = new StringBuilder();
                    StringBuilder sbIds = null;
                    int i = 0;
                    while (i < tasks.size()) {
                        if (i % 500 == 0) {
                            if (i == 0) {
                                sbOr.append("id in (");
                            } else {
                                sbOr.append(sbIds.toString()).append(") or id in (");
                            }
                            sbIds = new StringBuilder();
                            sbIds.append(tasks.get(i).getBusinessId());
                        } else {
                            sbIds.append(",").append(tasks.get(i).getBusinessId());
                        }
                        i++;
                    }
                    if (i % 500 != 0) {
                        sbOr.append(sbIds.toString()).append(")");
                    }
                    qo.addQuery(" and (" + sbOr.toString() + ")", null);
                }
            } else {

            }
        }
        return qo;
    }

    /**
     * 录入开始流程
     *
     * @param node        流程节点信息
     * @param business_id 业务ID
     * @param record      业务记录集合
     */
    public void doStartFlow(CurrentNode node, String business_id, Map<String, Object> record) {
        OnlineUser oUser = SecurityUserHolder.getOnlineUser();
        Assert.notNull(oUser);
        StartProcessInfo spi = new StartProcessInfo(oUser.getUserid());
        spi.setBusinessId(business_id);
        spi.setVariables(record);
        ProcessInstance pi = null;
        if (StrUtil.isNotBlank(node.getFlowId())) {
            pi = processService.startProcessById(Convert.toLong(node.getFlowId()), spi);
        } else if (StrUtil.isNotBlank(node.getFlowName())) {
            pi = processService.startProcessByName(node.getFlowName(), spi);
        } else if (StrUtil.isNotBlank(node.getFlowKey())) {
            pi = processService.startProcessByKey(node.getFlowKey(), spi);
        } else {
            throw new ResultException("CurrentNode流程参数未指定，未能获取流程信息");
        }
    }

    /**
     * 通用流程处理
     *
     * @param menu_id    当前菜单ID
     * @param state      当前状态
     * @param actionType 操作类型
     * @param advice     流程处理意见
     * @param records    业务记录列表
     */
    @Override
    public void doWorkFlowByBusiness(Long menu_id, NodeState state, ActionType actionType, String advice,
                                     List records) {
        doWorkFlowByBusiness(CurrentNode.menuInstance(menuService.getObjById(menu_id)).addState(state), actionType, advice, records, "task_id", null, false);
    }

    /**
     * 通用流程处理
     *
     * @param node       流程节点信息
     * @param actionType 操作类型
     * @param advice     流程处理意见
     * @param records    业务记录列表
     */
    @Override
    public void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice,
                                     List records) {
        doWorkFlowByBusiness(node, actionType, advice, records, "task_id", null, false);
    }

    /**
     * 通用流程处理
     *
     * @param node            流程节点信息
     * @param actionType      操作类型
     * @param advice          流程处理意见
     * @param records         业务记录列表
     * @param task_field      业务记录中对应的任务ID字段名
     * @param business_field  业务记录中对应的业务ID字段名，仅records记录集中不存在task_field字段才有用
     * @param bUpdateVariants 是否更新业务记录对应的流程变量
     */
    @Override
    public void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice, List records, String task_field, String business_field, boolean bUpdateVariants) {
        for (int i = 0; i < records.size(); i++) {
            Long taskId = null;
            Map mapRec = new HashMap();
            if (records.get(i) instanceof Long) {
                throw new ResultException("暂不支持业务数据集是Long的类型！");
            } else if (records.get(i) instanceof String) {
                throw new ResultException("暂不支持业务数据集是String的类型！");
            } else if (records.get(i) instanceof Map) {
                mapRec = (Map) records.get(i);
            } else {
                mapRec = BeanUtil.beanToMap(records.get(i));
            }
            Task task = null;
            if (ActionType.INPUT.equals(actionType)) {
                String business_id = null;
                if (StrUtil.isNotBlank(business_field) && mapRec.containsKey(business_field)) {
                    business_id = Convert.toStr(mapRec.get(business_field));
                } else if (mapRec.containsKey("id")) {
                    business_id = Convert.toStr(mapRec.get("id"));
                } else if (mapRec.containsKey("vou_id")) {
                    business_id = Convert.toStr(mapRec.get("vou_id"));
                } else {
                    throw new ResultException("业务数据集流程中所需字段不存在，没有任务ID字段或业务ID字段！");
                }
                doStartFlow(node, business_id, mapRec);
                OnlineUser oUser = SecurityUserHolder.getOnlineUser();
                Assert.notNull(oUser);
                StartProcessInfo spi = new StartProcessInfo(oUser.getUserid());
                spi.setBusinessId(business_id);
                spi.setVariables(mapRec);
                ProcessInstance pi = null;
                if (StrUtil.isNotBlank(node.getFlowId())) {
                    pi = processService.startProcessById(Convert.toLong(node.getFlowId()), spi);
                } else if (StrUtil.isNotBlank(node.getFlowName())) {
                    pi = processService.startProcessByName(node.getFlowName(), spi);
                } else if (StrUtil.isNotBlank(node.getFlowKey())) {
                    pi = processService.startProcessByKey(node.getFlowKey(), spi);
                } else {
                    throw new ResultException("CurrentNode流程参数未指定，未能获取流程信息");
                }
            } else {
                if (StrUtil.isNotBlank(task_field) && mapRec.containsKey(task_field)) {
                    taskId = Convert.toLong(mapRec.get(task_field));
                    task = taskService.getTask(taskId);
                } else {
                    TaskQuery query = taskService.createTaskQuery();
                    String busiId = "-1";
                    if (mapRec.containsKey(business_field)) {
                        busiId = Convert.toStr(mapRec.get(business_field));
                    } else if (mapRec.containsKey("id")) {
                        busiId = Convert.toStr(mapRec.get("id"));
                    } else if (mapRec.containsKey("vou_id")) {
                        busiId = Convert.toStr(mapRec.get("vou_id"));
                    } else {
                        throw new ResultException("业务数据集流程中所需字段不存在，没有任务ID字段或业务ID字段！");
                    }
                    query.businessId(busiId);
                    ProcessDefinition pd = getProcessDefinition(node);
                    query.addParticipator(SecurityUserHolder.getOnlineUser().getUserid());
                    query.addProcessId(pd.getId());
                    if (ActionType.RECALL.equals(actionType)) {
                        query.addTaskState(TaskState.Completed);
                        query.addOrderDesc("createDate");
                    } else {
                        query.addTaskState(TaskState.Created);
                        query.addTaskState(TaskState.InProgress);
                        query.addTaskState(TaskState.Ready);
                        query.addTaskState(TaskState.Suspended);
                        query.addTaskState(TaskState.Reserved);
                    }
                    List<Task> tasks = query.list();
                    if (tasks == null || tasks.size() == 0) {
                        throw new ResultException("根据关键字未找到对应流程！");
                    } else {
                        task = tasks.get(0);
                        taskId = task.getId();
                    }
                }
                if (ActionType.NEXT.equals(actionType)) {
                    if (TaskType.Participative.equals(task.getType())) {
                        taskService.claim(taskId, SecurityUserHolder.getOnlineUser().getUserid());
                    }
                    taskService.start(taskId);
                    if (bUpdateVariants) {
                        taskService.complete(taskId, mapRec, new TaskOpinion(advice));
                    } else {
                        taskService.complete(taskId, new TaskOpinion(advice));
                    }
                } else if (ActionType.RECALL.equals(actionType)) {
                    //撤销
                    if (task.getState().equals(TaskState.InProgress)) {
                        throw new IllegalStateException("Task " + task.getTaskName() + " state is InProgress,can not be withdraw.");
                    }
                    if (bUpdateVariants) {
                        commandService.executeCommand(new RecallTaskCommand(task, mapRec, new TaskOpinion(advice)));
                    } else {
                        commandService.executeCommand(new RecallTaskCommand(task, null, new TaskOpinion(advice)));
                    }
                } else if (ActionType.BACK.equals(actionType)) {
                    //退回
                    if (bUpdateVariants) {
                        taskService.rollback(task, task.getPrevTask(), mapRec, new TaskOpinion(advice));
                    } else {
                        taskService.rollback(task, task.getPrevTask(), null, new TaskOpinion(advice));
                    }
                } else if (ActionType.BACK_FIRST.equals(actionType)) {
                    ProcessDefinition pd = processService.getProcessById(task.getProcessId());
                    String nodeName = pd.getStartNode().getName();
                    //退回
                    if (bUpdateVariants) {
                        taskService.rollback(task, nodeName, mapRec, new TaskOpinion(advice));
                    } else {
                        taskService.rollback(task, nodeName, null, new TaskOpinion(advice));
                    }
                } else if (ActionType.DISCARD.equals(actionType)) {
                    taskService.cancelTask(taskId, new TaskOpinion(advice));
                }
                commandService.executeCommand(new LeaveTaskCommand(task, actionType));
            }
        }
    }

    /**
     * 通过任务ID进行流程处理
     *
     * @param actionType 操作类型
     * @param advice     处理意见
     * @param taskIdList 任务ID集合
     */
    public void doWorkFlowByTask(ActionType actionType, String advice,
                                 List taskIdList) {
        for (int i = 0; i < taskIdList.size(); i++) {
            Long taskId = Convert.toLong(taskIdList.get(i));
            Task task = taskService.getTask(taskId);
            if (ActionType.NEXT.equals(actionType)) {
                taskService.start(taskId);
                taskService.complete(taskId, new TaskOpinion(advice));
            } else if (ActionType.RECALL.equals(actionType)) {
                //撤销
                if (!taskService.canWithdraw(task)) {
                    throw new ResultException("当前任务不能回退到上一任务节点");
                }
                taskService.withdraw(taskId, new TaskOpinion(advice));
            } else if (ActionType.BACK.equals(actionType)) {
                //退回
                taskService.rollback(task, task.getPrevTask(), null, new TaskOpinion(advice));
            } else if (ActionType.DISCARD.equals(actionType)) {
                taskService.cancelTask(taskId, new TaskOpinion(advice));
            }
        }
    }

    /**
     * @param record
     */
    public void doUpdateFlowVariants(Map<String, Object> record) {
        //SaveProcessInstanceVariablesCommand中第69行感觉有多余

    }

    @Override
    public List<TaskVO> loadTaskLogList(Long menu_id, String business_id) {
        HistoryTaskQuery taskQuery = historyService.createHistoryTaskQuery();
        CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
        ProcessDefinition pd = getProcessDefinition(node);
        taskQuery.processId(pd.getId());
        taskQuery.businessId(business_id);
        taskQuery.addOrderDesc("endDate");
        return UfloUtil.convertTaskLog(taskQuery.list());
    }

    @Override
    public IPageList loadTaskLogList(Long menu_id, String business_id, int page, int limit) {
        HistoryTaskQuery taskQuery = historyService.createHistoryTaskQuery();
        CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
        ProcessDefinition pd = getProcessDefinition(node);
        taskQuery.processId(pd.getId());
        taskQuery.businessId(business_id);
        taskQuery.addOrderDesc("endDate").page(page, limit);
        return UfloUtil.getPageList(taskQuery.list(), taskQuery.count(), page, limit);
    }

    @Override
    public List<TaskVO> loadTaskInfoList(Long menu_id, String business_id) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
        ProcessDefinition pd = getProcessDefinition(node);
        taskQuery.processId(pd.getId());
        taskQuery.businessId(business_id);
        taskQuery.addOrderDesc("endDate");
        return UfloUtil.convertTaskInfo(taskQuery.list());
    }

    @Override
    public IPageList loadTaskInfoList(Long menu_id, String business_id, int page, int limit) {
        HistoryTaskQuery taskQuery = historyService.createHistoryTaskQuery();
        CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
        ProcessDefinition pd = getProcessDefinition(node);
        taskQuery.processId(pd.getId());
        taskQuery.businessId(business_id);
        taskQuery.addOrderDesc("endDate").page(page, limit);
        return UfloUtil.getPageList(taskQuery.list(), taskQuery.count(), page, limit);
    }

    private ProcessDefinition getProcessDefinition(CurrentNode node) {
        ProcessDefinition pd = null;
        if (StrUtil.isNotBlank(node.getFlowId())) {
            pd = processService.getProcessById(Convert.toLong(node.getFlowId()));
        } else if (StrUtil.isNotBlank(node.getFlowName())) {
            pd = processService.getProcessByName(node.getFlowName());
        } else if (StrUtil.isNotBlank(node.getFlowKey())) {
            pd = processService.getProcessByKey(node.getFlowKey());
        }
        if (pd == null)
            throw new ResultException("未找到对应的流程");
        return pd;
    }

    private Collection<UserTaskListener> fetchUserTaskListener(Context context) {
        return context.getApplicationContext().getBeansOfType(UserTaskListener.class).values();
    }

}
