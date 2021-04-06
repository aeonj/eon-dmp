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
import eon.hg.fap.flow.model.HistoryVariable;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Jacky.gao
 * @since 2013年9月28日
 */
public class GetHistoryVariableCommand implements Command<HistoryVariable> {
	private long historyProcessInstanceId;
	private String key;
	public GetHistoryVariableCommand(long historyProcessInstanceId,String key){
		this.historyProcessInstanceId=historyProcessInstanceId;
		this.key=key;
	}
	public HistoryVariable execute(Context context) {
		Criteria criteria=context.getSession().createCriteria(HistoryVariable.class);
		criteria.add(Restrictions.eq("historyProcessInstanceId", historyProcessInstanceId));
		criteria.add(Restrictions.eq("key", key));
		HistoryVariable hisVar=(HistoryVariable)criteria.uniqueResult();
		if(hisVar!=null){
			hisVar.init(context);			
		}
		return hisVar;
	}
}
