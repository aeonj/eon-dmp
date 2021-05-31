package eon.hg.fap.flow.command.impl;

import eon.hg.fap.flow.command.Command;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.ProcessInstance;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.utils.EnvironmentUtils;

import java.util.Map;

/**
 * 执行修改时保存工作流日志
 */
public class EditTaskCommand implements Command<Task> {
    private Task task;
    private Map<String, Object> variables;

    public EditTaskCommand(Task task, Map<String, Object> variables) {
        this.task = task;
        this.variables = variables;
    }

    @Override
    public Task execute(Context context) {
        ProcessInstance pi = context.getProcessService().getProcessInstanceById(task.getProcessInstanceId());
        if(variables!=null && variables.size()>0){
            context.getExpressionContext().addContextVariables(pi, variables);
            context.getCommandService().executeCommand(new SaveProcessInstanceVariablesCommand(pi, variables));
        }
        String loginUser= EnvironmentUtils.getEnvironment().getLoginUser();
        String oldUser = task.getAssignee();
        task.setAssignee(loginUser);
        TaskState oldState = task.getState();
        task.setState(TaskState.Edit);
        context.getCommandService().executeCommand(new SaveHistoryTaskCommand(task,pi));
        task.setState(oldState);
        task.setAssignee(oldUser);
        return task;
    }
}
