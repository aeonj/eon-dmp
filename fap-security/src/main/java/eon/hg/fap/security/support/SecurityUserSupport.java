package eon.hg.fap.security.support;

import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 用户登录管理器，用户名、密码验证完成后进入该验证器，该验证器用来获取用户权限获取及外部系统同步登录,该控制用来控制用户权限加载，用户从前台登录
 * ，默认登陆时候loginRole为public，此时只载入用户非MANAGE类型权限,用户从超级管理登陆页面登录，页面中包含表单字段loginRole=
 * MANAGE，此时加载用户所有角色权限，也就是说从前端登陆的用户无法操作超级管理内容
 * ，只有从超级管理登录页面登录的用户且该用户拥有超级管理权限，登录后才可以操作超级管理内容
 * @author AEON
 * 
 */
@Component
public class SecurityUserSupport implements UserDetailsService {
	@Autowired
	private IUserService userService;

	public UserDetails loadUserByUsername(String data)
			throws UsernameNotFoundException, DataAccessException {
		String[] list = data.split(",");
		String userName = list[0];
		String loginRole = "public";
		if (list.length == 2) {
			loginRole = list[1];
		}
        if (AeonConstants.SUPER_USER.equals(userName)) {
            User user = this.userService.getObjByProperty(null,"userName",userName);
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>() {{
                add(new SimpleGrantedAuthority("ROLE_ADMIN_SUPER"));
            }};
            user.setAuthorities(authorities);
            OnlineUser onlineUser = new OnlineUser(String.valueOf(user.getId()),user.getUsername(),user.getPassword(),user.getAuthorities());
            return onlineUser;
        } else {
            Map params = new HashMap();
            params.put("userName", userName);
            params.put("email", userName);
            params.put("mobile", userName);
            List<User> users = this.userService
                    .query("select obj from User obj join fetch obj.roles where obj.userName =:userName or obj.email=:email or obj.mobile=:mobile",
                            params, -1, -1);
            if (users.isEmpty()) {
                throw new UsernameNotFoundException("User " + userName
                        + " has no GrantedAuthority");
            }
            User user = users.get(0);
            //以下处理当前登录用户实际拥有的权限(根据用户请求的资源类别)
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            if (!user.getRoles().isEmpty() && user.getRoles() != null) {
                Iterator<Role> roleIterator = user.getRoles().iterator();
                while (roleIterator.hasNext()) {
                    Role role = roleIterator.next();
                    //根据类型加载用户权限
                    if (loginRole.equalsIgnoreCase("MANAGE")) {
                        if (role.getType().equals("MANAGE")) {
                            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                                    role.getAuthCode());
                            authorities.add(grantedAuthority);
                        }
                    } else {
                        if (loginRole.equalsIgnoreCase("PUBLIC")) {
                            if (role.getType().equals("PUBLIC")) {
                                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                                        role.getAuthCode());
                                authorities.add(grantedAuthority);
                            }
                        }
                    }
                }
            }
            user.setAuthorities(authorities);
            OnlineUser onlineUser = new OnlineUser(String.valueOf(user.getId()),user.getUsername(),user.getPassword(),user.getAuthorities());
            return onlineUser;
        }
	}

}
