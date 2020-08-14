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
package eon.hg.fap.uflo.command;

import com.bstek.uflo.command.Command;
import com.bstek.uflo.command.impl.SaveHistoryTaskCommand;
import com.bstek.uflo.command.impl.SaveProcessInstanceVariablesCommand;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.process.listener.GlobalTaskListener;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.process.node.reminder.DueDefinition;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.SchedulerService;
import com.bstek.uflo.service.TaskOpinion;
import eon.hg.fap.flow.meta.ActionType;
import eon.hg.fap.uflo.interfaces.UserTaskListener;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import javax.swing.*;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2013年8月7日
 */
public class LeaveTaskCommand implements Command<Task> {
	private Task task;
	private ActionType actionType;
	public LeaveTaskCommand(Task task, ActionType actionType){
		this.task=task;
		this.actionType=actionType;
	}
	public Task execute(Context context) {
		//追加全局任务结束处理
		Collection<UserTaskListener> coll=fetchUserTaskListener(context);
		for(UserTaskListener listener:coll){
			listener.onTaskLeave(context, task, actionType);
		}
		return task;
	}

	private Collection<UserTaskListener> fetchUserTaskListener(Context context){
		return context.getApplicationContext().getBeansOfType(UserTaskListener.class).values();
	}
}
