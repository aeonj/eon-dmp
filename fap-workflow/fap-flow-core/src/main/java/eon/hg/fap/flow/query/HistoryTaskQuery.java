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

import eon.hg.fap.flow.model.HistoryTask;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.model.task.TaskType;

import java.util.Date;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2013年8月14日
 */
public interface HistoryTaskQuery extends Query<List<HistoryTask>> {
	HistoryTaskQuery assignee(String assignee);
	HistoryTaskQuery owner(String owner);
	HistoryTaskQuery addTaskState(TaskState state);
	HistoryTaskQuery addPrevTaskState(TaskState state);
	HistoryTaskQuery processInstanceId(long processInstanceId);
	HistoryTaskQuery rootProcessInstanceId(long rootProcessInstanceId);
	HistoryTaskQuery historyProcessInstanceId(long historyProcessInstanceId);
	HistoryTaskQuery createDateLessThen(Date date);
	HistoryTaskQuery createDateLessThenOrEquals(Date date);
	HistoryTaskQuery createDateGreaterThen(Date date);
	HistoryTaskQuery createDateGreaterThenOrEquals(Date date);
	HistoryTaskQuery endDateLessThen(Date date);
	HistoryTaskQuery endDateLessThenOrEquals(Date date);
	HistoryTaskQuery endDateGreaterThen(Date date);
	HistoryTaskQuery endDateGreaterThenOrEquals(Date date);
	HistoryTaskQuery urlLike(String url);
	HistoryTaskQuery countersign(boolean countersign);
	HistoryTaskQuery taskType(TaskType type);
	HistoryTaskQuery processId(long processId);
	HistoryTaskQuery taskId(long taskId);
	HistoryTaskQuery nameLike(String name);
	HistoryTaskQuery businessId(String businessId);
	HistoryTaskQuery nodeName(String nodeName);
	HistoryTaskQuery page(int firstResult, int maxResults);
	HistoryTaskQuery addOrderAsc(String property);
	HistoryTaskQuery addOrderDesc(String property);
}
