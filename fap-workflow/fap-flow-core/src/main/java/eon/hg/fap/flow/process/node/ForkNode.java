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
package eon.hg.fap.flow.process.node;

import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.ProcessInstanceState;
import eon.hg.fap.flow.process.flow.SequenceFlowImpl;
import eon.hg.fap.flow.utils.IDGenerator;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分支节点，开启子流程实例
 * @author Jacky.gao
 * @since 2020年7月31日
 */
public class ForkNode extends Node {
	private static final long serialVersionUID = -1654058426879715199L;

	@Override
	public boolean enter(Context context, ProcessInstance processInstance) {
		return true;
	}

	@Override
	public String leave(Context context, ProcessInstance processInstance,String flowName) {
		Session session=context.getSession();
		List<SequenceFlowImpl> flows=new ArrayList<SequenceFlowImpl>();
		for(SequenceFlowImpl flow:getSequenceFlows()){
			if(!flow.canExecute(context, processInstance)){
				continue;
			}
			flows.add(flow);
		}
		for(SequenceFlowImpl flow:flows){
			ProcessInstance subProcessInstance=new ProcessInstance();
			subProcessInstance.setId(IDGenerator.getInstance().nextId());
			subProcessInstance.setProcessId(getProcessId());
			subProcessInstance.setParentId(processInstance.getId());
			subProcessInstance.setCreateDate(new Date());
			subProcessInstance.setState(ProcessInstanceState.Start);
			subProcessInstance.setRootId(processInstance.getRootId());
			subProcessInstance.setParallelInstanceCount(flows.size());
			subProcessInstance.setPromoter(processInstance.getPromoter());
			subProcessInstance.setHistoryProcessInstanceId(processInstance.getHistoryProcessInstanceId());
			subProcessInstance.setCurrentTask(processInstance.getCurrentTask());
			subProcessInstance.setBusinessId(processInstance.getBusinessId());
			subProcessInstance.setTag(processInstance.getTag());
			subProcessInstance.setSubject(processInstance.getSubject());
			session.save(subProcessInstance);
			context.getExpressionContext().createContext(subProcessInstance, null);
			flow.execute(context, subProcessInstance);
			flowName=flow.getName();
		}
		return flowName;
	}
	@Override
	public void cancel(Context context, ProcessInstance processInstance) {
	}
	
	@Override
	public NodeType getType() {
		return NodeType.Fork;
	}
}
