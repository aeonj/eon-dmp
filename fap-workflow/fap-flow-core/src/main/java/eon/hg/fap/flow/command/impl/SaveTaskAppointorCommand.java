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
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskAppointor;
import eon.hg.fap.flow.utils.IDGenerator;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Jacky.gao
 * @since 2013年10月20日
 */
public class SaveTaskAppointorCommand implements Command<Object> {
	private Task task;
	private String taskNodeName;
	private String[] assignees;
	public SaveTaskAppointorCommand(Task task,String taskNodeName,String[] assignees){
		this.task=task;
		this.taskNodeName=taskNodeName;
		this.assignees=assignees;
	}
	public Object execute(Context context) {
		Session session=context.getSession();
		Query query=session.createQuery("delete "+TaskAppointor.class.getName()+" where taskNodeName=:nodeName and processInstanceId=:processInstanceId");
		query.setString("nodeName",taskNodeName).setLong("processInstanceId", task.getRootProcessInstanceId()).executeUpdate();
		for(String assignee:assignees){
			TaskAppointor appointor=new TaskAppointor();
			appointor.setId(IDGenerator.getInstance().nextId());
			appointor.setAppointor(task.getAssignee());
			appointor.setOwner(assignee);
			appointor.setAppointorNode(task.getNodeName());
			appointor.setProcessInstanceId(task.getRootProcessInstanceId());
			appointor.setTaskNodeName(taskNodeName);
			session.save(appointor);
		}
		return null;
	}
}
