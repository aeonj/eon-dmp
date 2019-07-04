package eon.hg.fap.security.web;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SpringSeurity验证切入点，这里用来辨识是否通过过验证
 * @author AEON
 *
 */
@Component
public class LoginUrlEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException {
		String targetUrl;
		String url = request.getRequestURI();
		if (request.getQueryString() != null
				&& !request.getQueryString().equals("")) {
			url = url + "?" + request.getQueryString();
		}
		request.getSession(false).setAttribute("refererUrl", url);
		// 取得登陆前的url
		String refererUrl = request.getHeader("Referer");
		// TODO 增加处理逻辑
		targetUrl = refererUrl;
		if (url.indexOf("/manage/") >= 0) {//判断是否为后台业务管理请求
			targetUrl = request.getContextPath() + "/manage/login.htm";
		} else if (url.indexOf("/wap/") >= 0) {//判断是否为wap请求
			targetUrl = request.getContextPath() + "/wap/login.htm";
		} else {
			targetUrl = request.getContextPath() + "/login.htm";
		}
		response.sendRedirect(targetUrl);
	}
}
