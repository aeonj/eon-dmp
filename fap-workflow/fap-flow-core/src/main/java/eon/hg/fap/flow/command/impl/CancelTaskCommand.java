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
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.service.ProcessService;
import eon.hg.fap.flow.service.SchedulerService;
import eon.hg.fap.flow.service.TaskOpinion;
import org.hibernate.Session;

import java.util.Date;

/**
 * @author Jacky.gao
 * @since 2017年5月2日
 */
public class CancelTaskCommand implements Command<Task> {
	private Task task;
	private TaskOpinion opinion;
	public CancelTaskCommand(Task task,TaskOpinion opinion){
		this.task=task;
		this.opinion=opinion;
	}
	public Task execute(Context context) {
		Session session=context.getSession();
		task.setState(TaskState.Canceled);
		if(opinion!=null){
			task.setOpinion(opinion.getOpinion());
			task.setEndDate(new Date());
		}
		session.update(task);
		ProcessService processService=context.getProcessService();
		ProcessInstance processInstance=processService.getProcessInstanceById(task.getProcessInstanceId());
		context.getCommandService().executeCommand(new SaveHistoryTaskCommand(task,processInstance));
		SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
		schedulerService.removeReminderJob(task);
		return task;
	}
}
