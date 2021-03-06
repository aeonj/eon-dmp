package eon.hg.fap.core.filter;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.security.SecurityUserHolder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI();
        boolean redirect = false;
        String redirect_url = "";
        if (AeonConstants.VLicense.isinstall() || url.indexOf("/manage") < 0) {
            if (SecurityUserHolder.getOnlineUser() != null) {
                if (url.indexOf("/manage/login.htm") >= 0) {
                    redirect = true;
                    redirect_url = CommUtil.getURL(request) + "/manage/index.htm";
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
        } else {
            if (this.init_url(url)) {
                redirect_url = CommUtil.getURL(request) + "/manage/license.htm";
                redirect = true;
                if (url.indexOf("/license") >= 0) {
                    redirect = false;
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
        String[] extend_list = new String[]{"css", "jpg", "jpeg", "png",
                "gif", "bmp", "tbi", "js"};
        boolean flag = true;
        for (String temp : extend_list) {
            if (temp.equals(prifix)) {
                flag = false;
            }
        }

        return flag;
    }
}
