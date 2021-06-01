package eon.hg.fap.flow.utils;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.util.tools.GetValue;
import eon.hg.fap.core.query.PageList;
import eon.hg.fap.flow.meta.NodeState;
import eon.hg.fap.flow.meta.TaskVO;
import eon.hg.fap.flow.model.HistoryTask;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author eonook
 */
public class FlowTaskUtils {
    public static List<TaskState> getTaskStates(NodeState ns) {
        List<TaskState> taskStateList = new ArrayList<>();
        if (NodeState.UN_CHECK.equals(ns)) {
            taskStateList.add(TaskState.Created);
            taskStateList.add(TaskState.Ready);
            taskStateList.add(TaskState.InProgress);
            taskStateList.add(TaskState.Suspended);
            taskStateList.add(TaskState.Reserved);
        } else if (NodeState.CHECK.equals(ns)) {
            taskStateList.add(TaskState.Completed);
        } else if (NodeState.BACK.equals(ns)) {
            taskStateList.add(TaskState.Rollback);
        } else if (NodeState.FROM_BACK.equals(ns)) {
            taskStateList.add(TaskState.Ready);
        } else if (NodeState.ALL.equals(ns)) {

        }
        return taskStateList;
    }

    public static PageList getPageList(List list, int totalRows, int page, int limit) {
        PageList pageList = new PageList();
        pageList.setResult(list);
        pageList.setCurrentPage(page);
        pageList.setRowCount(totalRows);
        pageList.setPageSize(limit);
        pageList.setPages(totalRows/limit+totalRows%limit==0?0:1);
        return pageList;
    }


    public static List<TaskVO> convertTaskLog(List<HistoryTask> taskList, GetValue<String,String> user) {
        List<TaskVO> taskLogs = new ArrayList<>();
        for (HistoryTask ht : taskList) {
            TaskVO taskLog = new TaskVO();
            taskLog.setTaskId(Convert.toStr(ht.getTaskId()));
            taskLog.setTaskName(ht.getTaskName());
            if (TaskState.Completed.equals(ht.getState())) {
                taskLog.setState("完成");
            } else if (TaskState.Recall.equals(ht.getState())) {
                taskLog.setState("撤销");
            } else if (TaskState.Rollback.equals(ht.getState())) {
                taskLog.setState("退回");
            } else if (TaskState.Withdraw.equals(ht.getState())) {
                taskLog.setState("撤回");
            } else if (TaskState.Canceled.equals(ht.getState())) {
                taskLog.setState("作废");
            } else if (TaskState.Forwarded.equals(ht.getState())) {
                taskLog.setState("转发");
            } else if (TaskState.Ready.equals(ht.getState())) {
                taskLog.setState("待办");
            } else if (TaskState.Edit.equals(ht.getState())) {
                taskLog.setState("修改");
            }
            taskLog.setProcessId(Convert.toStr(ht.getProcessId()));
            taskLog.setProcessInstanceId(Convert.toStr(ht.getProcessInstanceId()));
            taskLog.setBusinessId(ht.getBusinessId());
            taskLog.setAssignee(user.get(ht.getAssignee()));
            taskLog.setCreateDate(ht.getCreateDate());
            taskLog.setDueDate(ht.getDuedate());
            taskLog.setEndDate(ht.getEndDate());
            taskLog.setDescription(ht.getDescription());
            taskLog.setOpinion(ht.getOpinion());
            taskLog.setOwner(ht.getOwner());
            taskLogs.add(taskLog);
        }
        return taskLogs;

    }

    public static List<TaskVO> convertTaskInfo(List<Task> taskList) {
        List<TaskVO> taskLogs = new ArrayList<>();
        for (Task ht : taskList) {
            TaskVO taskLog = new TaskVO();
            taskLog.setTaskId(Convert.toStr(ht.getId()));
            taskLog.setTaskName(ht.getTaskName());
            taskLog.setProcessId(Convert.toStr(ht.getProcessId()));
            taskLog.setProcessInstanceId(Convert.toStr(ht.getProcessInstanceId()));
            taskLog.setBusinessId(ht.getBusinessId());
            taskLog.setAssignee(ht.getAssignee());
            taskLog.setCreateDate(ht.getCreateDate());
            taskLog.setDueDate(ht.getDuedate());
            taskLog.setEndDate(ht.getEndDate());
            taskLog.setDescription(ht.getDescription());
            taskLog.setOpinion(ht.getOpinion());
            taskLog.setOwner(ht.getOwner());
            taskLogs.add(taskLog);
        }
        return taskLogs;

    }


}
