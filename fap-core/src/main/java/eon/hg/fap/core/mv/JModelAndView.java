package eon.hg.fap.core.mv;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.HttpInclude;
import eon.hg.fap.db.model.primary.SysConfig;
import eon.hg.fap.db.model.primary.UserConfig;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 顶级视图管理类，封装ModelAndView并进行系统扩展
 * @author AEON
 *
 */
public class JModelAndView extends ModelAndView {
	/**
	 * 普通视图，根据velocity配置文件的路径直接加载视图
	 * 
	 * @param viewName
	 *            视图名称
	 */
	public JModelAndView(String viewName) {
		super.setViewName(viewName);
	}

	/**
	 * 
	 * @param viewName
	 *            用户自定义的视图，可以添加任意路径
	 * @param request
	 */
	public JModelAndView(String viewName, SysConfig config, UserConfig uconfig,
						 HttpServletRequest request, HttpServletResponse response) {
		String contextPath = request.getContextPath().equals("/") ? ""
				: request.getContextPath();
		String webPath = CommUtil.getURL(request);
		super.addObject("current_webPath", webPath);
		String port = request.getServerPort() == 80
				|| request.getServerPort() == 443 ? "" : ":"
				+ CommUtil.null2Int(request.getServerPort());
		if (Globals.SSO_SIGN && config.isSecond_domain_open()
				&& !CommUtil.generic_domain(request).equals("localhost")
				&& !CommUtil.isIp(request.getServerName())) {
			webPath = (request.isSecure() ? "https://" : "http://") + "www."
					+ CommUtil.generic_domain(request) + port + contextPath;
		}
		super.setViewName(viewName);
		super.addObject("domainPath", CommUtil.generic_domain(request));
		if (config.getImageWebServer() != null
				&& !config.getImageWebServer().equals("")) {
			super.addObject("imageWebServer", config.getImageWebServer());
		} else {
			super.addObject("imageWebServer", webPath);
		}
		super.addObject("servletPath", request.getServletPath());
		super.addObject("webPath", webPath);
		super.addObject("config", config);
		super.addObject("uconfig", uconfig);
		super.addObject("user", SecurityUserHolder.getCurrentUser());
		super.addObject("rgcode", SecurityUserHolder.getRgCode());
		super.addObject("httpInclude", new HttpInclude(request, response));
		String query_url = "";
		if (request.getQueryString() != null
				&& !request.getQueryString().equals("")) {
			query_url = "?" + request.getQueryString();
		}
		super.addObject("current_url", request.getRequestURI() + query_url);
		boolean second_domain_view = false;
		String serverName = request.getServerName().toLowerCase();
		if (serverName.indexOf("www.") < 0 && serverName.indexOf(".") >= 0
				&& serverName.indexOf(".") != serverName.lastIndexOf(".")
				&& config.isSecond_domain_open()) {
			String secondDomain = serverName.substring(0,
					serverName.indexOf("."));
			second_domain_view = true;// 使用二级域名访问，相关js url需要处理，避免跨域
			super.addObject("secondDomain", secondDomain);
		}
		super.addObject("second_domain_view", second_domain_view);
	}

	/**
	 * 按指定路径加载视图，如不指定则系统默认路径加载
	 * 
	 * @param viewName
	 *            视图名称
	 * @param config
	 *            系统配置
	 * @param uconfig
	 *            用户配置
	 * @param type
	 *            视图类型 0为后台，1为前台 大于1为自定义路径
	 */
	public JModelAndView(String viewName, SysConfig config, UserConfig uconfig,
                         int type, HttpServletRequest request, HttpServletResponse response) {
		if (config.getSysLanguage() != null) {
			if (config.getSysLanguage().equals(Globals.DEFAULT_SYSTEM_LANGUAGE)) {
				if (type == 0) {
					super.setViewName(Globals.SYSTEM_MANAGE_PAGE_PATH
							+ viewName);
				}
				if (type == 1) {
					super.setViewName(Globals.SYSTEM_FORNT_PAGE_PATH + viewName);
				}
				if (type > 1) {
					super.setViewName(viewName);
				}

			} else {
				if (type == 0) {
					super.setViewName(Globals.DEFAULT_SYSTEM_PAGE_ROOT
							+ config.getSysLanguage() + "/system/" + viewName);
				}
				if (type == 1) {
					super.setViewName(Globals.DEFAULT_SYSTEM_PAGE_ROOT
							+ config.getSysLanguage() + "/client/" + viewName);
				}
				if (type > 1) {
					super.setViewName(viewName);
				}
			}
		} else {
			super.setViewName(viewName);
		}
		super.addObject("CommUtil", new CommUtil());
		String contextPath = request.getContextPath().equals("/") ? ""
				: request.getContextPath();
		String webPath = CommUtil.getURL(request);
		String port = request.getServerPort() == 80
				|| request.getServerPort() == 443 ? "" : ":"
				+ CommUtil.null2Int(request.getServerPort());
		super.addObject("current_webPath", webPath);
		if (Globals.SSO_SIGN && config.isSecond_domain_open()
				&& !CommUtil.generic_domain(request).equals("localhost")
				&& !CommUtil.isIp(request.getServerName())) {
			webPath = (request.isSecure() ? "https://" : "http://") + "www."
					+ CommUtil.generic_domain(request) + port + contextPath;
		}
		super.addObject("domainPath", CommUtil.generic_domain(request));
		super.addObject("webPath", webPath);
		super.addObject("servletPath", request.getServletPath());
		if (config.getImageWebServer() != null
				&& !config.getImageWebServer().equals("")) {
			super.addObject("imageWebServer", config.getImageWebServer());
		} else {
			super.addObject("imageWebServer", webPath);
		}
		String routePath = getViewName().substring(0,getViewName().lastIndexOf("/"));
		super.addObject("routePath",webPath+routePath);
		super.addObject("config", config);
		super.addObject("uconfig", uconfig);
		super.addObject("user", SecurityUserHolder.getCurrentUser());
		super.addObject("rgcode", SecurityUserHolder.getRgCode());
		super.addObject("httpInclude", new HttpInclude(request, response));
		String query_url = "";
		if (request.getQueryString() != null
				&& !request.getQueryString().equals("")) {
			query_url = "?" + request.getQueryString();
		}
		super.addObject("current_url", request.getRequestURI() + query_url);
		boolean second_domain_view = false;
		String serverName = request.getServerName().toLowerCase();
		if (serverName.indexOf("www.") < 0 && serverName.indexOf(".") >= 0
				&& serverName.indexOf(".") != serverName.lastIndexOf(".")
				&& config.isSecond_domain_open()) {
			String secondDomain = serverName.substring(0,
					serverName.indexOf("."));
			second_domain_view = true;// 使用二级域名访问，相关js url需要处理，避免跨域
			super.addObject("secondDomain", secondDomain);
		}
		super.addObject("second_domain_view", second_domain_view);
	}

	public JModelAndView(String viewName, int type) {
		if (type == 0) {
			super.setViewName(Globals.SYSTEM_MANAGE_PAGE_PATH
					+ viewName);
		}
		if (type == 1) {
			super.setViewName(Globals.SYSTEM_FORNT_PAGE_PATH + viewName);
		}
		if (type > 1) {
			super.setViewName(viewName);
		}
	}
}
