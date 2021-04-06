package eon.hg.fap.flow.command.impl;

import eon.hg.fap.flow.command.Command;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.process.flow.SequenceFlowImpl;
import eon.hg.fap.flow.process.node.Node;
import eon.hg.fap.flow.service.TaskOpinion;
import eon.hg.fap.flow.service.TaskService;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 撤销审核
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
        if(!taskService.canWithdraw(task)){
            throw new IllegalArgumentException("Task "+task.getTaskName()+" can not forward to "+task.getPrevTask()+" node.");
        }
        String nodeName = task.getTaskName();
        ProcessDefinition pd = context.getProcessService().getProcessById(task.getProcessId());
        ProcessInstance pi = context.getProcessService().getProcessInstanceById(task.getProcessInstanceId());

        Session session = context.getSession();
        Query query = session.createQuery("from "+Task.class.getName()+" where prevTask=:prevTask and processInstanceId=:processInstanceId");
        query.setString("prevTask",nodeName);
        query.setLong("processInstanceId",task.getProcessInstanceId());
        List<Task> taskList = query.list();
        for (Task nextTask : taskList) {
            if (TaskState.Reserved.equals(nextTask.getState())
                    || TaskState.Created.equals(nextTask.getState())
                    || TaskState.Ready.equals(nextTask.getState())
                    || TaskState.Suspended.equals(nextTask.getState())) {
                if(variables!=null && variables.size()>0){
                    context.getExpressionContext().addContextVariables(pi, variables);
                    context.getCommandService().executeCommand(new SaveProcessInstanceVariablesCommand(pi, variables));
                }
                if(opinion!=null){
                    nextTask.setOpinion(opinion.getOpinion());
                }
                nextTask.setEndDate(new Date());
                nextTask.setState(TaskState.Withdraw);
                nextTask.setAssignee(task.getAssignee());
                context.getSession().update(nextTask);
                context.getCommandService().executeCommand(new SaveHistoryTaskCommand(nextTask,pi));
                Node taskNode=pd.getNode(nextTask.getNodeName());
                String targetFlowName=null;
                List<SequenceFlowImpl> flows=taskNode.getSequenceFlows();
                for(SequenceFlowImpl flow:flows){
                    if(flow.getToNode().equals(nodeName)){
                        targetFlowName=flow.getName();
                        break;
                    }
                }
                if(targetFlowName==null){
                    targetFlowName=TaskService.TEMP_FLOW_NAME_PREFIX+ UUID.randomUUID().toString();
                    SequenceFlowImpl newFlow=new SequenceFlowImpl();
                    newFlow.setToNode(nodeName);
                    newFlow.setName(targetFlowName);
                    newFlow.setProcessId(task.getProcessId());
                    flows.add(newFlow);
                }
                taskNode.leave(context, pi, targetFlowName);
            }
        }
        return task;
    }

}
