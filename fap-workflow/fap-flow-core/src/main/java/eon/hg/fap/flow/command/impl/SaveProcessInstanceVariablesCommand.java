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
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.variable.BlobVariable;
import eon.hg.fap.flow.model.variable.TextVariable;
import eon.hg.fap.flow.model.variable.Variable;
import eon.hg.fap.flow.query.ProcessVariableQuery;
import eon.hg.fap.flow.query.impl.ProcessVariableQueryImpl;
import eon.hg.fap.flow.utils.IDGenerator;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2013年7月31日
 */
public class SaveProcessInstanceVariablesCommand implements Command<Object> {
	private Map<String,Object> variables;
	private ProcessInstance processInstance;
	public SaveProcessInstanceVariablesCommand(ProcessInstance processInstance,Map<String,Object> variables){
		this.processInstance=processInstance;
		this.variables=variables;
	}
	public Object execute(Context context) {
		Session session=context.getSession();
		for (Map.Entry<String,Object> entry : variables.entrySet()) {
			Object obj=entry.getValue();
			if(obj==null){
				continue;
				//throw new IllegalArgumentException("Variable ["+key+"] value can not be null.");
			}
			ProcessVariableQuery query=new ProcessVariableQueryImpl(context.getCommandService());
			query.processInstanceId(processInstance.getId());
			query.key(entry.getKey());
			List<Variable> oldVars=query.list();
			for(Variable var:oldVars){
				session.delete(var);
				if(var instanceof TextVariable){
					session.delete(((TextVariable)var).getBlob());
				}
				if(var instanceof BlobVariable){
					session.delete(((BlobVariable)var).getBlob());
				}
			}
			Variable var=Variable.newVariable(obj, context);
			var.setId(IDGenerator.getInstance().nextId());
			var.setKey(entry.getKey());
			var.setProcessInstanceId(processInstance.getId());
			var.setRootProcessInstanceId(processInstance.getRootId());
			context.getExpressionContext().addContextVariables(processInstance, variables);
			session.save(var);
		}
		return null;
	}

}
	
