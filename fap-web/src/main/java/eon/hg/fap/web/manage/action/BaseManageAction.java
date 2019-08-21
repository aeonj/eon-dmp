package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.annotation.Log;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.LogType;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.primary.UserConfig;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.db.service.IUserService;
import eon.hg.fap.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 平台管理基础控制，这里包含平台管理的基础方法、系统全局配置信息的保存、修改及一些系统常用请求
 * @author AEON
 *
 */
@Controller
public class BaseManageAction extends BizAction{
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IUserService userService;
	@Autowired
	SecurityManager securityManager;

	@RequestMapping("/")
    public String index() {
		if (Globals.DEFAULT_SYS_TYPE) {
			return "redirect:manage/index.htm";
		} else {
			return "redirect:index.htm";
		}
    }

	/**
	 * 用户登录后去向控制，根据用户角色UserRole进行控制,该请求不纳入权限管理
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Log(title = "用户登陆", type = LogType.LOGIN)
	@RequestMapping("/login_success.htm")
	public void login_success(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (SecurityUserHolder.getOnlineUser() != null) {
			User user = this.userService.getObjById(CommUtil.null2Long(SecurityUserHolder
					.getOnlineUser().getUserid()));
			user.setLoginDate(new Date());
			user.setLoginIp(CommUtil.getIpAddr(request));
			user.setLoginCount(user.getLoginCount() + 1);
			//此处在并发用户(同一用户在不同地址登录)比较多的情况下可能会更新失败 Row was updated or deleted by another transaction by aeon 2015.9.15
			this.userService.update(user);
			
			//重新加载权限
			if (this.securityManager.isResetAuthorities()) {
				this.securityManager.loadUrlAuthorities();
			}
			HttpSession session = request.getSession(false);
			session.setAttribute("user", user);
			session.setAttribute("userName", user.getUsername());
			session.setAttribute("rgCode", user.getRg_code());
			session.setAttribute("lastLoginDate", new Date());// 设置登录时间
			session.setAttribute("loginIp", CommUtil.getIpAddr(request));// 设置登录IP
			session.setAttribute("login", true);// 设置登录标识
			String role = user.getUserRole();
			String url = CommUtil.getURL(request) + "/user_login_success.htm";
			if (!CommUtil.null2String(
					request.getSession(false).getAttribute("refererUrl"))
					.equals("")) {
				url = CommUtil.null2String(request.getSession(false)
						.getAttribute("refererUrl"));
			}
			String login_role = (String) session.getAttribute("login_role");
			//String login_role = user.getUserRole();
			boolean ajax_login = CommUtil.null2Boolean(session
					.getAttribute("ajax_login"));
			if (ajax_login) {
				write("success",response);
			} else {
				if (login_role.equalsIgnoreCase("manage") && role.indexOf("MANAGE") >= 0) {
					url = CommUtil.getURL(request) + "/manage/index.htm";
					request.getSession(false).setAttribute("admin_login",
							true);
					Map dto = new HashMap();
					dto.put("success", true);
					dto.put("msg", "用户登录成功！");
					dto.put("url", url);
					dto.put("user_id", user.getId());
					write(JsonHandler.toJson(dto),response);
					return;
				} else if (!CommUtil.null2String(
						request.getSession(false).getAttribute("refererUrl"))
						.equals("")) {
					url = CommUtil.null2String(request.getSession(false)
							.getAttribute("refererUrl"));
					request.getSession(false).removeAttribute("refererUrl");
				}
				
				String userAgent = request.getHeader("user-agent");
				if (userAgent != null && userAgent.indexOf("Mobile") > 0) {
					url = CommUtil.getURL(request) + "/wap/index.htm?type=login";
				}
				response.sendRedirect(url);
			}
		} else {
			String url = CommUtil.getURL(request) + "/index.htm";
			response.sendRedirect(url);
		}

	}

	/**
	 * 用户成功退出后的URL导向
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/logout_success.htm")
	public void logout_success(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
//		boolean admin_login = CommUtil.null2Boolean(session
//				.getAttribute("admin_login"));
		boolean admin_login = true;
		String targetUrl = CommUtil.getURL(request) + "/user/login.htm";
		if (admin_login) {
			targetUrl = CommUtil.getURL(request) + "/manage/login.htm";
		}
		String userAgent = request.getHeader("user-agent");
		if (userAgent != null && userAgent.indexOf("Mobile") > 0) {
			targetUrl = CommUtil.getURL(request) + "/wap/index.htm";
		}
		response.sendRedirect(targetUrl);
	}

	/**
	 * 根据重定向登录标识，区分跳转和报错页面
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author AEON
	 */
	@RequestMapping("/login_error.htm")
	public void login_error(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (request.getSession(false).getAttribute("redirectlogin")!=null) {
			String redirectlogin = request.getSession(false).getAttribute("redirectlogin").toString();
			request.getSession(false).removeAttribute("redirectlogin");
			response.sendRedirect(redirectlogin);
		} else {
			response.sendRedirect(CommUtil.getURL(request) + "/login_error_local.htm");
		}
	}
	
	@RequestMapping("/login_error_local.htm")
	public ModelAndView login_error_local(HttpServletRequest request,
                                          HttpServletResponse response) {
		String login_role = (String) request.getSession(false).getAttribute(
				"login_role");
		ModelAndView mv = null;
		String aeon_view_type = CommUtil.null2String(request.getSession(
				false).getAttribute("aeon_view_type"));
		//如果是wap端,跳转错误页面,重新登陆
		String userAgent = request.getHeader("user-agent");
		if (userAgent != null && userAgent.indexOf("Mobile") > 0) {
					 mv = new JModelAndView("wap/common_error.html",
								configService.getSysConfig(),
								this.userConfigService.getUserConfig(), 1, request, response);
					 mv.addObject("op_title", "登陆失败,请重新登陆");
					 mv.addObject("url", CommUtil.getURL(request)
								+ "/wap/pre_waplogin.htm");
					return mv;
			}
		if (aeon_view_type != null && !aeon_view_type.equals("")) {
			if (aeon_view_type.equals("weixin")) {
				String store_id = CommUtil.null2String(request
						.getSession(false).getAttribute("store_id"));
				mv = new JModelAndView("weixin/error.html",
						configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request,
						response);
				mv.addObject("url", CommUtil.getURL(request)
						+ "/weixin/index.htm?store_id=" + store_id);
			}
		} else {
			if (login_role == null)
				login_role = "public";
			if (login_role.equalsIgnoreCase("manage")) {
				Map dto = new HashMap();
				dto.put("success", false);
				dto.put("msg", "用户名或密码错误！");
				dto.put("errorType", 3);
				write(JsonHandler.toJson(dto),response);
				return null;
			}
			if (login_role.equalsIgnoreCase("public")) {
				mv = new JModelAndView("error.html",
						configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request,
						response);
				mv.addObject("url", CommUtil.getURL(request)
						+ "/user/login.htm");
			}
		}
		mv.addObject("op_title", "登录失败");
		return mv;
	}
	
	@RequestMapping("/third_login.htm")
	public void third_login(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (SecurityUserHolder.getOnlineUser()==null) {
			request.getSession(false).removeAttribute("verify_code");
			request.getSession(false).removeAttribute("bind");
			response.sendRedirect(CommUtil.getURL(request)+"/iaeon_login.htm?username="
					+ CommUtil.encode(request.getParameter("username"))
					+ "&password=" + Globals.THIRD_ACCOUNT_LOGIN + request.getParameter("password")+"&login_role="+request.getParameter("login_role")+"&synclogin=0");
		}
	}
	
	@RequestMapping("/third_logout.htm")
	public void third_logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (SecurityUserHolder.getOnlineUser()!=null) {
			response.sendRedirect(CommUtil.getURL(request)+"/ixingyun_logout.htm?synclogout=0");
		}
	}
	
	@RequestMapping("/manage/switch_elecode.htm")
	public void switch_elecode(HttpServletRequest request,
			HttpServletResponse response) {
		OnlineUser user = SecurityUserHolder.getOnlineUser();
		if (user!=null) {
			UserConfig uconfig = this.userConfigService.getUserConfig();
			if (CommUtil.null2String(uconfig.getElecode_type()).equals("standard")) {
				uconfig.setElecode_type("normal");
			} else {
				uconfig.setElecode_type("standard");
			}
			this.userConfigService.update(uconfig);
		}
	}

	@RequestMapping("/manage/aboutus.htm")
	public ModelAndView aboutus(HttpServletRequest request,
                                HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("aboutus.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		return mv;
	}

	/**
	 * 管理员退出，清除管理员权限数据,退出后，管理员可以作为普通登录用户进行任意操作，该请求在前台将不再使用，保留以供二次开发使用
	 * 
	 * @return
	 */
	@RequestMapping("/manage/logout.htm")
	public String logout() {
		return "redirect:login.htm";
	}

	/**
	 * 登录页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/manage/login.htm")
	public ModelAndView login(HttpServletRequest request,
                              HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("login.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		request.getSession(false).removeAttribute("verify_code");
		return mv;
	}

	@RequestMapping("/success.htm")
	public ModelAndView success(HttpServletRequest request,
                                HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("success.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		mv.addObject("op_title",
				request.getSession(false).getAttribute("op_title"));
		mv.addObject("url", request.getSession(false).getAttribute("url"));
		request.getSession(false).removeAttribute("op_title");
		request.getSession(false).removeAttribute("url");
		return mv;
	}

	/**
	 * 默认错误页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/error.htm")
	public ModelAndView error(HttpServletRequest request,
                              HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("error.html",
					configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 0, request,
					response);

		mv.addObject("op_title",
				request.getSession(false).getAttribute("op_title"));
		mv.addObject("list_url", request.getSession(false).getAttribute("url"));
		mv.addObject("url", request.getSession(false).getAttribute("url"));
		request.getSession(false).removeAttribute("op_title");
		request.getSession(false).removeAttribute("url");
		return mv;
	}

	/**
	 * 默认异常出现
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exception.htm")
	public ModelAndView exception(HttpServletRequest request,
                                  HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mv = new JModelAndView("error.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		if (user != null && user.getUserRole().equalsIgnoreCase("MANAGE")) {
			mv = new JModelAndView("exception.html",
					configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 0, request,
					response);
		} else {
			mv.addObject("op_title", "系统出现异常");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	/**
	 * 超级后台默认无权限页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/authority.htm")
	public ModelAndView authority(HttpServletRequest request,
                                  HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("authority.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		boolean domain_error = CommUtil.null2Boolean(request.getSession(false)
				.getAttribute("domain_error"));
		if (domain_error) {
			mv = new JModelAndView("error.html", configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "域名绑定错误，请与aeonj@163.com联系");
		}
		return mv;
	}

	/**
	 * flash获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getCode.htm")
	public void getCode(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession(false);
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();
		writer.print("result=true&code="
				+ (String) session.getAttribute("verify_code"));
	}


	@RequestMapping("/js.htm")
	public ModelAndView js(HttpServletRequest request,
                           HttpServletResponse response, String js) {
		ModelAndView mv = new JModelAndView("resources/js/" + js + ".js",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 2, request, response);
		return mv;
	}

}
