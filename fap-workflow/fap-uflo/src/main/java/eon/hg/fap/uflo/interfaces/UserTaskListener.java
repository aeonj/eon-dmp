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
