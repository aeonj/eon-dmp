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
package eon.hg.fap.flow.query;

import eon.hg.fap.flow.model.HistoryVariable;

import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月14日
 */
public interface HistoryProcessVariableQuery extends Query<List<HistoryVariable>>{
	HistoryProcessVariableQuery historyProcessInstanceId(long historyProcessInstanceId);
	HistoryProcessVariableQuery key(String key);
	HistoryProcessVariableQuery page(int firstResult, int maxResults);
	HistoryProcessVariableQuery addOrderAsc(String property);
	HistoryProcessVariableQuery addOrderDesc(String property);
}
