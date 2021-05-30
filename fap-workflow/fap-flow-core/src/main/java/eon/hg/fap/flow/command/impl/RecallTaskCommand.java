package eon.hg.fap.flow.command.impl;

import eon.hg.fap.flow.command.Command;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.meta.ActionType;
import eon.hg.fap.flow.model.HistoryTask;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskParticipator;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.model.task.TaskType;
import eon.hg.fap.flow.process.node.Node;
import eon.hg.fap.flow.service.SchedulerService;
import eon.hg.fap.flow.service.TaskOpinion;
import eon.hg.fap.flow.service.TaskService;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务撤销，适用于已办任务操作人执行撤回任务
 * 本接口区别于WithDrawTaskCommand接口，主要是任务操作对象不一样
 * 该类采取删除下一节点的任务，撤回到待办状态，但保留历史Task记录（单独处理），同时触发当前节点进入事件
 * @author eonook
 */
public class RecallTaskCommand implements Command<Task> {

    private Map<String, Object> variables;
    private Task task;
    private TaskOpinion opinion;
    public RecallTaskCommand(Task task,Map<String, Object> variables,TaskOpinion opinion){
        this.variables=variables;
        this.opinion=opinion;
        this.task=task;
    }
    public Task execute(Context context) {
        TaskService taskService=context.getTaskService();

        String nodeName = task.getNodeName();
        ProcessDefinition pd = context.getProcessService().getProcessById(task.getProcessId());
        ProcessInstance pi = context.getProcessService().getProcessInstanceById(task.getProcessInstanceId());

        Session session = context.getSession();
        Query query = session.createQuery("from "+Task.class.getName()+" where prevTask=:prevTask and processInstanceId=:processInstanceId");
        query.setString("prevTask",nodeName);
        query.setLong("processInstanceId",task.getProcessInstanceId());
        List<Task> taskList = query.list();
        int prev_count = 0;
        for (Task nextTask : taskList) {
            if (TaskState.Reserved.equals(nextTask.getState())
                    || TaskState.Created.equals(nextTask.getState())
                    || TaskState.Ready.equals(nextTask.getState())
                    || TaskState.Suspended.equals(nextTask.getState())) {
                if(nextTask.getType().equals(TaskType.Participative) || nextTask.getType().equals(TaskType.Normal)){
                    session.createQuery("delete "+TaskParticipator.class.getName()+" where taskId=:taskId").setLong("taskId", nextTask.getId()).executeUpdate();
                }
                session.delete(nextTask);
                SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
                schedulerService.removeReminderJob(nextTask);
                prev_count++;
            }
        }
        if (prev_count!=1) {
            throw new IllegalArgumentException("Task "+task.getTaskName()+" can not recall to "+task.getPrevTask()+" node.");
        }
        pi.setCurrentTask(task.getNodeName());
        pi.setCurrentNode(task.getNodeName());
        task.setState(TaskState.Recall);
        if(variables!=null && variables.size()>0){
            context.getExpressionContext().addContextVariables(pi, variables);
            context.getCommandService().executeCommand(new SaveProcessInstanceVariablesCommand(pi, variables));
        }
        String old_opinion = task.getOpinion();
        if(opinion!=null){
            task.setOpinion(opinion.getOpinion());
        }
        task.setCreateDate(new Date());
        context.getCommandService().executeCommand(new SaveHistoryTaskCommand(task,pi));
        task.setState(TaskState.Ready);
        task.setOpinion(old_opinion);
        task.setEndDate(null);
        if (task.getType().equals(TaskType.Participative)) {
            task.setAssignee(null);
        }
        session.update(task);
        session.update(pi);
        Node targetNode=pd.getNode(nodeName);
        if(targetNode==null){
            throw new IllegalArgumentException("Target node "+nodeName+" is not exist!");
        }
        pi.setActionType(ActionType.RECALL);
        targetNode.doEnterEventHandler(context,pi);
        return task;
    }

}
