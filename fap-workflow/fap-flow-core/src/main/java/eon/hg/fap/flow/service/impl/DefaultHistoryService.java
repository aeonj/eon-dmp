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

import eon.hg.fap.flow.command.CommandService;
import eon.hg.fap.flow.command.impl.*;
import eon.hg.fap.flow.model.HistoryActivity;
import eon.hg.fap.flow.model.HistoryProcessInstance;
import eon.hg.fap.flow.model.HistoryTask;
import eon.hg.fap.flow.model.HistoryVariable;
import eon.hg.fap.flow.query.HistoryProcessInstanceQuery;
import eon.hg.fap.flow.query.HistoryProcessVariableQuery;
import eon.hg.fap.flow.query.HistoryTaskQuery;
import eon.hg.fap.flow.query.impl.HistoryProcessInstanceQueryImpl;
import eon.hg.fap.flow.query.impl.HistoryProcessVariableQueryImpl;
import eon.hg.fap.flow.query.impl.HistoryTaskQueryImpl;
import eon.hg.fap.flow.service.HistoryService;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年9月12日
 */
public class DefaultHistoryService implements HistoryService {
	private CommandService commandService;
	public List<HistoryActivity> getHistoryActivitysByProcesssInstanceId(long processInstanceId) {
		return commandService.executeCommand(new GetHistoryActivitiyCommand(processInstanceId,true));
	}
	
	public HistoryProcessVariableQuery createHistoryProcessVariableQuery() {
		return new HistoryProcessVariableQueryImpl(commandService);
	}
	
	public HistoryVariable getHistoryVariable(long historyProcessInstanceId,String key) {
		return commandService.executeCommand(new GetHistoryVariableCommand(historyProcessInstanceId,key));
	}

	public List<HistoryActivity> getHistoryActivitysByHistoryProcesssInstanceId(long historyProcessInstanceId) {
		return commandService.executeCommand(new GetHistoryActivitiyCommand(historyProcessInstanceId,false));
	}

	public List<HistoryProcessInstance> getHistoryProcessInstances(long processId) {
		return commandService.executeCommand(new GetListHistoryProcessInstancesCommand(processId));
	}

	public HistoryProcessInstance getHistoryProcessInstance(long processInstanceId) {
		return commandService.executeCommand(new GetHistoryProcessInstanceCommand(processInstanceId));
	}

	public List<HistoryTask> getHistoryTasks(long processInstanceId) {
		return commandService.executeCommand(new GetListHistoryTasksCommand(processInstanceId));
	}
	
	public HistoryProcessInstanceQuery createHistoryProcessInstanceQuery() {
		return new HistoryProcessInstanceQueryImpl(commandService);
	}
	
	public HistoryTaskQuery createHistoryTaskQuery() {
		return new HistoryTaskQueryImpl(commandService);
	}
	
	public List<HistoryVariable> getHistoryVariables(long historyProcessInstanceId) {
		return commandService.executeCommand(new GetHistoryVariablesCommand(historyProcessInstanceId));
	}
	
	public HistoryTask getHistoryTask(long taskId) {
		return commandService.executeCommand(new GetHistoryTaskCommand(taskId));
	}
	public void setCommandService(CommandService commandService) {
		this.commandService = commandService;
	}
}
