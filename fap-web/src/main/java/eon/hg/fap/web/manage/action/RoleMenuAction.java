package eon.hg.fap.web.manage.action;

import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/manage")
public class RoleMenuAction extends BizAction {

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;

    @SecurityMapping(title = "角色菜单管理", value = "rolemenu:view")
    @RequestMapping("/rolemenu_manage.htm")
    public ModelAndView rolemenu_manage(HttpServletRequest request,
                                    HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/rolemenu_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping(title = "角色菜单保存", value = "rolemenu:save_rm")
    @RequestMapping("/rolemenu_save_rm.htm")
    public void rolemenu_save_rm() {

    }

    @SecurityMapping(title = "角色操作保存", value = "rolemenu:save_ro")
    @RequestMapping("/rolemenu_save_ro.htm")
    public void rolemenu_save_ro() {

    }

}
