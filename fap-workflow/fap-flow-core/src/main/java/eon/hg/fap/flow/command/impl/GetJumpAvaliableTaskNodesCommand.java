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
import eon.hg.fap.flow.command.impl.jump.JumpNode;
import eon.hg.fap.flow.command.impl.jump.JumpNodeBuilder;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jacky.gao
 * @since 2013年9月26日
 */
public class GetJumpAvaliableTaskNodesCommand implements Command<List<JumpNode>> {
	private Task task;
	public GetJumpAvaliableTaskNodesCommand(Task task){
		this.task=task;
	}
	public List<JumpNode> execute(Context context) {
		ProcessDefinition process=context.getProcessService().getProcessById(task.getProcessId());
		JumpNodeBuilder builder=new JumpNodeBuilder(process);
		Map<String,JumpNode> map=builder.buildSimulationTasks();
		JumpNode node=map.get(task.getNodeName());
		List<JumpNode> list=new ArrayList<JumpNode>();
		int level=node.getLevel();
		for(JumpNode jumpNode:map.values()){
			if(jumpNode.getLevel()==level && parentEquals(node.getParent(),jumpNode.getParent()) && !jumpNode.getName().equals(node.getName())){
				list.add(jumpNode);
			}
		}
		return list;
	}
	
	private boolean parentEquals(List<String> a,List<String> b){
		if(a.size()!=b.size())return false;
		for(int i=0;i<a.size();i++){
			String name=a.get(i);
			if(!name.equals(b.get(i))){
				return false;
			}
		}
		return true;
	}
}
