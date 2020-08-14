package eon.hg.fap.uflo.util;

import cn.hutool.core.convert.Convert;
import com.bstek.uflo.model.HistoryTask;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import eon.hg.fap.core.query.PageList;
import eon.hg.fap.flow.meta.NodeState;
import eon.hg.fap.flow.meta.TaskVO;

import java.util.ArrayList;
import java.util.List;

public class UfloUtil {
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


    public static List<TaskVO> convertTaskLog(List<HistoryTask> taskList) {
        List<TaskVO> taskLogs = new ArrayList<>();
        for (HistoryTask ht : taskList) {
            TaskVO taskLog = new TaskVO();
            taskLog.setTaskId(Convert.toStr(ht.getTaskId()));
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
