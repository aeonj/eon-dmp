package eon.hg.fap.security.web;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.security.support.SsoCredential;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 重写SpringSecurity登录验证过滤器,验证器重新封装封装用户登录信息，可以任意控制用户与外部程序的接口，如整合UC论坛等等
 *
 * @author AEON
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.setFilterProcessesUrl("/iaeon_login.htm");
        this.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/login_success.htm"));
        this.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login_error.htm"));
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 状态， manage表示后台业务系统，public表示公众用户
        String login_role = request.getParameter("login_role");
        if (login_role == null || login_role.equals(""))
            login_role = "public";
        HttpSession session = request.getSession();
        session.setAttribute("login_role", login_role);
        boolean flag = true;
        if (session.getAttribute("verify_code") != null) {
            String code = request.getParameter("code") != null ? request
                    .getParameter("code").toUpperCase() : "";
            if (!session.getAttribute("verify_code").equals(code)) {
                flag = false;
            }
        }
        if (!flag) {
            throw new AuthenticationServiceException(
                    "验证码错误");
        } else {
            String username = "";
            if (CommUtil.null2Boolean(request.getParameter("encode"))) {
                username = CommUtil.decode(obtainUsername(request));
            } else
                username = obtainUsername(request);
            Object credentials = null;
            String sign = request.getParameter("sign");
            if (sign==null) {

                String password = obtainPassword(request);

                if (session.getAttribute("third_password") == null) {
                    if (password.indexOf(Globals.THIRD_ACCOUNT_LOGIN) >= 0) {
                        password = "";
                    }
                } else {
                    password = Globals.THIRD_ACCOUNT_LOGIN + session.getAttribute("third_password").toString();
                    session.removeAttribute("third_password");
                }
                username = username.trim();
                credentials = password;
            } else {
                credentials = WebHandler.toPo(request, SsoCredential.class);
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, credentials);
            if ((session != null) || (getAllowSessionCreation())) {
                request.getSession().setAttribute(
                        "SPRING_SECURITY_LAST_USERNAME",
                        CommUtil.escapeEntities(username));
            }
            setDetails(request, authRequest);
            return getAuthenticationManager().authenticate(authRequest);
        }

    }

    protected void setDetails(HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        request.getSession(false).removeAttribute("verify_code");

        super.successfulAuthentication(request, response, chain, authResult);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        String uri = request.getRequestURI();
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
