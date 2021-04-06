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
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.process.flow.SequenceFlowImpl;
import eon.hg.fap.flow.process.node.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年10月20日
 */
public class GetAvaliableAppointAssigneeTaskNodes implements Command<List<String>> {
	public Task task;
	public GetAvaliableAppointAssigneeTaskNodes(Task task){
		this.task=task;
	}
	public List<String> execute(Context context) {
		ProcessDefinition pd=context.getProcessService().getProcessById(task.getProcessId());
		Node node=pd.getNode(task.getNodeName());
		List<String> nodes=new ArrayList<String>();
		getAvliableNodes(node,nodes,pd);
		return nodes;
	}
	private void getAvliableNodes(Node node,List<String> nodes,ProcessDefinition pd){
		List<SequenceFlowImpl> flows=node.getSequenceFlows();
		if(flows==null || flows.size()==0)return;
		for(SequenceFlowImpl flow:flows){
			Node toNode=pd.getNode(flow.getToNode());
			if(toNode instanceof StartNode || 
					toNode instanceof JoinNode || 
					toNode instanceof ForeachNode || 
					toNode instanceof SubprocessNode || 
					toNode instanceof ForkNode){
				continue;
			}
			if(toNode instanceof TaskNode){
				TaskNode taskNode=(TaskNode)toNode;
				if(taskNode.isAllowSpecifyAssignee()){
					nodes.add(toNode.getName());					
				}
			}else{
				getAvliableNodes(toNode,nodes,pd);
			}
		}
	}
}
