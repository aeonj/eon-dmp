package eon.hg.fap.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.exception.ResultException;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.flow.meta.*;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FlowProviderImpl implements FlowProvider {

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 基于node的当前工作节点和业务状态获取工作流条件语句
     * 本接口与待办任务进行join连接查询，返回结果需要自行处理
     *
     * @param node     当前节点对象，包括流程名称，节点名称，节点状态，用户ID
     * @param alias    业务表别名
     * @param id_field 业务表ID
     * @return sql连接对象
     */
    @Override
    public SqlRelation getTaskSqlCondition(CurrentNode node, String alias, String id_field) {
        return null;
    }

    /**
     * 组装当前列表查询条件对象
     *
     * @param menu_id 当前菜单功能
     * @param state   当前状态
     * @param qo      条件对象
     * @return
     */
    @Override
    public IQueryObject getTaskHqlCondition(Long menu_id, NodeState state, IQueryObject qo) {
        return null;
    }

    /**
     * 组装当前列表条件对象
     *
     * @param node 当前节点对象
     * @param qo   条件对象
     * @return
     */
    @Override
    public IQueryObject getTaskHqlCondition(CurrentNode node, IQueryObject qo) {
        return null;
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
    public void doWorkFlowByBusiness(Long menu_id, NodeState state, ActionType actionType, String advice, List records) {

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
    public void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice, List records) {

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
            String taskId = null;
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

                ProcessInstance pi = null;
                if (StrUtil.isNotBlank(node.getFlowId())) {
                    pi = runtimeService.startProcessInstanceById(node.getFlowId(),business_id,mapRec);
                } else if (StrUtil.isNotBlank(node.getFlowName())) {
                    ProcessDefinition pd = getProcessDefinition(node);
                    pi = runtimeService.startProcessInstanceById(pd.getId(),business_id,mapRec);
                } else if (StrUtil.isNotBlank(node.getFlowKey())) {
                    pi = runtimeService.startProcessInstanceByKey(node.getFlowKey(),business_id,mapRec);
                } else {
                    throw new ResultException("CurrentNode流程参数未指定，未能获取流程信息");
                }
            } else {
                if (StrUtil.isNotBlank(task_field) && mapRec.containsKey(task_field)) {
                    taskId = Convert.toStr(mapRec.get(task_field));
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
                    if (ActionType.RECALL.equals(actionType)) {
                        OnlineUser loginUser = SecurityUserHolder.getOnlineUser();
                        List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
                                .processDefinitionId(pd.getId()).processInstanceBusinessKey(busiId)
                                .orderByTaskCreateTime()
                                .asc()
                                .list();
                        String myTaskId = null;
                        HistoricTaskInstance myTask = null;
                        for(HistoricTaskInstance hti : htiList) {
                            if(node.getUserId().equals(hti.getAssignee())) {
                                myTaskId = hti.getId();
                                myTask = hti;
                                break;
                            }
                        }
                        if(null==myTaskId) {
                            throw new ServiceException("该任务非当前用户提交，无法撤回");
                        }
                        taskId = myTaskId;
                    } else {
                        TaskQuery query = taskService.createTaskQuery();
                        query.processInstanceBusinessKey(busiId);

                        query.taskCandidateOrAssigned(SecurityUserHolder.getOnlineUser().getUserid());
                        query.processDefinitionId(pd.getId());
                        query.active();
                        List<Task> tasks = query.list();
                        if (tasks == null || tasks.size() == 0) {
                            throw new ResultException("根据关键字未找到对应流程！");
                        } else {
                            taskId = tasks.get(0).getId();
                        }
                    }

                }
                if (ActionType.NEXT.equals(actionType)) {
                    if (bUpdateVariants) {
                        taskService.complete(taskId, mapRec);
                    } else {
                        taskService.complete(taskId);
                    }
                } else if (ActionType.RECALL.equals(actionType)) {
                    //revoke(node.getFlowId(),busiId);

                    //根据流程id查询代办任务中流程信息
                    //Task currtask = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                    //取回流程接点 当前任务id 取回任务id
                    //callBackProcess(task.getId(),taskId);
                } else if (ActionType.BACK.equals(actionType)) {
                    //退回
                } else if (ActionType.BACK_FIRST.equals(actionType)) {

                } else if (ActionType.DISCARD.equals(actionType)) {

                }

            }
        }
    }

    private ProcessDefinition getProcessDefinition(CurrentNode node) {
        ProcessDefinition pd = null;
        if (StrUtil.isNotBlank(node.getFlowId())) {
            pd = repositoryService.getProcessDefinition(node.getFlowId());
        } else if (StrUtil.isNotBlank(node.getFlowName())) {
            pd = repositoryService.createProcessDefinitionQuery().processDefinitionName(node.getFlowName()).singleResult();
        } else if (StrUtil.isNotBlank(node.getFlowKey())) {
            pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(node.getFlowKey()).singleResult();
        }
        if (pd == null)
            throw new ResultException("未找到对应的流程");
        return pd;
    }

    /**
     * 获取操作日志
     *
     * @param menu_id     菜单ID
     * @param business_id 业务ID
     * @return
     */
    @Override
    public List<TaskVO> loadTaskLogList(Long menu_id, String business_id) {
        return null;
    }

    /**
     * 获取操作日志
     *
     * @param menu_id     菜单ID
     * @param business_id 业务ID
     * @param page        当前页
     * @param limit       页记录数
     * @return
     */
    @Override
    public IPageList loadTaskLogList(Long menu_id, String business_id, int page, int limit) {
        return null;
    }

    /**
     * 获取当前登录用户对应的任务列表
     *
     * @param menu_id     菜单ID
     * @param business_id 业务ID
     * @return
     */
    @Override
    public List<TaskVO> loadTaskInfoList(Long menu_id, String business_id) {
        return null;
    }

    /**
     * 获取当前登录用户对应的任务列表
     *
     * @param menu_id     菜单ID
     * @param business_id 业务ID
     * @param page        当前页
     * @param limit       页记录数
     * @return
     */
    @Override
    public IPageList loadTaskInfoList(Long menu_id, String business_id, int page, int limit) {
        return null;
    }

    public void revoke(String flowId,String objId) {

        Task task = taskService.createTaskQuery().processDefinitionId(flowId).processInstanceBusinessKey(objId).singleResult();
        if(task==null) {
            throw new ServiceException("流程未启动或已执行完成，无法撤回");
        }

        OnlineUser loginUser = SecurityUserHolder.getOnlineUser();
        List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionId(flowId).processInstanceBusinessKey(objId)
                .orderByTaskCreateTime()
                .asc()
                .list();
        String myTaskId = null;
        HistoricTaskInstance myTask = null;
        for(HistoricTaskInstance hti : htiList) {
            if(loginUser.getUserid().equals(hti.getAssignee())) {
                myTaskId = hti.getId();
                myTask = hti;
                break;
            }
        }
        if(null==myTaskId) {
            throw new ServiceException("该任务非当前用户提交，无法撤回");
        }

        String processDefinitionId = myTask.getProcessDefinitionId();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        //变量
//		Map<String, VariableInstance> variables = runtimeService.getVariableInstances(currentTask.getExecutionId());
        String myActivityId = null;
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery()
                .executionId(myTask.getExecutionId()).finished().list();
        for(HistoricActivityInstance hai : haiList) {
            if(myTaskId.equals(hai.getTaskId())) {
                myActivityId = hai.getActivityId();
                break;
            }
        }
        FlowNode myFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(myActivityId);


        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        log.warn("------->> activityId:" + activityId);
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);

        //记录原活动方向
        List<SequenceFlow> oriSequenceFlows = new ArrayList<SequenceFlow>();
        oriSequenceFlows.addAll(flowNode.getOutgoingFlows());

        //清理活动方向
        flowNode.getOutgoingFlows().clear();
        //建立新方向
        List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(flowNode);
        newSequenceFlow.setTargetFlowElement(myFlowNode);
        newSequenceFlowList.add(newSequenceFlow);
        flowNode.setOutgoingFlows(newSequenceFlowList);

        Authentication.setAuthenticatedUserId(loginUser.getUserid());
        taskService.addComment(task.getId(), task.getProcessInstanceId(), "撤回");

        Map<String,Object> currentVariables = new HashMap<String,Object>();
        currentVariables.put("applier", loginUser.getUserid());
        //完成任务
        taskService.complete(task.getId(),currentVariables);
        //恢复原方向
        flowNode.setOutgoingFlows(oriSequenceFlows);
    }

    /**
     * 驳回流程
     *
     * @param taskId
     *            当前任务ID
     * @param activityId
     *            驳回节点ID
     * @param variables
     *            流程存储参数
     * @throws Exception
     */
    public void backProcess(String taskId, String activityId,
                            Map<String, Object> variables) throws Exception {
        if (CommUtil.isEmpty(activityId)) {
            throw new Exception("驳回目标节点ID为空！");
        }

        // 查找所有并行任务节点，同时驳回
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(
                taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
        for (Task task : taskList) {
            commitProcess(task.getId(), variables, activityId);
        }
    }


    /**
     * 取回流程
     *
     * @param taskId
     *            当前任务ID
     * @param activityId
     *            取回节点ID
     * @throws Exception
     */
    public void callBackProcess(String taskId, String activityId)
            throws Exception {
        if (CommUtil.isEmpty(activityId)) {
            throw new Exception("目标节点ID为空！");
        }

        // 查找所有并行任务节点，同时取回
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(
                taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
        for (Task task : taskList) {
            commitProcess(task.getId(), null, activityId);
        }
    }


    /**
     * 清空指定活动节点流向
     *
     * @param flowNode
     *            活动节点
     * @return 节点流向集合
     */
    private List<SequenceFlow> clearTransition(FlowNode flowNode) {
        // 存储当前节点所有流向临时变量
        List<SequenceFlow> oriSequenceFlows = new ArrayList<SequenceFlow>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        oriSequenceFlows.addAll(flowNode.getOutgoingFlows());

        //清理活动方向
        flowNode.getOutgoingFlows().clear();

        return oriSequenceFlows;
    }
    /**
     * 提交流程/流程转向
     * @param taskId
     *            当前任务ID
     * @param variables
     *            流程变量
     * @param activityId
     *            流程转向执行任务节点ID<br>
     *            此参数为空，默认为提交操作
     * @throws Exception
     */
    private void commitProcess(String taskId, Map<String, Object> variables,
                               String activityId) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (CommUtil.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityId, variables);
        }
    }

    /**
     * 中止流程(特权人直接审批通过等)
     *
     * @param taskId
     */
    public void endProcess(String taskId) throws Exception {
        FlowNode endActivity = findActivitiFlowNode(taskId, "end");
        commitProcess(taskId, null, endActivity.getId());
    }


    /**
     * 根据流入任务集合，查询最近一次的流入任务节点
     *
     * @param processInstance
     *            流程实例
     * @param tempList
     *            流入任务集合
     * @return
     */
    private FlowNode filterNewestActivity(ProcessInstance processInstance,
                                              List<FlowNode> tempList) {
        while (tempList.size() > 0) {
            FlowNode activity_1 = tempList.get(0);
            HistoricActivityInstance activityInstance_1 = findHistoricUserTask(
                    processInstance, activity_1.getId());
            if (activityInstance_1 == null) {
                tempList.remove(activity_1);
                continue;
            }

            if (tempList.size() > 1) {
                FlowNode activity_2 = tempList.get(1);
                HistoricActivityInstance activityInstance_2 = findHistoricUserTask(
                        processInstance, activity_2.getId());
                if (activityInstance_2 == null) {
                    tempList.remove(activity_2);
                    continue;
                }

                if (activityInstance_1.getEndTime().before(
                        activityInstance_2.getEndTime())) {
                    tempList.remove(activity_1);
                } else {
                    tempList.remove(activity_2);
                }
            } else {
                break;
            }
        }
        if (tempList.size() > 0) {
            return tempList.get(0);
        }
        return null;
    }


    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     *
     * @param taskId
     *            任务ID
     * @param activityId
     *            活动节点ID <br>
     *            如果为null或""，则默认查询当前活动节点 <br>
     *            如果为"end"，则查询结束节点 <br>
     *
     * @return
     * @throws Exception
     */
    private FlowNode findActivitiFlowNode(String taskId, String activityId)
            throws Exception {
        // 取得任务实例
        Task task = findTaskById(taskId);
        // 取得流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();

        // 获取当前活动节点ID
        if (CommUtil.isEmpty(activityId)) {
            Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
            activityId = execution.getActivityId();
        }else{
            HistoricTaskInstance currTask = historyService
                    .createHistoricTaskInstanceQuery().taskId(activityId)
                    .singleResult();
            activityId = currTask.getTaskDefinitionKey();
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        // 根据流程定义，获取该流程实例的结束节点
//        if (activityId.toUpperCase().equals("END")) {
//            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
//                List<PvmTransition> pvmTransitionList = activityImpl
//                        .getOutgoingTransitions();
//                if (pvmTransitionList.isEmpty()) {
//                    return activityNode;
//                }
//            }
//        }

        // 根据节点ID，获取对应的活动节点
        FlowNode activityNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);

        return activityNode;
    }


    /**
     * 根据当前任务ID，查询可以驳回的任务节点
     *
     * @param taskId
     *            当前任务ID
     */
    public List<FlowNode> findBackAvtivity(String taskId) throws Exception {
        List<FlowNode> rtnList =  iteratorBackActivity(taskId, findActivitiFlowNode(taskId,
                null), new ArrayList<FlowNode>(),
                new ArrayList<FlowNode>());
        return reverList(rtnList);
    }

    /**
     * 查询指定任务节点的最新记录
     *
     * @param processInstance
     *            流程实例
     * @param activityId
     * @return
     */
    private HistoricActivityInstance findHistoricUserTask(
            ProcessInstance processInstance, String activityId) {
        HistoricActivityInstance rtnVal = null;
        // 查询当前流程实例审批结束的历史节点
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery().activityType("userTask")
                .processInstanceId(processInstance.getId()).activityId(
                        activityId).finished()
                .orderByHistoricActivityInstanceEndTime().desc().list();
        if (historicActivityInstances.size() > 0) {
            rtnVal = historicActivityInstances.get(0);
        }

        return rtnVal;
    }

    /**
     * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
     *
     * @param flowNode
     *            当前节点
     * @return
     */
    private String findParallelGatewayId(FlowNode flowNode) {
        List<SequenceFlow> incomingTransitions = flowNode
                .getOutgoingFlows();
        for (SequenceFlow transitionImpl : incomingTransitions) {
            flowNode = (FlowNode)transitionImpl.getTargetFlowElement();
            if (flowNode instanceof ParallelGateway) {// 并行路线
                String gatewayId = flowNode.getId();
                String gatewayType = gatewayId.substring(gatewayId
                        .lastIndexOf("_") + 1);
                if ("END".equals(gatewayType.toUpperCase())) {
                    return gatewayId.substring(0, gatewayId.lastIndexOf("_"))
                            + "_start";
                }
            }
        }
        return null;
    }

    /**
     * 根据任务ID获取流程定义
     *
     * @param taskId
     *            任务ID
     * @return
     */
    public ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId)
                        .getProcessDefinitionId());

        if (processDefinition == null) {
            throw new ResultException("流程定义未找到!");
        }

        return processDefinition;
    }

    /**
     * 根据任务ID获取对应的流程实例
     *
     * @param taskId
     *            任务ID
     * @return
     * @throws Exception
     */
    public ProcessInstance findProcessInstanceByTaskId(String taskId)
            throws Exception {
        // 找到流程实例
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery().processInstanceId(
                        findTaskById(taskId).getProcessInstanceId())
                .singleResult();
        if (processInstance == null) {
            throw new Exception("流程实例未找到!");
        }
        return processInstance;
    }

    /**
     * 根据任务ID获得任务实例
     *
     * @param taskId
     *            任务ID
     * @return
     */
    private TaskEntity findTaskById(String taskId) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(
                taskId).singleResult();
        if (task == null) {
            throw new ResultException("任务实例未找到!");
        }
        return task;
    }


    /**
     * 根据流程实例ID和任务key值查询所有同级任务集合
     *
     * @param processInstanceId
     * @param key
     * @return
     */
    private List<Task> findTaskListByKey(String processInstanceId, String key) {
        return taskService.createTaskQuery().processInstanceId(
                processInstanceId).taskDefinitionKey(key).list();
    }


    /**
     * 迭代循环流程树结构，查询当前节点可驳回的任务节点
     *
     * @param taskId
     *            当前任务ID
     * @param currActivity
     *            当前活动节点
     * @param rtnList
     *            存储回退节点集合
     * @param tempList
     *            临时存储节点集合（存储一次迭代过程中的同级userTask节点）
     * @return 回退节点集合
     */
    private List<FlowNode> iteratorBackActivity(String taskId,
                                                    FlowNode currActivity, List<FlowNode> rtnList,
                                                    List<FlowNode> tempList) throws Exception {
        // 查询流程定义，生成流程树结构
        ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);

        // 当前节点的流入来源
        List<SequenceFlow> incomingTransitions = currActivity.getIncomingFlows();
        // 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
        List<FlowNode> exclusiveGateways = new ArrayList<FlowNode>();
        // 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
        List<FlowNode> parallelGateways = new ArrayList<FlowNode>();
        // 遍历当前节点所有流入路径
        for (SequenceFlow transitionImpl : incomingTransitions) {
            FlowNode activityImpl = (FlowNode) transitionImpl.getSourceFlowElement();
            /**
             * 并行节点配置要求：<br>
             * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
             */
            if (activityImpl instanceof ParallelGateway) {// 并行路线
                String gatewayId = activityImpl.getId();
                String gatewayType = gatewayId.substring(gatewayId
                        .lastIndexOf("_") + 1);
                if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
                    return rtnList;
                } else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
                    parallelGateways.add(activityImpl);
                }
            } else if (activityImpl instanceof StartEvent) {// 开始节点，停止递归
                return rtnList;
            } else if (activityImpl instanceof UserTask) {// 用户任务
                tempList.add(activityImpl);
            } else if (activityImpl instanceof ExclusiveGateway) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
                currActivity = (FlowNode)transitionImpl.getSourceFlowElement();
                exclusiveGateways.add(currActivity);
            }
        }

        /**
         * 迭代条件分支集合，查询对应的userTask节点
         */
        for (FlowNode activityImpl : exclusiveGateways) {
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
        }

        /**
         * 迭代并行集合，查询对应的userTask节点
         */
        for (FlowNode activityImpl : parallelGateways) {
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);
        }

        /**
         * 根据同级userTask集合，过滤最近发生的节点
         */
        currActivity = filterNewestActivity(processInstance, tempList);
        if (currActivity != null) {
            // 查询当前节点的流向是否为并行终点，并获取并行起点ID
            String id = findParallelGatewayId(currActivity);
            if (CommUtil.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
                rtnList.add(currActivity);
            } else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
                currActivity = findActivitiFlowNode(taskId, id);
            }

            // 清空本次迭代临时集合
            tempList.clear();
            // 执行下次迭代
            iteratorBackActivity(taskId, currActivity, rtnList, tempList);
        }
        return rtnList;
    }


    /**
     * 还原指定活动节点流向
     *
     * @param flowNode
     *            活动节点
     * @param oriSequenceFlows
     *            原有节点流向集合
     */
    private void restoreTransition(FlowNode flowNode,
                                   List<SequenceFlow> oriSequenceFlows) {
        // 清空现有流向
        List<SequenceFlow> pvmSequenceFlowList = flowNode
                .getOutgoingFlows();
        pvmSequenceFlowList.clear();
        // 还原以前流向
        for (SequenceFlow pvmSequenceFlow : oriSequenceFlows) {
            pvmSequenceFlowList.add(pvmSequenceFlow);
        }
    }

    /**
     * 反向排序list集合，便于驳回节点按顺序显示
     *
     * @param list
     * @return
     */
    private List<FlowNode> reverList(List<FlowNode> list) {
        List<FlowNode> rtnList = new ArrayList<FlowNode>();
        // 由于迭代出现重复数据，排除重复
        for (int i = list.size(); i > 0; i--) {
            if (!rtnList.contains(list.get(i - 1)))
                rtnList.add(list.get(i - 1));
        }
        return rtnList;
    }

    /**
     * 转办流程
     *
     * @param taskId
     *            当前任务节点ID
     * @param userCode
     *            被转办人Code
     */
    public void transferAssignee(String taskId, String userCode) {
        taskService.setAssignee(taskId, userCode);
    }

    /**
     * 流程转向操作
     *
     * @param taskId
     *            当前任务ID
     * @param activityId
     *            目标节点任务ID
     * @param variables
     *            流程变量
     * @throws Exception
     */
    private void turnTransition(String taskId, String activityId,
                                Map<String, Object> variables) throws Exception {
        // 当前节点
        FlowNode currActivity = findActivitiFlowNode(taskId, null);
        // 清空当前流向
        List<SequenceFlow> SequenceFlows = clearTransition(currActivity);

        // 目标节点
        FlowNode pointActivity = findActivitiFlowNode(taskId, activityId);
        // 设置新流向的目标节点
        List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setId("newSequenceFlowId");
        newSequenceFlow.setSourceFlowElement(currActivity);
        newSequenceFlow.setTargetFlowElement(pointActivity);
        newSequenceFlowList.add(newSequenceFlow);
        currActivity.setOutgoingFlows(newSequenceFlowList);

        // 执行转向任务
        taskService.complete(taskId, variables);

        // 还原以前流向
        restoreTransition(currActivity, SequenceFlows);
    }
}
