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
import eon.hg.fap.flow.model.HistoryTask;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskParticipator;
import eon.hg.fap.flow.model.task.TaskType;
import eon.hg.fap.flow.service.SchedulerService;
import org.hibernate.Session;

/**
 * @author Jacky.gao
 * @since 2017年5月2日
 */
public class DeleteTaskCommand implements Command<Task> {
	private Task task;
	public DeleteTaskCommand(Task task){
		this.task=task;
	}
	public Task execute(Context context) {
		Session session=context.getSession();
		if(task.getType().equals(TaskType.Participative) || task.getType().equals(TaskType.Normal)){
			session.createQuery("delete "+TaskParticipator.class.getName()+" where taskId=:taskId").setLong("taskId", task.getId()).executeUpdate();			
		}
		String hql="delete "+HistoryTask.class.getName()+" where taskId=:taskId";
		session.createQuery(hql).setLong("taskId", task.getId()).executeUpdate();
		session.delete(task);
		SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
		schedulerService.removeReminderJob(task);
		return task;
	}
}
