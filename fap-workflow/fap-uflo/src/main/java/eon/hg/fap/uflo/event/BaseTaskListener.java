package eon.hg.fap.uflo.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskParticipator;
import com.bstek.uflo.model.task.TaskType;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.listener.GlobalTaskListener;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.utils.IDGenerator;
import eon.hg.fap.db.model.primary.UfloCurrentTasks;
import eon.hg.fap.db.service.IUfloCurrentTasksService;
import eon.hg.fap.db.service.IUfloNodeUsersService;
import eon.hg.fap.flow.meta.ActionType;
import eon.hg.fap.flow.meta.NodeState;
import eon.hg.fap.uflo.interfaces.UserTaskListener;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BaseTaskListener implements GlobalTaskListener,UserTaskListener {
    @Autowired
    private IUfloCurrentTasksService currentTasksService;
    @Autowired
    private IUfloNodeUsersService nodeUsersService;
    @Autowired
    @Qualifier("roleAssigneeProvider")
    private AssigneeProvider assigneeProvider;
    /**
     * 在流程实例流转到人工任务节点时触发该方法，此时还未开始创建任务
     *
     * @param context         上下文对象
     * @param processInstance 流程实例对象
     * @param node            当前的人工任务节点对象
     */
    @Override
    public void beforeTaskCreate(Context context, ProcessInstance processInstance, TaskNode node) {

    }

    /**
     * 人工任务创建后触发该方法，此时人工任务虽然已创建，但还未持久化，<br>
     * 所以在该方法当前可以对任务属性进行修改，修改结果将影响人工任务的创建.
     * 需要注意的时，如果当前节点有多个任务产生（如会签），那么每个任务都会触发该方法调用.
     *
     * @param context 上下文对象
     * @param task    当前节点上产生的一个人工任务.
     */
    @Override
    public void onTaskCreate(Context context, Task task) {
        if (TaskType.Normal.equals(task.getType())) {
            Session session = context.getSession();
            TaskParticipator p=new TaskParticipator();
            p.setId(IDGenerator.getInstance().nextId());
            p.setTaskId(task.getId());
            p.setUser(task.getAssignee());
            session.save(p);
        }
        UfloCurrentTasks currentTask = currentTasksService.getObjByProperty("process_instance_id",task.getProcessInstanceId());
        if (currentTask!=null) {
            currentTask.setNext_node_name(task.getNodeName());
            currentTask.setNext_task_id(task.getId());
            currentTasksService.update(currentTask);
        } else {
            currentTask = new UfloCurrentTasks();
            currentTask.setProcess_id(task.getProcessId());
            currentTask.setProcess_instance_id(task.getProcessInstanceId());
            currentTask.setBusiness_id(task.getBusinessId());
            currentTask.setCurrent_node_name(null);
            currentTask.setNext_node_name(task.getNodeName());
            currentTask.setNext_status_code(NodeState.UN_CHECK.getCode());
            currentTask.setNext_task_id(task.getId());
            currentTask.setAction_type(ActionType.INPUT);
            currentTasksService.save(currentTask);
        }
    }

    /**
     * 当前节点上人工任务完成时将触发该方法调用，如果当前节点生成有多个任务（比如会签），那么每个任务完成时都会触发该方法调用.
     *
     * @param context 上下文对象
     * @param task    当前节点上一个已完成的人工任务.
     */
    @Override
    public void onTaskLeave(Context context, Task task, ActionType actionType) {
        UfloCurrentTasks currentTask = currentTasksService.getObjByProperty("process_instance_id",task.getProcessInstanceId());
        if (currentTask!=null) {
            if (ActionType.INPUT.equals(actionType)) {
                currentTask.setNext_status_code(NodeState.UN_CHECK.getCode());
                currentTask.setCurrent_status_code(NodeState.CHECK.getCode());
            } else if (ActionType.NEXT.equals(actionType)) {
                currentTask.setNext_status_code(NodeState.UN_CHECK.getCode());
                currentTask.setCurrent_status_code(NodeState.CHECK.getCode());
            } else if (ActionType.RECALL.equals(actionType)) {
                currentTask.setNext_status_code(NodeState.UN_CHECK.getCode());
                currentTask.setCurrent_status_code(NodeState.CHECK.getCode());
            } else if (ActionType.BACK.equals(actionType)) {
                currentTask.setNext_status_code(NodeState.FROM_BACK.getCode());
                currentTask.setCurrent_status_code(NodeState.BACK.getCode());
            } else if (ActionType.DISCARD.equals(actionType)) {
                currentTask.setNext_status_code(null);
                currentTask.setCurrent_status_code(NodeState.DISCARD.getCode());
            }
            currentTask.setCurrent_node_name(task.getNodeName());
            currentTask.setNext_node_name(null);
            currentTask.setAction_type(actionType);
            currentTask.setCurrent_task_id(task.getId());
        }

    }
}
