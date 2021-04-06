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
import eon.hg.fap.flow.deploy.ProcessDeployer;
import eon.hg.fap.flow.deploy.parse.impl.ProcessParser;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.Blob;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.utils.EnvironmentUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月2日
 */
public class GetProcessCommand implements Command<ProcessDefinition> {
	private long processId;
	private String processName;
	private int version;
	private String categoryId;
	public GetProcessCommand(long processId,String processName,int version,String categoryId){
		this.processId=processId;
		this.processName=processName;
		this.version=version;
		this.categoryId=categoryId;
	}
	@SuppressWarnings("unchecked")
	public ProcessDefinition execute(Context context) {
		Session session=context.getSession();
		if(processId>0){
			ProcessDefinition p=(ProcessDefinition)session.get(ProcessDefinition.class, processId);
			return parseProcess(p.getId(),p.getVersion(),p.getName(),session);
		}else if(StrUtil.isNotEmpty(processName)){
			Criteria criteria=session.createCriteria(ProcessDefinition.class).add(Restrictions.eq("name", processName)).addOrder(Order.desc("version"));
			if(categoryId==null){
				categoryId=EnvironmentUtils.getEnvironment().getCategoryId();				
			}
			if(StrUtil.isNotEmpty(categoryId)){
				criteria.add(Restrictions.eq("categoryId", categoryId));
			}
			if(version>0){
				criteria.add(Restrictions.eq("version", version));
				List<ProcessDefinition> processes=criteria.list();
				if(processes.size()>0){
					ProcessDefinition p=processes.get(0);
					return parseProcess(p.getId(),p.getVersion(),p.getName(),session);
				}
			}else{
				List<ProcessDefinition> processes=criteria.list();
				for(ProcessDefinition process:processes){
					Date effectDate=process.getEffectDate();
					if(effectDate==null){
						return parseProcess(process.getId(),process.getVersion(),process.getName(),session);						
					}else{
						if((new Date()).getTime()>effectDate.getTime()){
							return parseProcess(process.getId(),process.getVersion(),process.getName(),session);													
						}
					}
				}
			}
		}
		return null;
	}
	
	private ProcessDefinition parseProcess(long processId,int version,String processName,Session session){
		String hql="from "+Blob.class.getName()+" where processId=:processId and name=:name";
		Blob blob=(Blob)session.createQuery(hql).setLong("processId",processId).setString("name",processName+ProcessDeployer.PROCESS_EXTENSION_NAME).uniqueResult();
		try {
			ProcessDefinition process=ProcessParser.parseProcess(blob.getBlobValue(),processId,true);
			process.setId(processId);
			process.setVersion(version);
			return process;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
