package eon.hg.fap.db.model.virtual;

import eon.hg.fap.support.session.UserContext;

import java.io.Serializable;


public interface OnlineUser extends Serializable,Cloneable {

    public String getUserid();

    public void setUserid(String userid);

    public String getNickname();

    public void setNickname(String nickname);

    public String getCookie();

    public void setCookie(String cookie);

    public UserContext getContext();

    public void setContext(UserContext context);

    String getPassword();

    String getUsername();

}
