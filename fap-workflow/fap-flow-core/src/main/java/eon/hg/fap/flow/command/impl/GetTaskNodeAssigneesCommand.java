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
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.process.node.TaskNode;
import eon.hg.fap.flow.service.ProcessService;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年10月20日
 */
public class GetTaskNodeAssigneesCommand implements Command<List<String>> {
	private long taskId;
	private String taskNodeName;
	public GetTaskNodeAssigneesCommand(long taskId,String taskNodeName){
		this.taskId=taskId;
		this.taskNodeName=taskNodeName;
	}
	public List<String> execute(Context context) {
		ProcessService processService=context.getProcessService();
		Task task=context.getTaskService().getTask(taskId);
		ProcessInstance processInstance=processService.getProcessInstanceById(task.getProcessInstanceId());
		ProcessDefinition pd=processService.getProcessById(processInstance.getProcessId());
		TaskNode node=(TaskNode)pd.getNode(taskNodeName);
		return node.getAssigneeUsers(context, processInstance);
	}
}
