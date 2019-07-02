package eon.hg.fap.core.mv;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.HttpInclude;
import eon.hg.fap.db.model.primary.SysConfig;
import eon.hg.fap.db.model.primary.UserConfig;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JModelMap extends ModelMap{

    public JModelMap() {
        super();
    }

    public JModelMap(SysConfig config, UserConfig uconfig,
                         HttpServletRequest request, HttpServletResponse response) {
        String contextPath = request.getContextPath().equals("/") ? ""
                : request.getContextPath();
        String webPath = CommUtil.getURL(request);
        super.addAttribute("current_webPath", webPath);
        String port = request.getServerPort() == 80
                || request.getServerPort() == 443 ? "" : ":"
                + CommUtil.null2Int(request.getServerPort());
        if (Globals.SSO_SIGN && config.isSecond_domain_open()
                && !CommUtil.generic_domain(request).equals("localhost")
                && !CommUtil.isIp(request.getServerName())) {
            webPath = (request.isSecure() ? "https://" : "http://") + "www."
                    + CommUtil.generic_domain(request) + port + contextPath;
        }
        super.addAttribute("domainPath", CommUtil.generic_domain(request));
        if (config.getImageWebServer() != null
                && !config.getImageWebServer().equals("")) {
            super.addAttribute("imageWebServer", config.getImageWebServer());
        } else {
            super.addAttribute("imageWebServer", webPath);
        }
        super.addAttribute("webPath", webPath);
        super.addAttribute("config", config);
        super.addAttribute("uconfig", uconfig);
        super.addAttribute("user", SecurityUserHolder.getCurrentUser());
        super.addAttribute("rgcode", SecurityUserHolder.getRgCode());
        super.addAttribute("httpInclude", new HttpInclude(request, response));
        String query_url = "";
        if (request.getQueryString() != null
                && !request.getQueryString().equals("")) {
            query_url = "?" + request.getQueryString();
        }
        super.addAttribute("current_url", request.getRequestURI() + query_url);
        boolean second_domain_view = false;
        String serverName = request.getServerName().toLowerCase();
        if (serverName.indexOf("www.") < 0 && serverName.indexOf(".") >= 0
                && serverName.indexOf(".") != serverName.lastIndexOf(".")
                && config.isSecond_domain_open()) {
            String secondDomain = serverName.substring(0,
                    serverName.indexOf("."));
            second_domain_view = true;// 使用二级域名访问，相关js url需要处理，避免跨域
            super.addAttribute("secondDomain", secondDomain);
        }
        super.addAttribute("second_domain_view", second_domain_view);
    }
}
