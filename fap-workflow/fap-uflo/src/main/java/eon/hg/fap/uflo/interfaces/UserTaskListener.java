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
package eon.hg.fap.uflo.interfaces;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.node.TaskNode;
import eon.hg.fap.flow.meta.ActionType;

/**
 * 全局处理任务结束事件类，允许多个实现
 * @author eonook
 * @since 2020年5月16日
 */
public interface UserTaskListener {
	/**
	 * 当前节点上人工任务完成时将触发该方法调用.
	 * @param context 上下文对象
	 * @param task 当前节点上一个已完成的人工任务.
	 * @param actionType 操作类型
	 */
	void onTaskLeave(Context context, Task task, ActionType actionType);
}
