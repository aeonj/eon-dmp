package eon.hg.fap.db.model.virtual;

import java.io.Serializable;
import java.util.Collection;


public interface OnlineUser extends Serializable,Cloneable {

    public String getUserid();

    public void setUserid(String userid);

    public String getNickname();

    public void setNickname(String nickname);

    public String getCookie();

    public void setCookie(String cookie);

    public Object getContext();

    public void setContext(Object context);

    String getPassword();

    String getUsername();

}
