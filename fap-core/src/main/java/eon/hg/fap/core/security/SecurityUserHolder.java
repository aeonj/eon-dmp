package eon.hg.fap.core.security;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.db.model.primary.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * SpringSecurity用户获取工具类，该类的静态方法可以直接获取已经登录的用户信息 
 * @author AEON
 *
 */
public class SecurityUserHolder {

	/**
	 * Returns the current user
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal() instanceof User) {

			return (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
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
