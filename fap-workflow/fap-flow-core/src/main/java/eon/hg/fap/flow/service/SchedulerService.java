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
package eon.hg.fap.flow.service;

import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.reminder.TaskReminder;
import eon.hg.fap.flow.process.node.reminder.CalendarInfo;
import org.quartz.Calendar;
import org.quartz.Scheduler;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月21日
 */
public interface SchedulerService {
	public static final String BEAN_ID="wf.schedulerService";
	Scheduler getScheduler();
	void addReminderJob(TaskReminder reminder, ProcessInstance processInstance, Task task);
	void removeReminderJob(Task task);
	Calendar buildCalendar(List<CalendarInfo> infos);
	void resetScheduer();
	void deleteJob(long reminderId);
}
