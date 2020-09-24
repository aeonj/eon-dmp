package eon.hg.fap.security.session;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import eon.hg.fap.core.beans.SpringUtils;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.db.dao.primary.UserDao;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.IUserService;
import eon.hg.fap.support.session.SessionPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class SessionPrincipalBean implements SessionPrincipal {

    @Resource
    private UserDao userDao;


    public OnlineUser getOnlineUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof OnlineUser) {
            return (OnlineUser)authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Returns the current user
     *
     * @return
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else if (authentication != null
                && authentication.getPrincipal() instanceof OnlineUser) {
            OnlineUser token_user = (OnlineUser) authentication.getPrincipal();
            User user = userDao.get(Long.valueOf(token_user.getUserid()));
            return user;
        } else if (authentication != null
                && authentication.getPrincipal() instanceof String) {
            String username = (String) authentication.getPrincipal();
            if ("anonymousUser".equals(username))
                return null;
            UserDetailsService userService = SpringUtils.getBean("securityUserSupport",UserDetailsService.class);
            User user = (User) userService.loadUserByUsername(username);
            return user;
        } else {
            User user = null;
            if (RequestContextHolder.getRequestAttributes() != null) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getRequest();
                user = (request.getSession().getAttribute("user") != null ? (User) request
                        .getSession().getAttribute("user") : null);
                // System.out.println(user != null ? user.getUserName() : "空");
            }
            return user;
        }

    }

}
