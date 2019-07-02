/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年12月30日
 * @author cxj
 */

package eon.hg.fap.security.access;

import eon.hg.fap.core.constant.AeonConstants;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 自定义权限验证，根据资源角色投票通过
 * 根据当前用户的信息，和目标url涉及到的角色权限，判断用户是否可以访问
 * @author aeon
 *
 */
@Component
public class EonAccessDecisionManager implements AccessDecisionManager {
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes)  
            return;
        if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (AeonConstants.SUPER_USER.equals(userDetails.getUsername())) {
				return;
			}
		}
		//迭代器遍历目标url的权限列表
        for (ConfigAttribute attribute : configAttributes) {
            String needRole = attribute.getAttribute();
            // authority为用户所被赋予的权限, needRole 为访问相应的资源应该具有的权限。  
            for (GrantedAuthority grantedAuthority : authentication
                    .getAuthorities()) {  
                if (needRole.equals(grantedAuthority.getAuthority()))  
                    return;  
            }  
        }  
        throw new AccessDeniedException(messages.getMessage(
				"DnsAccessDecisionManager.accessDenied", "访问被拒绝，权限不足"));

	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.ConfigAttribute)
	 */
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
