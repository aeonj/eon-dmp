package eon.hg.fap.web.manage.action;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.properties.ExternalProperties;
import eon.hg.fap.common.properties.SecurityProperties;
import eon.hg.fap.core.annotation.Log;
import eon.hg.fap.core.constant.AeonConstants;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 平台管理基础控制，这里包含平台管理的基础方法、系统全局配置信息的保存、修改及一些系统常用请求
 * @author AEON
 *
 */
@Slf4j
@RestController
public class BaseManageAction extends BizAction{
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IUserService userService;
	@Autowired
	private SecurityManager securityManager;
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ExternalProperties externalProperties;
	@Autowired
	private HttpSession httpSession;
	//重定向策略
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	//请求缓存session
	private RequestCache requestCache = new HttpSessionRequestCache();


	@RequestMapping("/")
    public void index(HttpServletRequest request,
						HttpServletResponse response) throws IOException {
		if (Globals.DEFAULT_SYS_TYPE) {
			redirectStrategy.sendRedirect(request, response, "manage/index.htm");
		} else {
			redirectStrategy.sendRedirect(request, response, securityProperties.getIndex_url());
		}
    }

    @RequestMapping("/login.htm")
	public void login(HttpServletRequest request,
							HttpServletResponse response) throws IOException{
		SavedRequest savedRequest = requestCache.getRequest(request,response);
		if (savedRequest != null){
			//获取 跳转url
			String targetUrl = savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是:"+targetUrl);
			boolean isAjaxRequest = false;
			if(savedRequest.getHeaderNames().contains("x-requested-with") && "xmlhttprequest".equalsIgnoreCase(savedRequest.getHeaderValues("x-requested-with").get(0))) {
                isAjaxRequest = true;
			}
			HttpSession session = request.getSession(false);
			session.setAttribute("ajax_login", isAjaxRequest);


			if (targetUrl.indexOf("/manage/") >= 0) {//判断是否为后台业务管理请求
				targetUrl = "/manage/login.htm";
				redirectStrategy.sendRedirect(request,response,targetUrl);
			} else {
				targetUrl = securityProperties.getLogin_url();
				redirectStrategy.sendRedirect(request,response,targetUrl);
			}
		} else {
            if (isAjaxRequest(request)) {
                response.setStatus(AeonConstants.Ajax_Timeout);
                setErrTipMsg("登录超时！",response);
            } else {
                redirectStrategy.sendRedirect(request, response, securityProperties.getLogin_url());
            }
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
			//此处在并发用户(同一用户在不同地址登录)比较多的情况下可能会更新失败 Row was updated or deleted by another transaction by aeon 2015.9.15
			this.userService.update(user);

			//重新加载权限
			if (this.securityManager.isResetAuthorities()) {
				this.securityManager.loadUrlAuthorities();
			}
			HttpSession session = request.getSession(false);
			session.setAttribute("user", user);
			session.setAttribute("rgCode", user.getRg_code());
			//获取 跳转url
			String targetUrl = securityProperties.getIndex_url();
			SavedRequest savedRequest = requestCache.getRequest(request,response);
			if (savedRequest!=null) {
				targetUrl = savedRequest.getRedirectUrl();
			}
			String login_role = (String) session.getAttribute("login_role");
			if (login_role.equalsIgnoreCase("manage")) {
				if (user.getUserRole().equals("MANAGE")) {
					request.getSession(false).setAttribute("admin_login",
							true);
					targetUrl = CommUtil.getURL(request) + "/manage/index.htm";
					Map dto = new HashMap();
					dto.put("success",true);
					dto.put("userid", user.getId());
					dto.put("url", targetUrl);
					write(JsonHandler.toJson(dto),response);
				} else {
					setErrTipMsg("该用户没有后台业务管理权限！",response);
				}
			} else {
				redirectStrategy.sendRedirect(request,response,targetUrl);
			}

		} else {
			setErrTipMsg("未成功登录！",response);
		}

	}

	/**
	 * 用户成功退出后的URL导向
	 *
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/logout_success.htm")
	public void logout_success(HttpServletResponse response)  {
		setOkTipMsg("退出登录成功！",response);
	}

	/**
	 * 根据重定向登录标识，区分跳转和报错页面
	 * @param response
	 * @throws IOException
	 * @author AEON
	 */
	@RequestMapping("/login_error.htm")
	public void login_error(HttpServletRequest request,HttpServletResponse response) throws IOException {
		AuthenticationException ex = (AuthenticationException) request.getSession(false)
				.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		String errorMsg = ex != null ? ex.getMessage() : "Invalid credentials";
		Map dto = new HashMap();
		dto.put("success", false);
		dto.put("msg", errorMsg);
		dto.put("errorType", 3);
		write(JsonHandler.toJson(dto),response);
	}

	@RequestMapping("/third_login.htm")
	public void third_login(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (SecurityUserHolder.getOnlineUser()==null) {
			//request.getSession(false).setAttribute("third_password",request.getParameter("password"));
			response.sendRedirect(CommUtil.getURL(request)+"/iaeon_login.htm?username="
					+ CommUtil.encode(request.getParameter("username"))
					+ "&timestamp=" + request.getParameter("timestamp")
					+ "&sign=" + request.getParameter("sign")
					+ "&login_role="+request.getParameter("login_role")+"&synclogin=0");
		}
	}

	@RequestMapping("/third_logout.htm")
	public void third_logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (SecurityUserHolder.getOnlineUser()!=null) {
			response.sendRedirect(CommUtil.getURL(request)+"/iaeon_logout.htm?synclogout=0");
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
	public ModelAndView manage_login(HttpServletRequest request,
                              HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("login.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		request.getSession(false).removeAttribute("verify_code");
		return mv;
	}

	@RequestMapping("/manage/success.htm")
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
	@RequestMapping("/manage/error.htm")
	public ModelAndView error(HttpServletRequest request,
                              HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("error.html",
					configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 0, request,
					response);

		mv.addObject("op_title",
				request.getSession(false).getAttribute("op_title"));
		mv.addObject("list_url", request.getSession(false).getAttribute("list_url"));
		mv.addObject("open_url", request.getSession(false).getAttribute("open_url"));
		request.getSession(false).removeAttribute("op_title");
		request.getSession(false).removeAttribute("list_url");
		request.getSession(false).removeAttribute("open_url");
		return mv;
	}

	/**
	 * 默认异常出现
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/manage/exception.htm")
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
	@RequestMapping(value = "/manage/authority.htm")
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

	@RequestMapping("/manage/change_menu_tab.htm")
	public void change_menu_tab(String menu_id) {
		httpSession.setAttribute("cur_menu_id",menu_id);
		OnlineUser user = SecurityUserHolder.getOnlineUser();
		if (user != null) {
			if (user.getContext()==null) {
				user.setContext(ReflectUtil.newInstance(externalProperties.getUser_context_class()));
			}
			user.getContext().setMenuId(Convert.toLong(menu_id));
		}
	}

	/**
	 * flash获取验证码
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/manage/getCode.htm")
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
