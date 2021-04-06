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
import eon.hg.fap.flow.model.variable.BlobVariable;
import eon.hg.fap.flow.model.variable.TextVariable;
import eon.hg.fap.flow.query.QueryJob;
import org.hibernate.Criteria;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月14日
 */
public class QueryListCommand<T> implements Command<T> {
	private QueryJob job;
	public QueryListCommand(QueryJob job){
		this.job=job;
	}
	@SuppressWarnings("unchecked")
	public T execute(Context context) {
		Criteria criteria=job.getCriteria(context.getSession(),false);
		List<Object> list=criteria.list();
		for(Object obj:list){
			if(obj instanceof BlobVariable){
				((BlobVariable)obj).initValue(context);
			}
			if(obj instanceof TextVariable){
				((TextVariable)obj).initValue(context);
			}
		}
		return (T)list;
	}
}
