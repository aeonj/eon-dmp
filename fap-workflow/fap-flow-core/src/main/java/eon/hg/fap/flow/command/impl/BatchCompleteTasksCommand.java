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
import eon.hg.fap.flow.service.TaskOpinion;

import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2013年10月9日
 */
public class BatchCompleteTasksCommand implements Command<Object> {
	private List<Long> taskIds;
	private Map<String,Object> variables;
	private TaskOpinion opinion;
	public BatchCompleteTasksCommand(List<Long> taskIds,Map<String,Object> variables,TaskOpinion opinion){
		this.taskIds=taskIds;
		this.variables=variables;
		this.opinion=opinion;
	}
	public Object execute(Context context) {
		for(long taskId:taskIds){
			context.getTaskService().complete(taskId, variables,opinion);
		}
		return null;
	}
}
