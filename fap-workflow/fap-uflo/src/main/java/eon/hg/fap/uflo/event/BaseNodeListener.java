package eon.hg.fap.uflo.event;

import cn.hutool.core.convert.Convert;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.Assignee;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.TaskNode;
import eon.hg.fap.db.model.primary.UfloCurrentTasks;
import eon.hg.fap.db.model.primary.UfloNodeUsers;
import eon.hg.fap.db.service.IUfloNodeUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 通用节点事件触发，针对于人工任务
 */
@Service
public class BaseNodeListener implements NodeEventHandler {
    @Autowired
    private IUfloNodeUsersService nodeUsersService;
    @Autowired
    @Qualifier("roleAssigneeProvider")
    private AssigneeProvider assigneeProvider;

    /**
     * 进入节点后触发的方法
     *
     * @param node            当前节点对象
     * @param processInstance 当前流程实例对象
     * @param context         流程上下文
     */
    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        if (assigneeProvider!=null) {
            if (node instanceof TaskNode) {
                TaskNode taskNode = (TaskNode) node;
                for (Assignee assignee : taskNode.getAssignees()) {
                    Collection<String> users = assigneeProvider.getUsers(assignee.getId(),context,processInstance);
                    for (String user_id : users) {
                        UfloNodeUsers vf = nodeUsersService.getObjByProperty("process_instance_id",processInstance.getId(),"node_name",node.getName(),"user_id", Convert.toLong(user_id));
                        if (vf==null) {
                            UfloNodeUsers nodeUsers = new UfloNodeUsers();
                            nodeUsers.setProcess_instance_id(processInstance.getId());
                            nodeUsers.setNode_name(node.getName());
                            nodeUsers.setUser_id(Convert.toLong(user_id));
                            nodeUsersService.save(nodeUsers);
                        }
                    }
                }
            }
        }
    }

    /**
     * 离开节点后触发的方法
     *
     * @param node            当前节点对象
     * @param processInstance 当前流程实例对象
     * @param context         流程上下文
     */
    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {

    }
}
