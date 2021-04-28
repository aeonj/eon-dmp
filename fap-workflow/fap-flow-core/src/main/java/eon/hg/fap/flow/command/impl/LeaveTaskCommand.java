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
import eon.hg.fap.flow.meta.ActionType;
import eon.hg.fap.flow.meta.NodeState;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskBusiness;
import eon.hg.fap.flow.process.listener.UserTaskListener;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.List;

/**
 * 任务节点离开执行
 * @author aeon
 * @since 2020年8月7日
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
		Session session=context.getSession();
		Criteria criteria=session.createCriteria(TaskBusiness.class);
		criteria.add(Restrictions.eq("processInstanceId",task.getProcessInstanceId()));
		List<TaskBusiness> list = criteria.list();
		if (list!=null && list.size()>0) {
			TaskBusiness taskBusiness = list.get(0);
			if (ActionType.INPUT.equals(actionType)) {
				taskBusiness.setNextStatusCode(NodeState.UN_CHECK.getCode());
				taskBusiness.setCurrentStatusCode(NodeState.CHECK.getCode());
			} else if (ActionType.NEXT.equals(actionType)) {
				taskBusiness.setNextStatusCode(NodeState.UN_CHECK.getCode());
				taskBusiness.setCurrentStatusCode(NodeState.CHECK.getCode());
			} else if (ActionType.RECALL.equals(actionType)) {
				taskBusiness.setNextStatusCode(NodeState.UN_CHECK.getCode());
				taskBusiness.setCurrentStatusCode(NodeState.CHECK.getCode());
			} else if (ActionType.BACK.equals(actionType)) {
				taskBusiness.setNextStatusCode(NodeState.FROM_BACK.getCode());
				taskBusiness.setCurrentStatusCode(NodeState.BACK.getCode());
			} else if (ActionType.DISCARD.equals(actionType)) {
				taskBusiness.setNextStatusCode(null);
				taskBusiness.setCurrentStatusCode(NodeState.DISCARD.getCode());
			}
			taskBusiness.setCurrentNodeName(task.getNodeName());
			taskBusiness.setActionType(actionType);
			taskBusiness.setCurrentTaskId(task.getId());
			session.update(taskBusiness);
		}
		return task;
	}

	private Collection<UserTaskListener> fetchUserTaskListener(Context context){
		return context.getApplicationContext().getBeansOfType(UserTaskListener.class).values();
	}
}
