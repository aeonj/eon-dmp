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
package eon.hg.fap.flow.heartbeat;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.flow.service.SchedulerService;
import eon.hg.fap.flow.utils.EnvironmentUtils;
import org.hibernate.SessionFactory;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.simpl.SimpleThreadPool;
import org.springframework.beans.factory.InitializingBean;

import java.text.ParseException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Jacky.gao
 * @since 2013-5-6
 */
public class InstanceDetection implements InitializingBean{
	public static final String BEAN_ID="wf.instanceDetection";
	private String instanceNames;
	private Scheduler scheduler;
	private boolean disableScheduler;
	private SchedulerService schedulerService;
	private String detectionCron="0/30 * * * * ?";
	private Logger log=Logger.getGlobal();
	private void startDaemonJob() throws Exception{
		if(disableScheduler){
			log.info("Current flow application is disabled scheduler...");
			return;
		}
		String currentInstanceName=System.getProperty("wf.instanceName");
		if(StrUtil.isBlank(instanceNames)){
			if(StrUtil.isNotBlank(currentInstanceName)){
				log.info("Flow job cluster names is empty,but system property \"wf.instanceName\" value is \""+currentInstanceName+"\",so Flow job run mode is single still...");
			}else{
				log.info("Flow job run mode is single...");
			}
			schedulerService.resetScheduer();
			return;
		}else{
			if(StrUtil.isBlank(currentInstanceName)){
				String msg="Current flow application configured cluster names \""+instanceNames+"\",but not configure system property \"wf.instanceName\".";
				log.info(msg);
				throw new RuntimeException(msg);
			}
		}
		log.info("Flow job run mode is cluster...");
		initDetectionScheduler();
		
		JobDetailImpl jobDetail=initJobDetail(currentInstanceName);
		Trigger trigger=initTrigger();
		HeartbeatDetectionJob detectionJob=new HeartbeatDetectionJob();
		jobDetail.setJobClass(detectionJob.getClass());
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
		log.info("Flow cluster daemon scheduler is started...");
	}
	
	private void initDetectionScheduler() throws Exception{
		StdSchedulerFactory factory=new StdSchedulerFactory();
		Properties mergedProps = new Properties();
		mergedProps.setProperty(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, SimpleThreadPool.class.getName());
		mergedProps.setProperty("org.quartz.scheduler.makeSchedulerThreadDaemon", "true");
		mergedProps.setProperty("org.quartz.scheduler.instanceName", "UfloClusterHeartbeatScheduler");
		mergedProps.setProperty("org.quartz.scheduler.instanceId", "UfloHeartbeatDetectionScheduler");
		mergedProps.setProperty("org.quartz.threadPool.threadCount","2");
		factory.initialize(mergedProps);
		scheduler=factory.getScheduler();
	}
	
	private JobDetailImpl initJobDetail(String currentInstanceName){
		String clusterJobInstanceNames[]=instanceNames.split(",");
		SessionFactory sessionFactory=EnvironmentUtils.getEnvironment().getSessionFactory();
		JobDetailImpl jobDetail=new DetectionJobDetail(sessionFactory,currentInstanceName,clusterJobInstanceNames,schedulerService);
		jobDetail.setKey(new JobKey("UfloDaemonJobDetail"));
		jobDetail.setName("UfloDaemonDetectionJobDetail");
		return jobDetail;
	}
	
	private Trigger initTrigger(){
		CronTriggerImpl trigger=new CronTriggerImpl();
		trigger.setName("UfloHeartbeatTrigger");
		trigger.setKey(new TriggerKey("UfloHeartbeatTrigger"));
		try {
			trigger.setCronExpression(detectionCron);
			return trigger;
		} catch (ParseException e1) {
			throw new RuntimeException(e1);
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		startDaemonJob();
	}
	
	public void setDisableScheduler(boolean disableScheduler) {
		this.disableScheduler = disableScheduler;
	}

	
	public void setInstanceNames(String instanceNames) {
		this.instanceNames = instanceNames;
	}
	
	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}
	public void setDetectionCron(String detectionCron) {
		this.detectionCron = detectionCron;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
}
