package eon.hg.fap.security.access;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Collection;
import java.util.List;

/**
 * 重写SpringSecurity权限控制接口，管理系统无权限操作的页面导向，及用户多次登录分别加载对应权限信息，系统可以采用多种登陆机制
 * ，用户从前台登陆，仅仅加载非管理员权限，从管理员页面登陆，通过该控制器加载管理员权限
 * @author aeon
 * 
 */
@Component
public class EonAccessDeniedHandler implements AccessDeniedHandler {
	public static final String SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY = "SPRING_SECURITY_403_EXCEPTION";
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
			this.errorPage = "/authority.htm";
			User user = SecurityUserHolder.getCurrentUser();
			List<GrantedAuthority> all_authorities = user.get_all_Authorities();
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			Collection<? extends GrantedAuthority> current_authorities = auth.getAuthorities();
			if (user.getUserRole().indexOf("MANAGE") < 0) {
				this.errorPage = "/authority.htm";
			} else {
				// for(GrantedAuthority auth1:all_authorities){
				// boolean p=false;
				// for(GrantedAuthority auth2:current_authorities){
				// if(auth1.toString().equals(auth2.toString())){
				// p=true;
				// }
				// }
				// if(p){
				// System.out.println("共同存在:"+auth1.toString());
				// }else{
				// System.out.println("=========================================不存在:"+auth1.toString());
				// }
				// }
				if (all_authorities.size() != current_authorities.size()) {
					this.errorPage = "/manage/login.htm";
				}
			}
			if (this.errorPage != null) {
				((HttpServletRequest) request).setAttribute(
						"SPRING_SECURITY_403_EXCEPTION", accessDeniedException);

				RequestDispatcher rd = request.getRequestDispatcher(this.errorPage);
				rd.forward(request, response);
			}

			if (!response.isCommitted()) {
				((HttpServletResponse) response).sendError(403,
						accessDeniedException.getMessage());
			}
		}
	}

}
