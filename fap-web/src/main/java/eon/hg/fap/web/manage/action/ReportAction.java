/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年12月14日
 *
 * @author cxj
 */

package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.properties.ReportProperties;
import eon.hg.fap.core.exception.Assert;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aeon
 *
 */
@Slf4j
@RestController
@RequestMapping("/manage")
public class ReportAction extends BizAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private ReportProperties reportProperties;

    @RequestMapping("/report_template.htm")
    public ModelAndView field_demo(HttpServletRequest request,
                                   HttpServletResponse response, Long menu_id, String report_id) {
        Assert.notEmpty(reportProperties.getServer_url(), "未配置");
        Assert.notNull(menu_id, "未获取到菜单信息");
        //判断用户是否在线
        ModelAndView mv = new JModelAndView("fap/report_template.html",
                configService.getSysConfig(), userConfigService.getUserConfig(),
                0, request, response);
        mv.addObject("report_id",report_id);
        mv.addObject("server_url",reportProperties.getServer_url());
        return mv;
    }


}
