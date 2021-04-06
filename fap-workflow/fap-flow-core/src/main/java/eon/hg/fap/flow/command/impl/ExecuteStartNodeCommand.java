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
import eon.hg.fap.flow.process.node.StartNode;

public class ExecuteStartNodeCommand implements Command<Object> {
	private StartNode startNode;
	private ProcessInstance processInstance;
	public ExecuteStartNodeCommand(StartNode startNode,ProcessInstance processInstance){
		this.startNode=startNode;
		this.processInstance=processInstance;
	}
	public Object execute(Context context) {
		startNode.getSequenceFlows().get(0).execute(context,processInstance);
		return null;
	}
}
