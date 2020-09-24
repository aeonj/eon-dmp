package eon.hg.fap.core.security;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import eon.hg.fap.core.beans.SpringUtils;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.support.session.SessionPrincipal;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * SpringSecurity用户获取工具类，该类的静态方法可以直接获取已经登录的用户信息 
 * @author AEON
 *
 */
public class SecurityUserHolder {
    private static SessionPrincipal principal;

	public static OnlineUser getOnlineUser() {
		if (principal==null) {
			principal = SpringUtils.getBean("sessionPrincipal", SessionPrincipal.class);
			if (principal != null) {
				return principal.getOnlineUser();
			}
		}
		return null;
	}

	public static IdEntity getCurrentUser() {
		if (principal==null) {
			principal = SpringUtils.getBean("sessionPrincipal", SessionPrincipal.class);
			if (principal != null) {
				return principal.getCurrentUser();
			}
		}
		return null;
	}

	public static String getSetYear() {
		return Convert.toStr(DateUtil.thisYear());
	}

	/**
	 * 获取当前登录用户的所在区划
	 * @return
	 */
	public static String getRgCode() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			if (request.getSession().getAttribute("rgCode") != null) {
				return (String) request.getSession().getAttribute("rgCode");
			}
		}
		return AeonConstants.SUPER_RG_CODE;
	}
	/**
	 * 获取当前登录用户的所在区划及下级区划，用于人行系统
	 * @return
	 */
	public static String getRgCodes() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			if (request.getSession().getAttribute("rgCodes") != null) {
				return (String) request.getSession().getAttribute("rgCodes");
			}
		}
		return AeonConstants.SUPER_RG_CODE;
	}
	/**
	 * 获取当前登录用户的所在区划对应的授权区划，用于人行系统
	 * @return
	 */
	public static String getRgCodeExts() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			if (request.getSession().getAttribute("rgCodeExts") != null) {
				return (String) request.getSession().getAttribute("rgCodeExts");
			}
		}
		return AeonConstants.SUPER_RG_CODE;
	}
}
