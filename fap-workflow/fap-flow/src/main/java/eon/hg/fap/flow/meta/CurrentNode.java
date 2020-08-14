package eon.hg.fap.flow.meta;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.core.exception.ResultException;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.model.virtual.CurrentMenuParam;
import eon.hg.fap.db.model.virtual.OnlineUser;

import java.util.HashMap;
import java.util.Map;

public class CurrentNode extends CurrentMenuParam {
    private static Map<Long,CurrentNode> nodes = new HashMap<>();
    private String flowId;
    private String flowName;
    private String flowKey;
    private String nodeName;
    private String userId;
    private NodeState state;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    public static CurrentNode menuInstance(Menu menu) {
        if (menu == null) {
            throw new ResultException("未获取到当前菜单！");
        }
        OnlineUser user = SecurityUserHolder.getOnlineUser();
        if (user == null) {
            throw new ResultException("未能获取到当前登录用户！");
        }
        if (CurrentNode.nodes.containsKey(menu.getId())) {
            return CurrentNode.nodes.get(menu.getId());
        } else {
            CurrentNode node = new CurrentNode();
            if (StrUtil.isNotBlank(menu.getJson_obj())) {
                Map dtoParam = JsonHandler.parseObject(menu.getJson_obj(), Map.class);
                if (dtoParam.containsKey("flow")) {
                    Map mapFlow = (Map) dtoParam.get("flow");
                    BeanUtil.fillBeanWithMap(mapFlow, node, false);
                    CurrentNode.nodes.put(menu.getId(), node);
                }
            }
            return node;
        }
    }

    public CurrentNode addState(NodeState state) {
        this.state = state;
        return this;
    }
}
