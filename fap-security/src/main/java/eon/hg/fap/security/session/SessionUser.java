package eon.hg.fap.security.session;

import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.support.session.UserContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SessionUser extends User implements OnlineUser {
    private String userid;

    private String cookie;

    private String nickname;

    private UserContext context = new UserContext();

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public UserContext getContext() {
        return context;
    }

    public void setContext(UserContext context) {
        this.context = context;
    }

    /**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     *
     * @param username
     * @param nickname
     * @param password
     * @param authorities
     */
    public SessionUser(String userid, String username, String nickname, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userid = userid;
        this.nickname = nickname;
    }

}
