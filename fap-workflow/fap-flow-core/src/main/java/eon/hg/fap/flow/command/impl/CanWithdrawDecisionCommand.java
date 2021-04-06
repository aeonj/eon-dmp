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
import eon.hg.fap.flow.command.impl.jump.JumpNode;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.process.node.Node;
import eon.hg.fap.flow.process.node.StartNode;
import eon.hg.fap.flow.process.node.TaskNode;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年9月24日
 */
public class CanWithdrawDecisionCommand implements Command<Boolean> {
	public Task task;
	public CanWithdrawDecisionCommand(Task task){
		this.task=task;
	}
	public Boolean execute(Context context) {
		ProcessDefinition pd=context.getProcessService().getProcessById(task.getProcessId());
		String prevTaskName=task.getPrevTask();
		if(StrUtil.isEmpty(prevTaskName)){
			return false;
		}
		if(prevTaskName.equals(task.getNodeName())){
			return false;
		}
		List<JumpNode> nodes=context.getTaskService().getAvaliableRollbackTaskNodes(task);
		boolean canJump=false;
		for(JumpNode jumpNode:nodes){
			if(jumpNode.getName().equals(prevTaskName)){
				canJump=true;
				break;
			}
		}
		if(!canJump){
			return false;
		}
		
		Node currentNode=pd.getNode(task.getNodeName());
		Node node=pd.getNode(prevTaskName);
		if(node!=null && (node instanceof TaskNode || node instanceof StartNode)){
			if(node instanceof StartNode && currentNode instanceof StartNode){
				return false;
			}else{
				return true;				
			}
		}else{
			return false;
		}
	}
}
