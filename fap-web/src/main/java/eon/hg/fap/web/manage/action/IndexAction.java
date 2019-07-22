package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IMenuService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/manage")
public class IndexAction extends BizAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;

    /**
     * 管理页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/index.htm")
    public ModelAndView index(HttpServletRequest request,
                              HttpServletResponse response) {
        User user = SecurityUserHolder.getCurrentUser();
        if (user==null) {
            ModelAndView mv = new JModelAndView("login.html",
                    configService.getSysConfig(),
                    this.userConfigService.getUserConfig(), 0, request, response);
            request.getSession(false).removeAttribute("verify_code");
            return mv;
        }
        ModelAndView mv = new JModelAndView("index.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        //mv.addObject("mgslist", user.getMgs());
        Object urlObj = request.getSession(false).getAttribute("refererUrl");
        if (CommUtil.isNotEmpty(urlObj)) {
            if (CommUtil.null2String(urlObj).indexOf("/index.htm")==-1) {
                mv.addObject("refer_url",CommUtil.null2String(urlObj));
            }
            request.getSession(false).removeAttribute("refererUrl");
        }
        return mv;
    }
}
