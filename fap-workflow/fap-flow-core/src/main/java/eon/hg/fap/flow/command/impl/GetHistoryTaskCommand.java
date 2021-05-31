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
import eon.hg.fap.flow.model.HistoryTask;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * 只取historytask的第一条记录，应用时需注意
 */
public class GetHistoryTaskCommand implements Command<HistoryTask> {
	private long taskId;
	public GetHistoryTaskCommand(long taskId){
		this.taskId=taskId;
	}
	public HistoryTask execute(Context context) {
		List<HistoryTask> tasks = context.getSession().createCriteria(HistoryTask.class).add(Restrictions.eq("taskId", taskId)).list();
		if (tasks==null || tasks.size()==0) {
			return null;
		} else {
			return tasks.get(0);
		}
	}
}
