package eon.hg.fap.security.access;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 重写SpringSecurity权限控制接口，管理系统无权限操作的页面导向，及用户多次登录分别加载对应权限信息，系统可以采用多种登陆机制
 * ，用户从前台登陆，仅仅加载非管理员权限，从管理员页面登陆，通过该控制器加载管理员权限
 * @author aeon
 * 
 */
@Component
public class EonAccessDeniedHandler implements AccessDeniedHandler {
	@Autowired
	private IUserService userService;
	protected static final Log logger = LogFactory
			.getLog(AccessDeniedHandlerImpl.class);
	private String errorPage;

	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && (!errorPage.startsWith("/"))) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		boolean ajax_login = CommUtil.null2Boolean(session
				.getAttribute("ajax_login"));
		if (ajax_login) {
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.print("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			this.errorPage = null;
			User user = userService.getObjById(Convert.toLong(SecurityUserHolder.getOnlineUser().getUserid()));
			if (user.getUserRole().indexOf("MANAGE") < 0) {
				this.errorPage = "/authority.htm";
			} else {
				if (request.getQueryString()!=null && request.getQueryString().contains("menu_id=")) {
					this.errorPage = "/manage/authority.htm";
				}
			}
            if (!response.isCommitted()) {
                if (errorPage != null) {
                    // Put exception into request scope (perhaps of use to a view)
                    request.setAttribute(WebAttributes.ACCESS_DENIED_403,
                            accessDeniedException);

                    // Set the 403 status code.
                    response.setStatus(HttpStatus.FORBIDDEN.value());

                    // forward to error page.
                    RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
                    dispatcher.forward(request, response);
                }
                else {
                    response.sendError(HttpStatus.FORBIDDEN.value(),
                            "访问被拒绝");
                }
            }
		}
	}

}
