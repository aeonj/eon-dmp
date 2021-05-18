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

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年5月2日
 */
public class DeleteTaskByNodeCommand implements Command<Integer> {
	private long processInstanceId;
	private String nodeName;
	public DeleteTaskByNodeCommand(long processInstanceId,String nodeName){
		this.processInstanceId=processInstanceId;
		this.nodeName=nodeName;
	}
	@SuppressWarnings("unchecked")
	public Integer execute(Context context) {
		Session session=context.getSession();
		String hql="from "+Task.class.getName()+" where nodeName=:nodeName and (processInstanceId=:processInstanceId or rootProcessInstanceId=:rootProcessInstanceId)";
		List<Task> list=session.createQuery(hql)
				.setString("nodeName", nodeName)
				.setLong("processInstanceId", processInstanceId)
				.setLong("rootProcessInstanceId", processInstanceId)
				.list();
		SchedulerService schedulerService=(SchedulerService)context.getApplicationContext().getBean(SchedulerService.BEAN_ID);
		for(Task task:list){
			if(task.getType().equals(TaskType.Participative) || task.getType().equals(TaskType.Normal)){
				session.createQuery("delete "+TaskParticipator.class.getName()+" where taskId=:taskId").setLong("taskId", task.getId()).executeUpdate();			
			}
			hql="delete "+HistoryTask.class.getName()+" where taskId=:taskId";
			session.createQuery(hql).setLong("taskId", task.getId()).executeUpdate();
			session.delete(task);
			schedulerService.removeReminderJob(task);
		}
		return list.size();
	}
}
