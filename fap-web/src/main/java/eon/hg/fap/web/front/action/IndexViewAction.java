package eon.hg.fap.web.front.action;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.SysConfig;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexViewAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	
	@RequestMapping("/index.htm")
	public ModelAndView index(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
		//默认跳转到登录页面 modify by aeon 2015.6.2
		if (Globals.MUST_LOGIN_FLAG) {
			if (SecurityUserHolder.getOnlineUser()==null) {
				ModelAndView mv = new JModelAndView("login.html",
						configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
				return mv;
			}
		}
		ModelAndView mv = new JModelAndView("index.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		request.getSession(false).removeAttribute("verify_code");
		return mv;
	}

	@RequestMapping("/jsload.htm")
	public ModelAndView jsload(HttpServletRequest request,
                               HttpServletResponse response) {
		String name = (String) request.getAttribute("name");
		SysConfig config = configService.getSysConfig();
		ModelAndView mv = new JModelAndView(Globals.DEFAULT_SYSTEM_PAGE_ROOT + config.getSysLanguage()+name,
				config,	this.userConfigService.getUserConfig(), request, response);
		return mv;
	}
	
	/**
	 * 对外网页关闭时候导向的请求
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/close.htm")
	public ModelAndView close(HttpServletRequest request,
                              HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("close.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		return mv;
	}

}
