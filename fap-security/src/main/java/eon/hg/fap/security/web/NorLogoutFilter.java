package eon.hg.fap.security.web;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.db.model.primary.SysLog;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.ISysLogService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * SpringSecurity用户退出过滤器，重写LogoutFilter，用来记录用户退出信息，及清除用户登录时保存的相关session信息
 * @author apple
 *
 */
public class NorLogoutFilter extends LogoutFilter {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IUserService userService;

	public NorLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
                           LogoutHandler... handlers) {
		super(logoutSuccessHandler, handlers);
	}

	public NorLogoutFilter(String logoutSuccessUrl, LogoutHandler... handlers) {
		super(logoutSuccessUrl, handlers);
	}

	public void saveLog(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String aeon_view_type = CommUtil.null2String(request
				.getParameter("aeon_view_type"));
		session.setAttribute("aeon_view_type", aeon_view_type);
		User u = (User) session.getAttribute("user");
		if (u != null) {
			User user = this.userService.getObjById(u.getId());
			this.userService.update(user);
			SysLog log = new SysLog();
			log.setAddTime(new Date());
			log.setContent(user.getTrueName() + "于"
					+ CommUtil.formatTime("yyyy-MM-dd HH:mm:ss", new Date())
					+ "退出系统");
			log.setTitle("用户退出");
			log.setType(0);
			log.setUser_id(user.getId());
			log.setUser_name(user.getUserName());
			log.setIp(CommUtil.getIpAddr(request));
			this.sysLogService.save(log);
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (requiresLogout(request, response)) {
			HttpSession session = request.getSession(false);
			if (null != session) {
				saveLog(request);
			}
		}
		super.doFilter(req, res, chain);;
	}
}
