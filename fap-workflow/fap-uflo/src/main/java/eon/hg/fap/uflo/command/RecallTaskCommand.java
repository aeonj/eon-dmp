package eon.hg.fap.uflo.command;

import com.bstek.uflo.command.Command;
import com.bstek.uflo.command.impl.SaveHistoryTaskCommand;
import com.bstek.uflo.command.impl.SaveProcessInstanceVariablesCommand;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.process.flow.SequenceFlowImpl;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
