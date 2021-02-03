package eon.hg.fap.support.session;

import eon.hg.fap.db.model.virtual.OnlineUser;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 当前登录的用户对象模型
 */
public class UserContext implements Serializable,Cloneable {

    private OnlineUser onlineUser;
    private Long menuId;
    private Integer year;
    // 用户其他信息的HashMap
    protected HashMap context = new HashMap();

    public OnlineUser getOnlineUser() {
        return onlineUser;
    }

    public void setOnlineUser(OnlineUser onlineUser) {
        this.onlineUser = onlineUser;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setAttribute(Object attrKey, Object attrValue) {
        if (context.containsKey(attrKey)) {
            context.remove(attrKey);
            context.put(attrKey, attrValue);
        } else {
            context.put(attrKey, attrValue);
        }
    }

    public Object getAttribute(Object attrKey) {
        if (context.containsKey(attrKey)) {
            return context.get(attrKey);
        } else
            return null;
    }

    public HashMap getContext() {
        return context;
    }

    public void setContext(HashMap context) {
        this.context = context;
        //System.out.println("set UserInfoContext:"+context.get("user_id"));
    }

}
