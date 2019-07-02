package eon.hg.fap.core.filter;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.SysConfig;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class RequestFilter implements Filter {
    @Autowired
    private ISysConfigService configService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        SysConfig config = this.configService.getSysConfig();
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI();
        boolean redirect = false;
        String redirect_url = "";
            if (!config.isWebsiteState()) {
                if (this.init_url(url)) {
                    if (url.indexOf("/manage") < 0
                            && url.indexOf("/install.htm") <= 0
                            && url.indexOf("/close.htm") < 0
                            && url.indexOf("/jsload.htm") < 0
                            && url.indexOf("/verify.htm") < 0
                            && url.indexOf("login_success.htm") < 0
                            && url.indexOf("logout_success.htm") < 0) {
                        redirect = true;
                        redirect_url = CommUtil.getURL(request) + "/close.htm";
                    }
                    if (url.indexOf("/mobile") >= 0) {
                        redirect = true;
                        redirect_url = CommUtil.getURL(request)
                                + "/mobile/close.htm";
                    }
                }
            } else {
                User user = SecurityUserHolder.getCurrentUser();
                if (user != null) {
                    if (url.indexOf("/login.htm") >= 0) {
                        redirect = true;
                        redirect_url = CommUtil.getURL(request) + "/index.htm";
                    }
                    if (url.indexOf("/register.htm") >= 0) {
                        redirect = true;
                        redirect_url = CommUtil.getURL(request) + "/index.htm";
                    }
                } else {
                    if (url.indexOf("/install") >= 0) {
                        redirect_url = CommUtil.getURL(request) + "/index.htm";
                        redirect = true;
                    }
                }
            }
        if (redirect) {
            response.sendRedirect(redirect_url);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean init_url(String url) {
        String prifix = "";
        if (url.indexOf(".") > 0) {
            prifix = url.substring(url.lastIndexOf(".") + 1);
        } else {
            prifix = url;
        }
        String[] extend_list = new String[] { "css", "jpg", "jpeg", "png",
                "gif", "bmp", "tbi", "js" };
        boolean flag = true;
        for (String temp : extend_list) {
            if (temp.equals(prifix)) {
                flag = false;
            }
        }

        return flag;
    }
}
