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

/**
 * @author Jacky.gao
 * @since 2013年8月14日
 */
public class StartTaskCommand implements Command<Task> {
	private Task task;
	public StartTaskCommand(Task task){
		this.task=task;
	}
	public Task execute(Context context) {
		if(!task.getState().equals(TaskState.Created) && !task.getState().equals(TaskState.Reserved)){
			throw new IllegalStateException("Only state is Created or Reserved task can be start!");
		}
		task.setState(TaskState.InProgress);
		context.getSession().update(task);
		return task;
	}

}
