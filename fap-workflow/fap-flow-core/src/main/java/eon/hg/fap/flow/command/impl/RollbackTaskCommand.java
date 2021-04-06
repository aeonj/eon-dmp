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
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.service.TaskOpinion;
import eon.hg.fap.flow.service.TaskService;

import java.util.Map;


/**
 * @author Jacky.gao
 * @since 2013年10月12日
 */
public class RollbackTaskCommand implements Command<Object> {
	private Map<String, Object> variables;
	private Task task;
	private TaskOpinion opinion;
	private String targetNodeName;
	public RollbackTaskCommand(Task task,String targetNodeName,Map<String, Object> variables,TaskOpinion opinion){
		this.variables=variables;
		this.opinion=opinion;
		this.task=task;
		this.targetNodeName=targetNodeName;
	}
	public Object execute(Context context) {
		TaskService taskService=context.getTaskService();
		taskService.forward(task, targetNodeName, variables,opinion,TaskState.Rollback);
		return null;
	}
}
