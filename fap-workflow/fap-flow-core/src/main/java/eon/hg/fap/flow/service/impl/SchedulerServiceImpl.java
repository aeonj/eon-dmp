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
package eon.hg.fap.flow.service.impl;

import eon.hg.fap.flow.env.TaskDueDefinitionProvider;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.reminder.ReminderType;
import eon.hg.fap.flow.model.task.reminder.TaskReminder;
import eon.hg.fap.flow.process.handler.ReminderHandler;
import eon.hg.fap.flow.process.node.TaskNode;
import eon.hg.fap.flow.process.node.calendar.MultipleCalendar;
import eon.hg.fap.flow.process.node.reminder.*;
import eon.hg.fap.flow.service.ProcessService;
import eon.hg.fap.flow.service.SchedulerService;
import eon.hg.fap.flow.service.TaskService;
import eon.hg.fap.flow.service.impl.job.ReminderJob;
import eon.hg.fap.flow.service.impl.job.ReminderJobDetail;
import eon.hg.fap.flow.service.impl.job.ScanReminderJob;
import eon.hg.fap.flow.service.impl.job.ScanReminderJobDetail;
import org.quartz.Calendar;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.simpl.SimpleThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;

/**
 * @author Jacky.gao
 * @since 2013年8月21日
 */
public class SchedulerServiceImpl implements SchedulerService,ApplicationContextAware {
	private static final Logger log= LoggerFactory.getLogger(SchedulerServiceImpl.class);
	private static final String JOB_NAME_PREFIX="reminderJob";
	private static final String JOB_GROUP_PREFIX="remindergroup";
	private static final String REMINDER_CALENDAR_PREFIX="reminderCalendar";
	private static final String SCAN_REMINDER_CRON="0/40 * * * * ?";
	private Scheduler scheduler;
	private TaskService taskService;
	private ProcessService processService;
	private int threadCount;
	private ApplicationContext applicationContext;
	private TaskDueDefinitionProvider provider;
	private String makeSchedulerThreadDaemon;
	private boolean enableScanReminderJob;
	private List<Long> reminderTaskList=new ArrayList<Long>();
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public void addReminderJob(TaskReminder reminder,ProcessInstance processInstance,Task task) {
		JobKey jobKey=new JobKey(JOB_NAME_PREFIX+reminder.getId(),JOB_GROUP_PREFIX);
		try {
			if(scheduler.checkExists(jobKey)){
				return;
			}
			AbstractTrigger<? extends Trigger> trigger=null;
			if(reminder.getType().equals(ReminderType.Once)){
				SimpleTriggerImpl simpleTrigger=new SimpleTriggerImpl();
				simpleTrigger.setRepeatCount(0);
				trigger=simpleTrigger;
				long executeTime=reminder.getStartDate().getTime()+10000;
				long now=(new Date()).getTime();
				if(executeTime<=now){
					return;
				}
			}else{
				CronTriggerImpl cronTrigger=new CronTriggerImpl();
				cronTrigger.setCronExpression(reminder.getCron());
				trigger=cronTrigger;
			}
			trigger.setName("trigger_"+reminder.getId());
			trigger.setStartTime(reminder.getStartDate());
			ReminderJobDetail jobDetail=new ReminderJobDetail();
			jobDetail.setJobClass(ReminderJob.class);
			ReminderHandler handler=(ReminderHandler)applicationContext.getBean(reminder.getReminderHandlerBean());
			jobDetail.setReminderHandlerBean(handler);
			if(task==null){
				task=taskService.getTask(reminder.getTaskId());				
			}
			jobDetail.setTask(task);
			jobDetail.setProcessInstance(processService.getProcessInstanceById(task.getProcessInstanceId()));
			jobDetail.setKey(jobKey);
			Calendar calendar=getCalendar(reminder,processInstance,task);
			if(calendar!=null){
				String calendarName=REMINDER_CALENDAR_PREFIX+reminder.getId();
				scheduler.addCalendar(calendarName, calendar,false, false);
				trigger.setCalendarName(calendarName);
			}
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public void removeReminderJob(Task task) {
		List<TaskReminder> reminders=taskService.getTaskReminders(task.getId());
		for(TaskReminder reminder:reminders){
			try {
				scheduler.deleteJob(new JobKey(JOB_NAME_PREFIX+reminder.getId(),JOB_GROUP_PREFIX));
			} catch (SchedulerException e) {
				throw new RuntimeException(e);
			}
			String calendarId=REMINDER_CALENDAR_PREFIX+reminder.getId();
			try {
				scheduler.deleteCalendar(calendarId);
			} catch (SchedulerException e) {
				log.error(e.getMessage());
			}
			taskService.deleteTaskReminder(reminder.getId());
			reminderTaskList.remove(reminder.getId());
		}
	}
	
	@Override
	public void deleteJob(long reminderId){
		try {
			scheduler.deleteJob(new JobKey(JOB_NAME_PREFIX+reminderId,JOB_GROUP_PREFIX));
			String calendarId=REMINDER_CALENDAR_PREFIX+reminderId;
			scheduler.deleteCalendar(calendarId);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void resetScheduer() {
		try{
			reminderTaskList.clear();
			if(scheduler!=null && !scheduler.isShutdown()){
				scheduler.shutdown(false);
			}
			log.info("Current uflo job instance scheduler is starting...");
			this.scheduler=this.newScheduler();
			if(this.scheduler.isInStandbyMode()){
				this.scheduler.start();
			}
			initTaskReminders();
			log.info("Uflo scheduler is started successfully...");
		}catch(SchedulerException ex){
			throw new RuntimeException(ex);
		}
	}

	private Scheduler newScheduler() throws SchedulerException {
		StdSchedulerFactory factory=new StdSchedulerFactory();
		Properties mergedProps = new Properties();
		mergedProps.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, SimpleThreadPool.class.getName());
		mergedProps.setProperty("org.quartz.scheduler.instanceName", "UFLOScheduler");
		mergedProps.setProperty("org.quartz.scheduler.instanceId", "UFLOScheduler");
		mergedProps.setProperty("org.quartz.scheduler.makeSchedulerThreadDaemon", makeSchedulerThreadDaemon);
		mergedProps.setProperty("org.quartz.threadPool.threadCount", Integer.toString(threadCount));
		factory.initialize(mergedProps);
		Scheduler newScheduler=factory.getScheduler();
		return newScheduler;
	}

	private void initTaskReminders(){
		List<TaskReminder> reminders=taskService.getAllTaskReminders();
		for(TaskReminder reminder:reminders){
			reminderTaskList.add(reminder.getId());
			addReminderJob(reminder,null,null);
		}
		if(enableScanReminderJob){
			initScanReminderJob();			
		}
	}
	
	private void initScanReminderJob(){
		CronTriggerImpl trigger=new CronTriggerImpl();
		trigger.setName("UfloScanReminderTrigger");
		trigger.setKey(new TriggerKey("UfloScanReminderTrigger"));
		try {
			trigger.setCronExpression(SCAN_REMINDER_CRON);
			ScanReminderJob job=new ScanReminderJob();
			ScanReminderJobDetail detail=new ScanReminderJobDetail();
			detail.setSchedulerService(this);
			detail.setTaskService(taskService);
			detail.setReminderTaskList(reminderTaskList);
			detail.setJobClass(job.getClass());
			detail.setKey(new JobKey("UfloScanReminderJob"));
			scheduler.scheduleJob(detail, trigger);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
	
	private Calendar getCalendar(TaskReminder taskReminder,ProcessInstance processInstance,Task task){
		DueDefinition dueDefinition=null;
		Reminder reminder=null;
		if(provider!=null){
			if(task==null){
				long taskId=taskReminder.getTaskId();
				task=taskService.getTask(taskId);				
			}
			if(processInstance==null){
				processInstance=processService.getProcessInstanceById(task.getProcessInstanceId());				
			}
			dueDefinition=provider.getDueDefinition(task, processInstance);
			if(dueDefinition!=null){
				reminder=dueDefinition.getReminder();	
			}
		}
		if(dueDefinition==null){
			long processId=taskReminder.getProcessId();
			String taskNodeName=taskReminder.getTaskNodeName();
			TaskNode node=(TaskNode)processService.getProcessById(processId).getNode(taskNodeName);
			dueDefinition=node.getDueDefinition();
			if(dueDefinition!=null){
				reminder=dueDefinition.getReminder();				
			}
		}
		if(reminder==null || !(reminder instanceof PeriodReminder)){
			return null;
		}
		List<CalendarInfo> infos=((PeriodReminder)reminder).getCalendarInfos();
		if(infos==null){
			return null;
		}
		return buildCalendar(infos);
	}
	public Calendar buildCalendar(List<CalendarInfo> infos) {
		MultipleCalendar mulCalendar=null;
		Collection<CalendarProvider> providers=applicationContext.getBeansOfType(CalendarProvider.class).values();
		for(CalendarInfo info:infos){
			for(CalendarProvider provider:providers){
				Calendar calendar=provider.getCalendar(info.getId());
				if(calendar!=null){
					if(mulCalendar==null){
						mulCalendar=new MultipleCalendar();;
					}
					mulCalendar.addCalendar((BaseCalendar)calendar);
				}
			}
		}
		return mulCalendar;
	}
		
	public void setMakeSchedulerThreadDaemon(String makeSchedulerThreadDaemon) {
		this.makeSchedulerThreadDaemon = makeSchedulerThreadDaemon;
	}
	
	public void setEnableScanReminderJob(boolean enableScanReminderJob) {
		this.enableScanReminderJob = enableScanReminderJob;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		Collection<TaskDueDefinitionProvider> colls=applicationContext.getBeansOfType(TaskDueDefinitionProvider.class).values();
		if(colls.size()>0){
			provider=colls.iterator().next();
		}
	}

	public void destroy(){
		try{
			if(scheduler!=null && !scheduler.isShutdown()){
				scheduler.shutdown(false);
			}			
		}catch(Exception ex){}
	}
	
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
}
