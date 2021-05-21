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
package eon.hg.fap.flow.command.impl;

import eon.hg.fap.flow.command.Command;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.meta.ActionType;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.process.flow.SequenceFlowImpl;
import eon.hg.fap.flow.process.node.Node;
import eon.hg.fap.flow.service.ProcessService;
import eon.hg.fap.flow.service.TaskOpinion;
import eon.hg.fap.flow.service.TaskService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jacky.gao
 * @since 2013年9月19日
 */
public class ForwardTaskCommand implements Command<Task> {
	private Task task;
	private String jumpTargetNodeName;
	private Map<String,Object> variables;
	private TaskOpinion opinion;
	private TaskState state;
	public ForwardTaskCommand(Task task,String jumpTargetNodeName,Map<String,Object> variables,TaskOpinion opinion,TaskState state){
		this.task=task;
		this.opinion=opinion;
		this.jumpTargetNodeName=jumpTargetNodeName;
		this.variables=variables;
		this.state=state;
	}
	public Task execute(Context context) {
		ProcessService processService=context.getProcessService();
		ProcessDefinition processDefinition=processService.getProcessById(task.getProcessId());
		Node targetNode=processDefinition.getNode(jumpTargetNodeName);
		if(targetNode==null){
			throw new IllegalArgumentException("Target node "+jumpTargetNodeName+" is not exist!");
		}
		Node taskNode=processDefinition.getNode(task.getNodeName());
		String targetFlowName=null;
		List<SequenceFlowImpl> flows=taskNode.getSequenceFlows();
		for(SequenceFlowImpl flow:flows){
			if(flow.getToNode().equals(jumpTargetNodeName)){
				targetFlowName=flow.getName();
				break;
			}
		}
		if(targetFlowName==null){
			targetFlowName=TaskService.TEMP_FLOW_NAME_PREFIX+UUID.randomUUID().toString();
			SequenceFlowImpl newFlow=new SequenceFlowImpl();
			newFlow.setToNode(jumpTargetNodeName);
			newFlow.setName(targetFlowName);
			newFlow.setProcessId(task.getProcessId());
			flows.add(newFlow);
		}
		ProcessInstance processInstance=processService.getProcessInstanceById(task.getProcessInstanceId());
		if(variables!=null && variables.size()>0){
			context.getExpressionContext().addContextVariables(processInstance, variables);
			context.getCommandService().executeCommand(new SaveProcessInstanceVariablesCommand(processInstance, variables));			
		}
		if(opinion!=null){
			task.setOpinion(opinion.getOpinion());
		}
		task.setEndDate(new Date());
		task.setState(state);
		context.getSession().update(task);
		context.getCommandService().executeCommand(new SaveHistoryTaskCommand(task,processInstance));
		processInstance.setActionType(ActionType.FORWARD);
		taskNode.leave(context, processInstance, targetFlowName);
		return task;
	}
}
