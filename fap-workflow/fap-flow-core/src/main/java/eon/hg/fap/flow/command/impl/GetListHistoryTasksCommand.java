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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年9月12日
 */
public class GetListHistoryTasksCommand implements Command<List<HistoryTask>> {
	private long processInstanceId;

	public GetListHistoryTasksCommand(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@SuppressWarnings("unchecked")
	public List<HistoryTask> execute(Context context) {
		return context.getSession().createCriteria(HistoryTask.class)
				.add(Restrictions.eq("processInstanceId", processInstanceId))
				.addOrder(Order.desc("endDate")).list();
	}
}
