package eon.hg.fap.security.interceptor;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.db.model.primary.Res;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.service.IResService;
import eon.hg.fap.db.service.IRoleService;
import eon.hg.fap.security.SecurityManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 系統权限核心管理类，使用SpringSecurity完成角色、权限拦截
 * @author AEON
 *
 */
@Component
public class SecureResourceFilterMetadataSource implements
        FilterInvocationSecurityMetadataSource, InitializingBean, SecurityManager {
	@Autowired
	private IResService resService;
	@Autowired
	private IRoleService roleService;

	private Map<String, Collection<ConfigAttribute>> urlAuthorities = new HashMap<>();

	private boolean reset_flag = false;
	
	@Override
	public void afterPropertiesSet() {
		loadUrlAuthorities();
	}
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> attributes = new HashSet<>();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : urlAuthorities.entrySet()) {
            attributes.addAll(entry.getValue());  
        }  
        return attributes; 
	}

	/**
	 * 接收用户请求的地址，返回访问该地址需要的所有角色
	 * @param filter
	 * @return
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object filter) {
		FilterInvocation filterInvocation = (FilterInvocation) filter;
		final HttpServletRequest request = filterInvocation.getHttpRequest();

		Iterator<String> iterator = urlAuthorities.keySet().iterator();
		while (iterator.hasNext()) {
			String url = iterator.next();
			RequestMatcher requestMatcher = new AntPathRequestMatcher(url, request.getMethod(), true);
			if (requestMatcher.matches(request)) {
				Collection<ConfigAttribute> confs = urlAuthorities.get(url);
				if (confs.size() > 0) {
					return urlAuthorities.get(url);
				} else {
					return SecurityConfig.createList("online_error");
				}
			}
		}

		return null;
	}

	public void loadUrlAuthorities() {
		setResetAuthorities(false);
		urlAuthorities.clear();
		Map<String, Object> params = new HashMap<>();
		//session和token方式加载资源角色权限暂时分开 2019.8.11
		if (Globals.AUTH_TYPE.equals("session")) {
			params.put("type", "URL");
			List<Res> urlResources = this.resService.query(
					"select obj from Res obj where obj.type = :type", params, -1,
					-1);
			for (Res res : urlResources) {
				String[] permissions = StrUtil.split(res.getPermission(), ",");
				urlAuthorities.put(res.getValue(), list2Collection(permissions));
			}
		} else {
			params.put("type", "ANY");
			List<Res> urlResources = this.resService.query(
					"select obj from Res obj where obj.type = :type", params, -1,
					-1);
			for (Res res : urlResources) {
				urlAuthorities.put(res.getValue(), list2Collection(res.getRoles()));
			}
		}
	}

	/** 
     * 将List<Role>集合转换为框架需要的Collection<ConfigAttribute>集合 
     *  
     * @param roles 
     * @return Collection<ConfigAttribute> 
     */  
    private Collection<ConfigAttribute> list2Collection(List<Role> roles) {
        List<ConfigAttribute> list = new ArrayList<>();
        for (Role role : roles)  
            list.add(new SecurityConfig(role.getAuthCode()));
        return list;  
    }

    private Collection<ConfigAttribute> list2Collection(String[] permissions) {
        List<ConfigAttribute> list = new ArrayList<>();
        if (permissions!=null) {
            for (String permission : permissions) {
                Map<String, Object> params = new HashMap<>();
                String[] keys = StrUtil.split(permission, ":");
                params.put("auth_key", permission);
                List<Role> roles;
                if (keys.length<=1 || "view".equals(keys[1])) {
					roles = this.roleService.query("select role from Role as role join fetch role.menus as menu where menu.authkey=:auth_key", params, -1, -1);
                } else {
					roles = this.roleService.query("select role from Role as role join fetch role.operates as r_operate join fetch r_operate.operate as operate where operate.authkey=:auth_key", params, -1, -1);
				}
                for (Role role : roles) {
                    list.add(new SecurityConfig(role.getAuthCode()));
                }
            }
        }
        return list;
    }

	/**
	 * 
	 * @param filterInvocation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getUrlAuthorities(
			FilterInvocation filterInvocation) {
		ServletContext servletContext = filterInvocation.getHttpRequest()
				.getSession().getServletContext();
		return (Map<String, String>) servletContext
				.getAttribute("urlAuthorities");
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public boolean isResetAuthorities() {
		return reset_flag;
	}

	@Override
	public void setResetAuthorities(boolean reset_flag) {
		this.reset_flag=reset_flag;
	}  

}
