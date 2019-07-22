package eon.hg.fap.security.interceptor;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.db.model.primary.Res;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.service.IResService;
import eon.hg.fap.db.service.IRoleService;
import eon.hg.fap.security.SecurityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	protected final Log logger = LogFactory.getLog(getClass());

	private Map<String, Collection<ConfigAttribute>> urlAuthorities = new HashMap<String, Collection<ConfigAttribute>>();

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
		String requestURI = filterInvocation.getRequestUrl();
		boolean verify = true;
		// 这里绑定域名
		String serverName = filterInvocation.getHttpRequest().getServerName();
		if (!serverName.trim().equals("localhost")&&!CommUtil.isIp(serverName)) {
			if (!(serverName.indexOf(".") == serverName.lastIndexOf("."))) {
				serverName = serverName.substring(serverName.indexOf(".") + 1);
			}
			verify = CommUtil.cal_domain(serverName).equals(
					Globals.DEFAULT_BIND_DOMAIN_CODE);
		}
		verify = true;
		if (verify && requestURI.indexOf("login.htm") < 0) {
			//System.out.println("当前用户为:" + SecurityUserHolder.getCurrentUser());
			
			Iterator<String> iterator = urlAuthorities.keySet().iterator();
			while (iterator.hasNext()) {
				String url = iterator.next();
				RequestMatcher requestMatcher = new AntPathRequestMatcher(url, request.getMethod(), true);
				if (requestMatcher.matches(request)) {
					Collection<ConfigAttribute> confs = urlAuthorities.get(url);
					if (confs.size()>0) {
						return urlAuthorities.get(url);
					} else {
						return SecurityConfig.createList("online_error");
					}
				}
			}
		} else {
			if (requestURI.indexOf("login.htm") < 0) {
				filterInvocation.getHttpRequest().getSession()
						.setAttribute("domain_error", true);
				return SecurityConfig.createList("domain_error");
			}
		}

		return null;
	}

	public void loadUrlAuthorities() {
		setResetAuthorities(false);
		urlAuthorities.clear();
		Map<String, Object> params = new HashMap<>();
		params.put("type", "URL");
		List<Res> urlResources = this.resService.query(
				"select obj from Res obj where obj.type = :type", params, -1,
				-1);
		for (Res res : urlResources) {
            String[] permissions = StrUtil.split(res.getPermission(),",");
            urlAuthorities.put(res.getValue(), list2Collection(permissions));
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
