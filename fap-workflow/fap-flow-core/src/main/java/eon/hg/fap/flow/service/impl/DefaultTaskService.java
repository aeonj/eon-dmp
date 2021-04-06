/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eon.hg.fap.flow.service.impl;

import eon.hg.fap.flow.command.CommandService;
import eon.hg.fap.flow.command.impl.*;
import eon.hg.fap.flow.command.impl.jump.JumpNode;
import eon.hg.fap.flow.model.HistoryActivity;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskAppointor;
import eon.hg.fap.flow.model.task.TaskParticipator;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.model.task.reminder.TaskReminder;
import eon.hg.fap.flow.process.node.TaskNode;
import eon.hg.fap.flow.process.node.UserData;
import eon.hg.fap.flow.query.TaskQuery;
import eon.hg.fap.flow.query.impl.TaskQueryImpl;
import eon.hg.fap.flow.service.HistoryService;
import eon.hg.fap.flow.service.ProcessService;
import eon.hg.fap.flow.service.TaskOpinion;
import eon.hg.fap.flow.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2013年7月29日
 */
public class DefaultTaskService implements TaskService {
	private CommandService commandService;
	private HistoryService historyService;
	private ProcessService processService;
	
	public void setProgress(int progress,long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ChangeTaskProgressCommand(task,progress));
	}
	
	@Override
	public void setPriority(String priority, long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ChangeTaskPriorityCommand(task,priority));
	}
	
	public Task addCountersign(long taskId, String username) {
		Task task=getTask(taskId);
		return commandService.executeCommand(new AddCountersignCommand(task,username));
	}
	
	public void deleteCountersign(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new DeleteCountersignCommand(task));
	}
	
	@Override
	public void deleteTask(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new DeleteTaskCommand(task));
	}
	
	@Override
	public void deleteTaskByNode(long processInstanceId, String nodeName) {
		commandService.executeCommand(new DeleteTaskByNodeCommand(processInstanceId,nodeName));
	}
	
	@Override
	public void cancelTask(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CancelTaskCommand(task,null));
	}
	
	@Override
	public void cancelTask(long taskId, TaskOpinion opinion) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CancelTaskCommand(task,opinion));
	}
	
	public List<JumpNode> getAvaliableForwardTaskNodes(long taskId) {
		Task task=getTask(taskId);
		return getAvaliableForwardTaskNodes(task);
	}
	
	public List<JumpNode> getAvaliableForwardTaskNodes(Task task) {
		return commandService.executeCommand(new GetJumpAvaliableTaskNodesCommand(task));
	}
	
	public void saveTaskAppointor(long taskId, String assignee, String taskNodeName) {
		saveTaskAppointor(taskId,new String[]{assignee},taskNodeName);
	}
	
	public void saveTaskAppointor(long taskId, String[] assignees,String taskNodeName) {
		Task task=getTask(taskId);
		commandService.executeCommand(new SaveTaskAppointorCommand(task,taskNodeName,assignees));
		
	}
	
	public List<String> getAvaliableAppointAssigneeTaskNodes(long taskId) {
		Task task=getTask(taskId);
		return commandService.executeCommand(new GetAvaliableAppointAssigneeTaskNodes(task));
	}
	
	public List<String> getTaskNodeAssignees(long taskId,String taskNodeName) {
		return commandService.executeCommand(new GetTaskNodeAssigneesCommand(taskId,taskNodeName));
	}
	public List<JumpNode> getAvaliableRollbackTaskNodes(Task task) {
		ProcessInstance pi=processService.getProcessInstanceById(task.getProcessInstanceId());
		List<JumpNode> allNodes=getAvaliableForwardTaskNodes(task);
		List<HistoryActivity> hisActivities=historyService.getHistoryActivitysByProcesssInstanceId(pi.getRootId());
		List<JumpNode> result=new ArrayList<JumpNode>();
		for(JumpNode node:allNodes){
			for(HistoryActivity activity:hisActivities){
				if(node.getName().equals(activity.getNodeName())){
					result.add(node);
					break;
				}
			}
		}
		return result;
		
	}
	
	public List<JumpNode> getAvaliableRollbackTaskNodes(long taskId) {
		Task task=getTask(taskId);
		return getAvaliableRollbackTaskNodes(task);
	}
	
	public void rollback(long taskId, String targetNodeName) {
		forward(taskId, targetNodeName);
	}
	
	public void rollback(long taskId, String targetNodeName,Map<String, Object> variables) {
		Task task=getTask(taskId);
		rollback(task,targetNodeName,variables,null);
	}
	
	public void rollback(long taskId, String targetNodeName,Map<String, Object> variables, TaskOpinion opinion) {
		Task task=getTask(taskId);
		rollback(task,targetNodeName,variables,opinion);
	}
	
	public void rollback(Task task, String targetNodeName,Map<String, Object> variables, TaskOpinion opinion) {
		commandService.executeCommand(new RollbackTaskCommand(task,targetNodeName,variables,opinion));
	}
	
	public void complete(long taskId, String flowName) {
		complete(taskId,flowName,null,null);
	}

	public void complete(long taskId, String flowName, TaskOpinion opinion) {
		complete(taskId,flowName,null,opinion);
	}
	
	public void batchComplete(List<Long> taskIds, Map<String, Object> variables) {
		commandService.executeCommand(new BatchCompleteTasksCommand(taskIds, variables,null));
	}
	
	public void batchComplete(List<Long> taskIds,Map<String, Object> variables, TaskOpinion opinion) {
		commandService.executeCommand(new BatchCompleteTasksCommand(taskIds, variables,opinion));
	}
	
	public void batchStartAndComplete(List<Long> taskIds,Map<String, Object> variables, TaskOpinion opinion) {
		commandService.executeCommand(new BatchStartAndCompleteTasksCommand(taskIds, variables,opinion));
	}
	
	public void batchStart(List<Long> taskIds) {
		commandService.executeCommand(new BatchStartTasksCommand(taskIds));
	}
	
	public void batchStartAndComplete(List<Long> taskIds,Map<String, Object> variables) {
		commandService.executeCommand(new BatchStartAndCompleteTasksCommand(taskIds, variables,null));
	}
	
	public void complete(long taskId,String flowName,Map<String, Object> variables) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CompleteTaskCommand(task, flowName,null, variables));
	}
	
	public void complete(long taskId, String flowName,Map<String, Object> variables, TaskOpinion opinion) {
		Task task=getTask(taskId);
		commandService.executeCommand(new CompleteTaskCommand(task,flowName,opinion, variables));
	}

	public void complete(long taskId) {
		complete(taskId,null,null,null);
	}
	
	public void complete(long taskId, TaskOpinion opinion) {
		complete(taskId,null,null,opinion);
	}

	public void complete(long taskId, Map<String, Object> variables) {
		complete(taskId,null,variables,null);
	}
	
	public void complete(long taskId, Map<String, Object> variables,TaskOpinion opinion) {
		complete(taskId,null,variables,opinion);
	}
	
	public void forward(long taskId, String targetNodeName) {
		forward(taskId,targetNodeName,null,null);
	}
	public void forward(long taskId, String targetNodeName,TaskOpinion opinion) {
		forward(taskId,targetNodeName,null,opinion);
	}
	
	public void forward(long taskId, String targetNodeName,Map<String, Object> variables) {
		forward(taskId,targetNodeName,variables,null);
	}
	
	public void forward(long taskId, String targetNodeName,Map<String, Object> variables, TaskOpinion opinion) {
		Task task=getTask(taskId);
		forward(task,targetNodeName,variables,opinion,TaskState.Forwarded);
	}
	public void forward(Task task, String targetNodeName,Map<String, Object> variables, TaskOpinion opinion) {
		forward(task,targetNodeName,variables,opinion,TaskState.Forwarded);
	}
	
	public void forward(Task task, String targetNodeName,Map<String, Object> variables, TaskOpinion opinion, TaskState state) {
		commandService.executeCommand(new ForwardTaskCommand(task,targetNodeName,variables,opinion,state));
	}

	public void withdraw(long taskId) {
		withdraw(taskId,null,null);
	}
	
	public void withdraw(long taskId, TaskOpinion opinion) {
		withdraw(taskId,null,opinion);
	}

	public void withdraw(long taskId, Map<String, Object> variables) {
		withdraw(taskId,variables,null);
	}
	
	public void withdraw(long taskId, Map<String, Object> variables,TaskOpinion opinion) {
		Task task=getTask(taskId);
		if(task.getState().equals(TaskState.InProgress)){
			throw new IllegalStateException("Task "+task.getTaskName()+" state is InProgress,can not be withdraw.");
		}
		commandService.executeCommand(new WithdrawTaskCommand(task,variables,opinion));
	}
	
	public boolean canWithdraw(long taskId) {
		Task task=getTask(taskId);
		return canWithdraw(task);
	}
	
	public boolean canWithdraw(Task task) {
		if(task.getState().equals(TaskState.InProgress)){
			return false;
		}
		return commandService.executeCommand(new CanWithdrawDecisionCommand(task));
	}

	public Task getTask(long taskId) {
		return commandService.executeCommand(new GetTaskCommand(taskId));
	}
	
	public void claim(long taskId, String username) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ClaimTaskCommand(task,username));
	}

	public void release(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ReleaseTaskCommand(task));
	}

	public void start(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new StartTaskCommand(task));
	}

	public void suspend(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new SuspendTaskCommand(task));
	}

	public void resume(long taskId) {
		Task task=getTask(taskId);
		commandService.executeCommand(new ResumeTaskCommand(task));
	}
	public List<TaskAppointor> getTaskAppointors(String taskNodeName,
			long processInstanceId) {
		return commandService.executeCommand(new GetTaskAppointorCommand(taskNodeName, processInstanceId));
	}

	public void changeTaskAssignee(long taskId, String username) {
		commandService.executeCommand(new ChangeTaskAssigneeCommand(taskId, username));
	}
	
	public String getUserData(Task task, String key) {
		return getUserData(task.getProcessId(),task.getTaskName(),key);
	}
	
	public String getUserData(long processId,String taskNodeName,String key) {
		ProcessDefinition process=processService.getProcessById(processId);
		TaskNode node=(TaskNode)process.getNode(taskNodeName);
		if(node.getUserData()==null)return null;
		for(UserData data:node.getUserData()){
			if(data.getKey().equals(key)){
				return data.getValue();
			}
		}
		return null;
	}
	
	public TaskQuery createTaskQuery() {
		return new TaskQueryImpl(commandService);
	}
	
	public List<TaskReminder> getAllTaskReminders() {
		return commandService.executeCommand(new GetTaskReminderCommand(0));
	}
	
	public List<TaskParticipator> getTaskParticipators(long taskId) {
		return commandService.executeCommand(new GetTaskParticipatorsCommand(taskId));
	}
	
	public List<TaskReminder> getTaskReminders(long taskId) {
		return commandService.executeCommand(new GetTaskReminderCommand(taskId));
	}
	public void deleteTaskReminder(long taskReminderId) {
		commandService.executeCommand(new DeleteTaskReminderCommand(taskReminderId));
	}
	
	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}
}
