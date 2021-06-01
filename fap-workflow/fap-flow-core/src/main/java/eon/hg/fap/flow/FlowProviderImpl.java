package eon.hg.fap.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.exception.ResultException;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.IDataRightService;
import eon.hg.fap.db.service.IMenuService;
import eon.hg.fap.db.service.IUserService;
import eon.hg.fap.flow.command.CommandService;
import eon.hg.fap.flow.command.impl.EditTaskCommand;
import eon.hg.fap.flow.command.impl.LeaveTaskCommand;
import eon.hg.fap.flow.command.impl.RecallTaskCommand;
import eon.hg.fap.flow.meta.*;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.model.task.TaskType;
import eon.hg.fap.flow.process.node.Node;
import eon.hg.fap.flow.process.node.TaskNode;
import eon.hg.fap.flow.query.HistoryTaskQuery;
import eon.hg.fap.flow.query.ProcessInstanceQuery;
import eon.hg.fap.flow.query.TaskQuery;
import eon.hg.fap.flow.service.*;
import eon.hg.fap.flow.utils.FlowTaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用流程处理入口
 */
@Service("eonFlowProviderImpl")
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
    private IDataRightService dataRightService;

    @Autowired
    private IUserService userService;

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
    public SqlRelation getTaskSqlCondition(Long menu_id, NodeState state, String alias, String id_field) {
        return getTaskSqlCondition(CurrentNode.menuInstance(menuService.getObjById(menu_id)).addState(state), alias, id_field);
    }

    @Override
    public SqlRelation getTaskSqlCondition(CurrentNode node, String alias, String id_field) {
        ProcessDefinition pd = getProcessDefinition(node);
        if (StrUtil.isBlank(node.getNodeName())) {
            throw new ResultException("未找到对应的流程节点");
        }
        OnlineUser oUser = SecurityUserHolder.getOnlineUser();
        Assert.notNull(oUser);
        SqlRelation sqlRelation = new SqlRelation();
        StringBuilder sqlBuilder = new StringBuilder();
        if (NodeState.ALL.equals(node.getState())) {
            //sqlBuilder.append(" and ct.process_id_=").append(pd.getId()).append(" and ct.current_node_name_='").append(node.getNodeName()).append("' and exists(select 1 from wf_node_users unu where unu.process_instance_id_=ct.process_instance_id_ and unu.node_name_=ct.current_node_name_ and unu.user_id_='").append(oUser.getUserid()).append("')");
            sqlBuilder.append(" and ct.process_id_=").append(pd.getId());//.append(" and ct.current_node_name_='").append(node.getNodeName()).append("' ");
            sqlBuilder.append(dataRightService.getDataRightSql(alias));
            sqlRelation.setColumns(",ct.current_task_id_ task_id");
            sqlRelation.setJoins(StrUtil.concat(true, " inner join wf_task_business ct on ct.business_id_=", alias, ".", id_field));
            sqlRelation.setConditions(sqlBuilder.toString());
        } else if (NodeState.CHECK.equals(node.getState()) || NodeState.BACK.equals(node.getState()) || NodeState.DISCARD.equals(node.getState())) {
            sqlBuilder.append(" and ct.process_id_=").append(pd.getId()).append(" and ct.current_node_name_='").append(node.getNodeName()).append("' and ct.current_status_code_='").append(node.getState().getCode()).append("'");
            Node taskNode = pd.getNode(node.getNodeName());
            if (taskNode instanceof TaskNode) {
                TaskNode tn = (TaskNode) taskNode;
                if (TaskType.Countersign.equals(tn.getTaskType())) {
                    sqlBuilder.append(" and exists(select 1 from wf_task unu where unu.id_=ct.current_task_id_ and unu.owner_='").append(oUser.getUserid()).append("')");
                } else {
                    sqlBuilder.append(" and exists(select 1 from wf_his_task unu where unu.task_id_=ct.current_task_id_ and unu.process_id_=ct.process_id_ and unu.assignee_='").append(oUser.getUserid()).append("')");
//                } else {
//                    sqlBuilder.append(" and exists(select 1 from wf_task_participator unu where unu.task_id_=ct.current_task_id_ and unu.user_='").append(oUser.getUserid()).append("')");
                }
            } else {
                sqlBuilder.append(" and 1=0");
            }

            sqlRelation.setColumns(",ct.current_task_id_ task_id");
            sqlRelation.setJoins(StrUtil.concat(true, " inner join wf_task_business ct on ct.business_id_=", alias, ".", id_field));
            sqlRelation.setConditions(sqlBuilder.toString());
//            if (taskNode instanceof TaskNode) {
//                TaskNode tn = (TaskNode) taskNode;
//                if (TaskType.Countersign.equals(tn.getTaskType())) {
//                    sqlBuilder.append(" and exists(select 1 from wf_task ct where ").append(alias).append(".").append(id_field).append("=ct.business_id_ and ct.process_id_=").append(pd.getId()).append(" and ct.node_name_='").append(node.getNodeName()).append("' and ct.state_ in ('Completed') and ct.type_!='Participative' and ct.owner_='").append(oUser.getUserid()).append("')");
//                } else {
//                    sqlBuilder.append(" and exists(select 1 from wf_task ct where ").append(alias).append(".").append(id_field).append("=ct.business_id_ and ct.process_id_=").append(pd.getId()).append(" and ct.node_name_='").append(node.getNodeName()).append("' and ct.state_ in ('Completed') and ct.type_='Participative' and exists(select 1 from wf_task_participator tp where tp.task_id_=ct.id_ and tp.user_='").append(oUser.getUserid()).append("'))");
//                }
//            }
//            sqlRelation.setConditions(sqlBuilder.toString());
        } else if (NodeState.UN_CHECK.equals(node.getState()) || NodeState.FROM_BACK.equals(node.getState())) {
            sqlBuilder.append(" and ct.process_id_=").append(pd.getId()).append(" and ct.next_node_name_='").append(node.getNodeName()).append("' and ct.next_status_code_='").append(node.getState().getCode()).append("'");
            Node taskNode = pd.getNode(node.getNodeName());
            if (taskNode instanceof TaskNode) {
                TaskNode tn = (TaskNode) taskNode;
                if (TaskType.Countersign.equals(tn.getTaskType())) {
                    sqlBuilder.append(" and exists(select 1 from wf_task unu where unu.id_=ct.next_task_id_ and unu.owner_='").append(oUser.getUserid()).append("')");
//                } else if (TaskType.Normal.equals(tn.getTaskType())) {
//                    sqlBuilder.append(" and exists(select 1 from wf_task unu where unu.id_=ct.next_task_id_ and unu.assignee_='").append(oUser.getUserid()).append("')");
                } else {
                    sqlBuilder.append(" and exists(select 1 from wf_task_participator unu where unu.task_id_=ct.next_task_id_ and unu.user_='").append(oUser.getUserid()).append("')");
                }
            } else {
                sqlBuilder.append(" and 1=0");
            }

            sqlRelation.setColumns(",ct.next_task_id_ task_id");
            sqlRelation.setJoins(StrUtil.concat(true, " inner join wf_task_business ct on ct.business_id_=", alias, ".", id_field));
            sqlRelation.setConditions(sqlBuilder.toString());

//            if (taskNode instanceof TaskNode) {
//                TaskNode tn = (TaskNode) taskNode;
//                if (TaskType.Countersign.equals(tn.getTaskType())) {
//                    sqlBuilder.append(" and exists(select 1 from wf_task ct where ").append(alias).append(".").append(id_field).append("=ct.business_id_ and ct.process_id_=").append(pd.getId()).append(" and ct.node_name_='").append(node.getNodeName()).append("' and ct.state_ in ('Created','Ready','InProgress') and ct.owner_='").append(oUser.getUserid()).append("')");
//                } else {
//                    sqlBuilder.append(" and exists(select 1 from wf_task ct where ").append(alias).append(".").append(id_field).append("=ct.business_id_ and ct.process_id_=").append(pd.getId()).append(" and ct.node_name_='").append(node.getNodeName()).append("' and ct.state_ in ('Created','Ready','InProgress') and exists(select 1 from wf_task_participator tp where tp.task_id_=ct.id_ and tp.user_='").append(oUser.getUserid()).append("'))");
//                }
//            }
//            sqlRelation.setConditions(sqlBuilder.toString());
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
        OnlineUser oUser = SecurityUserHolder.getOnlineUser();
        Assert.notNull(oUser);
        Assert.notNull(oUser.getUserid());
        Dto params = new HashDto();
        params.put("processId", pd.getId());
        if (NodeState.ALL.equals(node.getState())) {
            params.put("currentNodeName", node.getNodeName());
            params.put("userId", oUser.getUserid());
            //qo.addQuery("exists(select 1 from TaskBusiness ct where obj.id=ct.businessId and ct.processId=:processId and ct.currentNodeName=:currentNodeName and exists(select 1 from NodeUsers unu where unu.processInstanceId=ct.processInstanceId and unu.nodeName=ct.currentNodeName and unu.userId=:userId))", params);
            //qo.addQuery("exists(select 1 from TaskBusiness ct where obj.id=ct.businessId and ct.processId=:processId and ct.currentNodeName=:currentNodeName)", params);
            qo.addQuery(dataRightService.getDataRightHql(qo.getAlias()),null);
        } else if (NodeState.CHECK.equals(node.getState()) || NodeState.BACK.equals(node.getState()) || NodeState.DISCARD.equals(node.getState())) {
            params.put("currentNodeName", node.getNodeName());
            params.put("currentStatusCode", node.getState().getCode());
            params.put("user_id", oUser.getUserid());
            params.put("state",FlowTaskUtils.getTaskStates(node.getState()));
            //基于nodeUsers生成的流程条件
            //qo.addQuery("exists(select 1 from TaskBusiness ct where obj.id=ct.businessId and ct.processId=:processId and ct.currentNodeName=:currentNodeName and ct.currentStatusCode=:currentStatusCode and exists(select 1 from NodeUsers unu where unu.processInstanceId=ct.processInstanceId and unu.nodeName=ct.currentNodeName and unu.userId=:userId))", params);

            //基于TaskBusines和TaskParticipator生成的流程条件
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("exists(select 1 from TaskBusiness ct where obj.id=ct.businessId and ct.processId=:processId and ct.currentNodeName=:currentNodeName and ct.currentStatusCode=:currentStatusCode");
            Node taskNode = pd.getNode(node.getNodeName());
            if (taskNode instanceof TaskNode) {
                TaskNode tn = (TaskNode) taskNode;
                if (TaskType.Countersign.equals(tn.getTaskType())) {
                    sqlBuilder.append(" and exists(select 1 from Task unu where unu.id=ct.currentTaskId and unu.owner=:userId)");
                } else {
                    sqlBuilder.append(" and exists(select 1 from HistoryTask unu where unu.taskId=ct.currentTaskId and unu.processId=ct.processId and unu.assignee=:userId)");
//                } else {
//                    sqlBuilder.append(" and exists(select 1 from TaskParticipator unu where unu.taskId_=ct.currentTaskId and unu.user=:userId)");
                }
            } else {
                sqlBuilder.append(" and 1=0");
            }
            sqlBuilder.append(")");
            qo.addQuery(sqlBuilder.toString(),params);
            //基于Task和TaskParticipator生成的流程条件
//            Node taskNode = pd.getNode(node.getNodeName());
//            if (taskNode instanceof TaskNode) {
//                TaskNode tn = (TaskNode) taskNode;
//                if (TaskType.Countersign.equals(tn.getTaskType())) {
//                    qo.addQuery("exists(select 1 from Task ct where obj.id=ct.businessId and ct.processId=:processId and ct.nodeName=:nextNodeName and ct.prevTask in (:state) and ct.owner=:userId)",params);
//                } else {
//                    qo.addQuery("exists(select 1 from Task ct where obj.id=ct.businessId and ct.processId=:processId and ct.nodeName=:nextNodeName and ct.state in (:state) and exists(select 1 from TaskParticipator tp where tp.taskId=ct.Id and tp.user=:userId))",params);
//                }
//            }
        } else if (NodeState.UN_CHECK.equals(node.getState()) || NodeState.FROM_BACK.equals(node.getState())) {
            params.put("nextNodeName", node.getNodeName());
            params.put("nextStatusCode", node.getState().getCode());
            params.put("userId", oUser.getUserid());
            params.put("state",FlowTaskUtils.getTaskStates(node.getState()));
            //基于nodeUsers生成的流程条件
            //qo.addQuery("exists(select 1 from TaskBusiness ct where obj.id=ct.businessId and ct.processId=:processId and ct.nextNodeName=:nextNodeName and ct.nextStatusCode=:nextStatusCode and exists(select 1 from NodeUsers unu where unu.processInstanceId=ct.processInstanceId and unu.nodeName=ct.nextNodeName and unu.userId=:userId))",params);

            //基于TaskBusines和TaskParticipator生成的流程条件
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("exists(select 1 from TaskBusiness ct where obj.id=ct.businessId and ct.processId=:processId and ct.nextNodeName=:nextNodeName and ct.nextStatusCode=:nextStatusCode");
            Node taskNode = pd.getNode(node.getNodeName());
            if (taskNode instanceof TaskNode) {
                TaskNode tn = (TaskNode) taskNode;
                if (TaskType.Countersign.equals(tn.getTaskType())) {
                    sqlBuilder.append(" and exists(select 1 from Task unu where unu.id=ct.nextTaskId and unu.owner=:userId)");
//                } else if (TaskType.Normal.equals(tn.getTaskType())) {
//                    sqlBuilder.append(" and exists(select 1 from Task unu where unu.id=ct.nextTaskId and unu.assignee=:userId)");
                } else {
                    sqlBuilder.append(" and exists(select 1 from TaskParticipator unu where unu.taskId_=ct.nextTaskId and unu.user=:userId)");
                }
            } else {
                sqlBuilder.append(" and 1=0");
            }
            sqlBuilder.append(")");
            qo.addQuery(sqlBuilder.toString(),params);
            //基于Task生成的流程条件
//            Node taskNode = pd.getNode(node.getNodeName());
//            if (taskNode instanceof TaskNode) {
//                TaskNode tn = (TaskNode) taskNode;
//                if (TaskType.Countersign.equals(tn.getTaskType())) {
//                    qo.addQuery("exists(select 1 from Task ct where obj.id=ct.businessId and ct.processId=:processId and ct.nodeName=:nextNodeName and ct.state in (:state) and ct.owner=:userId)",params);
//                } else {
//                    qo.addQuery("exists(select 1 from Task ct where obj.id=ct.businessId and ct.processId=:processId and ct.nodeName=:nextNodeName and ct.state in (:state) and exists(select 1 from TaskParticipator tp where tp.taskId=ct.Id and tp.user=:userId))",params);
//                }
//            }

        } else {
            if (NodeState.UN_CHECK.equals(node.getState())) {
                TaskQuery taskQuery = taskService.createTaskQuery();
                for (TaskState taskState : FlowTaskUtils.getTaskStates(node.getState())) {
                    taskQuery.addTaskState(taskState);
                }
                List<Task> tasks = taskQuery.nodeName(node.getNodeName()).addParticipator(oUser.getUserid()).addProcessId(pd.getId()).list();
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
     * @param menu_id    当前菜单ID
     * @param actionType 操作类型
     * @param advice     流程处理意见
     * @param records    业务记录列表
     */
    @Override
    public void doWorkFlowByBusiness(Long menu_id, ActionType actionType, String advice,
                                     List records) {
        doWorkFlowByBusiness(CurrentNode.menuInstance(menuService.getObjById(menu_id)), actionType, advice, records, "task_id", null, false);
    }

    /**
     * 通用流程处理
     * @param menu_id 当前菜单ID
     * @param actionType 操作类型
     * @param advice 流程处理意见
     * @param records 业务记录列表（允许map集合，对象集合，业务ID集合）
     * @param variables 流程中需要的变量，注意涉及到权限的业务表字段建议指定,默认records集合全加
     */
    @Override
    public void doWorkFlowByBusiness(Long menu_id, ActionType actionType, String advice,
                                      List records,String[] variables) {
        doWorkFlowByBusiness(CurrentNode.menuInstance(menuService.getObjById(menu_id)), actionType, advice, records, variables,"task_id", null, false);
    };

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
    public void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice, List records,
                                     String task_field, String business_field, boolean bUpdateVariants) {
        doWorkFlowByBusiness(node,actionType,advice,records,null,task_field,business_field,bUpdateVariants);
    }

    /**
     * 通用流程处理
     *
     * @param node            流程节点信息
     * @param actionType      操作类型
     * @param advice          流程处理意见
     * @param records         业务记录列表
     * @param variables       流程中需要的变量，注意涉及到权限的业务表字段建议指定,默认records集合全加
     * @param task_field      业务记录中对应的任务ID字段名
     * @param business_field  业务记录中对应的业务ID字段名，仅records记录集中不存在task_field字段才有用
     * @param bUpdateVariants 是否更新业务记录对应的流程变量
     */
    public void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice, List records, String[] variables,
                                     String task_field, String business_field, boolean bUpdateVariants) {
        for (int i = 0; i < records.size(); i++) {
            Long taskId = null;
            Map mapRec = new HashMap();
            if (records.get(i) instanceof Long) {
                mapRec.put("id",records.get(i));
            } else if (records.get(i) instanceof String) {
                mapRec.put("id",records.get(i));
            } else if (records.get(i) instanceof Map) {
                if (variables==null || variables.length==0) {
                    mapRec = (Map) records.get(i);
                } else {
                    Map<String,Object> mapTemp = (Map) records.get(i);
                    for (String key :mapTemp.keySet()) {
                        if (ArrayUtil.containsIgnoreCase(variables,key)) {
                            mapRec.put(key,mapTemp.get(key));
                        }
                    };
                }
            } else {
                if (variables==null || variables.length==0) {
                    mapRec = BeanUtil.beanToMap(records.get(i));
                } else {
                    mapRec = BeanUtil.beanToMap(records.get(i),new LinkedHashMap<String, Object>(),false,
                            (String key) -> ArrayUtil.containsIgnoreCase(variables,key) ? key : null);
                }
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
                    ProcessDefinition pd = getProcessDefinition(node);
                    Assert.notNull(pd);
                    if (ActionType.NEXT.equals(actionType)) {
                        //没有流程实例将自动创建
                        ProcessInstanceQuery piQuery = processService.createProcessInstanceQuery();
                        piQuery.businessId(busiId);
                        piQuery.processId(pd.getId());
                        List<ProcessInstance> piList = piQuery.list();
                        if (piList == null || piList.size() == 0) {
                            doWorkFlowByBusiness(node, ActionType.INPUT, advice,records,task_field,business_field,bUpdateVariants);
                        }
                    }
                    TaskQuery query = taskService.createTaskQuery();
                    query.businessId(busiId);
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
                    task.setAssignee(SecurityUserHolder.getOnlineUser().getUserid());
                    if (bUpdateVariants) {
                        commandService.executeCommand(new RecallTaskCommand(task, mapRec, new TaskOpinion(advice)));
                    } else {
                        commandService.executeCommand(new RecallTaskCommand(task, null, new TaskOpinion(advice)));
                    }
                } else if (ActionType.BACK.equals(actionType)) {
                    task.setAssignee(SecurityUserHolder.getOnlineUser().getUserid());
                    //退回
                    if (task.getState().equals(TaskState.InProgress) || task.getState().equals(TaskState.Rollback)) {
                        throw new IllegalStateException("Task " + task.getTaskName() + " state is InProgress,can not be rollback.");
                    }
                    //获取前一流程节点（取complete状态的task）
                    List<Task> listTask = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).nodeName(task.getNodeName()).addOrderAsc("endDate").list();
                    if (listTask.size()>0) {
                        Task backTask = listTask.get(0);
                        if (bUpdateVariants) {
                            taskService.rollback(task, backTask.getPrevTask(), mapRec, new TaskOpinion(advice));
                        } else {
                            taskService.rollback(task, backTask.getPrevTask(), null, new TaskOpinion(advice));
                        }
                    } else {
                        throw new IllegalStateException("Task " + task.getTaskName() + " not found.");
                    }
                } else if (ActionType.BACK_FIRST.equals(actionType)) {
                    task.setAssignee(SecurityUserHolder.getOnlineUser().getUserid());
                    ProcessDefinition pd = processService.getProcessById(task.getProcessId());
                    String nodeName = pd.getStartNode().getName();
                    //退回
                    if (bUpdateVariants) {
                        taskService.rollback(task, nodeName, mapRec, new TaskOpinion(advice));
                    } else {
                        taskService.rollback(task, nodeName, null, new TaskOpinion(advice));
                    }
                } else if (ActionType.DISCARD.equals(actionType)) {
                    if (TaskType.Participative.equals(task.getType())) {
                        taskService.claim(taskId, SecurityUserHolder.getOnlineUser().getUserid());
                    }
                    taskService.cancelTask(taskId, new TaskOpinion(advice));
                } else if (ActionType.EDIT.equals(actionType)) {
                    if (bUpdateVariants) {
                        commandService.executeCommand(new EditTaskCommand(task, mapRec));
                    } else {
                        commandService.executeCommand(new EditTaskCommand(task, null));
                    }
                } else if (ActionType.DELETE.equals(actionType)) {
                    processService.deleteProcessInstanceById(task.getProcessInstanceId());
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
                commandService.executeCommand(new RecallTaskCommand(task, null, new TaskOpinion(advice)));
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
        return FlowTaskUtils.convertTaskLog(taskQuery.list(),(value)->{
            User user = userService.getObjById(Convert.toLong(value));
            return user.getTrueName();
        });
    }

    @Override
    public IPageList loadTaskLogList(Long menu_id, String business_id, int page, int limit) {
        HistoryTaskQuery taskQuery = historyService.createHistoryTaskQuery();
        CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
        ProcessDefinition pd = getProcessDefinition(node);
        taskQuery.processId(pd.getId());
        taskQuery.businessId(business_id);
        taskQuery.addOrderDesc("endDate").page((page-1)*limit, limit);
        List taskList =FlowTaskUtils.convertTaskLog(taskQuery.list(),(value)->{
            User user = userService.getObjById(Convert.toLong(value));
            return user.getTrueName();
        });
        return FlowTaskUtils.getPageList(taskList, taskQuery.count(), page, limit);
    }

    @Override
    public List<TaskVO> loadTaskInfoList(Long menu_id, String business_id) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
        ProcessDefinition pd = getProcessDefinition(node);
        taskQuery.processId(pd.getId());
        taskQuery.businessId(business_id);
        taskQuery.addOrderDesc("endDate");
        return FlowTaskUtils.convertTaskInfo(taskQuery.list());
    }

    @Override
    public IPageList loadTaskInfoList(Long menu_id, String business_id, int page, int limit) {
        HistoryTaskQuery taskQuery = historyService.createHistoryTaskQuery();
        CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
        ProcessDefinition pd = getProcessDefinition(node);
        taskQuery.processId(pd.getId());
        taskQuery.businessId(business_id);
        taskQuery.addOrderDesc("endDate").page(page, limit);
        return FlowTaskUtils.getPageList(taskQuery.list(), taskQuery.count(), page, limit);
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

}
