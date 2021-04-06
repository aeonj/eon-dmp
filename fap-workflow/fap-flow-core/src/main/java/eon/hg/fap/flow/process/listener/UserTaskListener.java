package eon.hg.fap.flow.process.listener;

import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.meta.ActionType;
import eon.hg.fap.flow.model.task.Task;

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
