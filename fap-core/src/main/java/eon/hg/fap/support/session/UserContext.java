package eon.hg.fap.support.session;

import java.io.Serializable;

/**
 * 当前登录的用户对象模型
 */
public class UserContext implements Serializable,Cloneable {

    private String userid;
    private String username;
    private String nickname;
    private Long menuId;

}
