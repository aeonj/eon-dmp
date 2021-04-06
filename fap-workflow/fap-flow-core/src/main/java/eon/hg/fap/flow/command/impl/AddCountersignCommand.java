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

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.flow.command.Command;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.model.task.TaskType;
import eon.hg.fap.flow.process.listener.GlobalTaskListener;
import eon.hg.fap.flow.process.listener.TaskListener;
import eon.hg.fap.flow.process.node.Node;
import eon.hg.fap.flow.process.node.TaskNode;
import eon.hg.fap.flow.query.TaskQuery;
import eon.hg.fap.flow.utils.IDGenerator;
import org.hibernate.Session;

import java.util.Collection;
import java.util.Date;

/**
 * @author Jacky.gao
 * @since 2013年10月15日
 */
public class AddCountersignCommand implements Command<Task> {
	private Task task;
	private String username;
	public AddCountersignCommand(Task task,String username){
		this.task=task;
		this.username=username;
	}
	public Task execute(Context context) {
		if(!TaskType.Countersign.equals(task.getType())){
			throw new IllegalArgumentException("Task "+task.getId()+" is not a countersign task.");
		}
		Session session=context.getSession();
		TaskQuery query=context.getTaskService().createTaskQuery();
		query.processInstanceId(task.getProcessInstanceId());
		query.nodeName(task.getNodeName());
		int count=0;
		for(Task t:query.list()){
			count=t.getCountersignCount();
			t.setCountersignCount(count+1);
			session.update(t);
		}
		
		ProcessDefinition pd=context.getProcessService().getProcessById(task.getProcessId());
		Node node=pd.getNode(task.getNodeName());
		if(node!=null && (node instanceof TaskNode)) {
			TaskNode taskNode=(TaskNode)node;
			String taskListenerBean=taskNode.getTaskListenerBean();
			if(StrUtil.isNotEmpty(taskListenerBean)){
				TaskListener taskListener=(TaskListener)context.getApplicationContext().getBean(taskListenerBean);
				taskListener.onTaskCreate(context, task);
			}
		}
		
		Collection<GlobalTaskListener> coll=context.getApplicationContext().getBeansOfType(GlobalTaskListener.class).values();
		for(GlobalTaskListener listener:coll){
			listener.onTaskCreate(context, task);
		}
		
		Task newTask=new Task();
		newTask.setAssignee(username);
		newTask.setBusinessId(task.getBusinessId());
		newTask.setCountersignCount(count+1);
		newTask.setCreateDate(new Date());
		newTask.setDateUnit(task.getDateUnit());
		newTask.setDescription(task.getDescription());
		newTask.setDuedate(task.getDuedate());
		newTask.setId(IDGenerator.getInstance().nextId());
		newTask.setNodeName(task.getNodeName());
		newTask.setOwner(task.getOwner());
		newTask.setPrevTask(task.getPrevTask());
		newTask.setProcessId(task.getProcessId());
		newTask.setProcessInstanceId(task.getProcessInstanceId());
		newTask.setState(TaskState.Created);
		newTask.setRootProcessInstanceId(task.getRootProcessInstanceId());
		newTask.setTaskName(task.getTaskName());
		newTask.setType(task.getType());
		newTask.setUrl(task.getUrl());
		newTask.setSubject(task.getSubject());
		newTask.setPriority(task.getPriority());
		session.save(newTask);
		
		return newTask;
	}
}
