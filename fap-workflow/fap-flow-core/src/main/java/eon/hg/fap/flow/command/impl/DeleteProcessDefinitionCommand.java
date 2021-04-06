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
import eon.hg.fap.flow.model.*;
import eon.hg.fap.flow.model.variable.Variable;
import eon.hg.fap.flow.query.ProcessInstanceQuery;
import eon.hg.fap.flow.service.ProcessService;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年9月9日
 */
public class DeleteProcessDefinitionCommand implements Command<Object> {
	private ProcessDefinition processDefinition;
	public DeleteProcessDefinitionCommand(ProcessDefinition processDefinition){
		this.processDefinition=processDefinition;
	}
	@SuppressWarnings("unchecked")
	public Object execute(Context context) {
		ProcessService processService=context.getProcessService();
		ProcessInstanceQuery query=context.getProcessService().createProcessInstanceQuery();
		query.processId(processDefinition.getId());
		Session session=context.getSession();
		for(ProcessInstance pi:query.list()){
			processService.deleteProcessInstance(pi);
			session.createQuery("delete "+Variable.class.getName()+" where processInstanceId=:processInstanceId").setLong("processInstanceId", pi.getId()).executeUpdate();
		}
		
		List<HistoryProcessInstance> hisInstances=session.createQuery("from "+HistoryProcessInstance.class.getName()+" where processId=:processId").setLong("processId", processDefinition.getId()).list();
		for(HistoryProcessInstance instance:hisInstances){
			session.createQuery("delete "+HistoryVariable.class.getName()+" where historyProcessInstanceId=:historyProcessInstanceId").setLong("historyProcessInstanceId", instance.getId()).executeUpdate();
		}
		
		session.createQuery("delete "+Blob.class.getName()+" where processId=:processId").setLong("processId", processDefinition.getId()).executeUpdate();
		session.createQuery("delete "+HistoryProcessInstance.class.getName()+" where processId=:processId").setLong("processId", processDefinition.getId()).executeUpdate();
		session.createQuery("delete "+HistoryTask.class.getName()+" where processId=:processId").setLong("processId", processDefinition.getId()).executeUpdate();
		session.createQuery("delete "+HistoryActivity.class.getName()+" where processId=:processId").setLong("processId", processDefinition.getId()).executeUpdate();
		session.delete(processDefinition);
		return null;
	}
}
