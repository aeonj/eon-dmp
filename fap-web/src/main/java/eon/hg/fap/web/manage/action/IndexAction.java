package eon.hg.fap.web.manage.action;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.tools.json.JsonIncludePreFilter;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.body.ResultCode;
import eon.hg.fap.core.exception.ResultException;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.primary.UserConfig;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.IMenuService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.db.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 管理页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/index.htm")
    public ModelAndView index(HttpServletRequest request,
                              HttpServletResponse response) {
        OnlineUser user = SecurityUserHolder.getOnlineUser();
        if (user==null) {
            ModelAndView mv = new JModelAndView("login.html",
                    configService.getSysConfig(),
                    this.userConfigService.getUserConfig(), 0, request, response);
            request.getSession(false).removeAttribute("verify_code");
            return mv;
        } else {
            UserConfig userConfig = this.userConfigService.getUserConfig();
            if ("2".equals(userConfig.getLayout())) {
                ModelAndView mv = new JModelAndView("desktop.html",
                        configService.getSysConfig(),
                        userConfig, 0, request, response);
                //mv.addObject("mgslist", user.getMgs());
                Object urlObj = request.getSession(false).getAttribute("refererUrl");
                if (CommUtil.isNotEmpty(urlObj)) {
                    if (CommUtil.null2String(urlObj).indexOf("/index.htm") == -1) {
                        mv.addObject("refer_url", CommUtil.null2String(urlObj));
                    }
                    request.getSession(false).removeAttribute("refererUrl");
                }
                return mv;
            } else {
                ModelAndView mv = new JModelAndView("index.html",
                        configService.getSysConfig(),
                        userConfig, 0, request, response);
                //mv.addObject("mgslist", user.getMgs());
                Object urlObj = request.getSession(false).getAttribute("refererUrl");
                if (CommUtil.isNotEmpty(urlObj)) {
                    if (CommUtil.null2String(urlObj).indexOf("/index.htm") == -1) {
                        mv.addObject("refer_url", CommUtil.null2String(urlObj));
                    }
                    request.getSession(false).removeAttribute("refererUrl");
                }
                return mv;
            }
        }
    }

    @RequestMapping("/workbench.htm")
    public ModelAndView workbench(HttpServletRequest request,
                              HttpServletResponse response) {
        ModelAndView mv =new JModelAndView("workbench.html",
                this.configService.getSysConfig(), this.userConfigService.getUserConfig(),
                0, request, response);
        return mv;
    }

    @RequestMapping("/save_theme.htm")
    public @ResponseBody ResultBody save_theme(String theme) {
        if (SecurityUserHolder.getOnlineUser()==null) {
            throw new ResultException("用户未登录");
        }
        UserConfig userConfig = this.userConfigService.getUserConfig();
        userConfig.setTheme(theme);
        this.userConfigService.update(userConfig);
        this.userConfigService.reset();
        return ResultBody.success();
    }

    @RequestMapping("/save_layout.htm")
    public @ResponseBody ResultBody save_layout(String layout) {
        if (SecurityUserHolder.getOnlineUser()==null) {
            throw new ResultException("用户未登录");
        }
        UserConfig userConfig = this.userConfigService.getUserConfig();
        userConfig.setLayout(layout);
        this.userConfigService.update(userConfig);
        this.userConfigService.reset();
        return ResultBody.success();
    }

    @RequestMapping("/unlock_system.htm")
    public @ResponseBody ResultBody unlock_system(@RequestParam Map<String, Object> mapPara) {
        User user = (User) SecurityUserHolder.getCurrentUser();
        if (user==null) {
            throw new ResultException("用户未登录");
        }
        String psw = Convert.toStr(mapPara.get("password"));
        if (passwordEncoder.matches(psw,user.getPassword())) {
            return ResultBody.success();
        } else {
            return ResultBody.failed("密码不正确");
        }
    }

    @RequestMapping("/load_user_info.htm")
    public @ResponseBody String load_user_info(String layout) {
        User user = (User) SecurityUserHolder.getCurrentUser();
        if (user==null) {
            throw new ResultException("用户未登录");
        }
        return JsonHandler.toExtJson(user, true, new JsonIncludePreFilter( "userName", "nickName", "trueName", "telephone", "QQ", "years", "address", "sex", "email", "mobile", "card"));
    }

    @RequestMapping("/update_user_psw.htm")
    public @ResponseBody ResultBody update_user_psw(@RequestParam("userName") String userName,
                                              @RequestParam("password2") String password2,
                                              @RequestParam("password") String password) {
        User user = (User) SecurityUserHolder.getCurrentUser();
        if (user==null) {
            return ResultBody.failed("用户未登录");
        }
        if (StrUtil.isBlank(userName)) {
            return ResultBody.failed("传入的用户名为空");
        }
        if (!userName.equals(user.getUserName())) {
            return ResultBody.failed("当前修改密码的用户与登录用户不符");
        }
        String psw = Convert.toStr(password2);
        if (!passwordEncoder.matches(psw,user.getPassword())) {
            return ResultBody.failed(ResultCode.USER_LOGIN_ERROR);
        }
        if (StrUtil.isNotBlank(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        this.userService.update(user);
        return ResultBody.success();
    }
}
