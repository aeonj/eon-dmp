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
import eon.hg.fap.flow.model.task.reminder.TaskReminder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月21日
 */
public class GetTaskReminderCommand implements Command<List<TaskReminder>> {
	private long taskId;
	public GetTaskReminderCommand(long taskId){
		this.taskId=taskId;
	}
	@SuppressWarnings("unchecked")
	public List<TaskReminder> execute(Context context) {
		Criteria criteria=context.getSession().createCriteria(TaskReminder.class);
		if(taskId>0){
			criteria.add(Restrictions.eq("taskId",taskId));
		}
		return criteria.list();
	}
}
