package eon.hg.fap.security.web;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.security.support.SsoCredential;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SsoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SsoAuthenticationFilter(AuthenticationManager authenticationManager) {
        super("/sso_login.htm");
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String refer_url = request.getParameter("refer_url");
                if (CommUtil.isNotEmpty(refer_url)) {
                    response.sendRedirect(refer_url);
                } else {
                    //这里直接写自己的处理逻辑，比如下面这段代码
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out=response.getWriter();
                    try {
                        Map dto = new HashMap();
                        dto.put("success",true);
                        dto.put("userid", ((OnlineUser) authentication.getPrincipal()).getUserid());
                        dto.put("url", CommUtil.getURL(request) + "/");
                        out.write(JsonHandler.toJson(dto));
                    } finally {
                        out.flush();
                        out.close();
                    }
                }
            }
        });
        this.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                String refer_url = request.getParameter("refer_url");
                String errorMsg = exception != null ? exception.getMessage() : "用户认证失败";
                if (CommUtil.isNotEmpty(refer_url)) {

                    HttpSession session = request.getSession();
                    session.setAttribute("op_title", errorMsg);
                    response.sendRedirect("/manage/error.htm");
                } else {
                    //这里直接写自己的处理逻辑，比如下面这段代码
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out=response.getWriter();
                    try {
                        Map dto = new HashMap();
                        dto.put("success",false);
                        dto.put("msg", errorMsg);
                        dto.put("errorType", 3);
                        out.write(JsonHandler.toJson(dto));
                    } finally {
                        out.flush();
                        out.close();
                    }
                }
            }
        });
        this.setAuthenticationManager(authenticationManager);
    }

    /**
     * Performs actual authentication.
     * <p>
     * The implementation should do one of the following:
     * <ol>
     * <li>Return a populated authentication token for the authenticated user, indicating
     * successful authentication</li>
     * <li>Return null, indicating that the authentication process is still in progress.
     * Before returning, the implementation should perform any additional work required to
     * complete the process.</li>
     * <li>Throw an <tt>AuthenticationException</tt> if the authentication process fails</li>
     * </ol>
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OpenID).
     * @return the authenticated user token, or null if authentication is incomplete.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        Object credentials = null;
        credentials = WebHandler.toPo(request, SsoCredential.class);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, credentials);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(authRequest);
    }
}
