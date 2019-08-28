/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年12月14日
 *
 * @author cxj
 */

package eon.hg.fap.web.manage.action;

import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.model.primary.EleEnterprise;
import eon.hg.fap.db.service.IBaseDataService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author aeon
 *
 */
@Controller
public class DemoAction extends BizAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IBaseDataService baseDataService;

    @RequestMapping("/demo/field_demo.htm")
    public ModelAndView field_demo(HttpServletRequest request,
                                   HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("demo/field_demo.html",
                configService.getSysConfig(), userConfigService.getUserConfig(),
                0, request, response);
        return mv;
    }

    @RequestMapping("/demo/tree_demo.htm")
    public ModelAndView tree_demo(HttpServletRequest request,
                                  HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("demo/tree_demo.html",
                configService.getSysConfig(), userConfigService.getUserConfig(),
                0, request, response);
        return mv;
    }

    @RequestMapping("/demo/enterprise.htm")
    public @ResponseBody BaseData enterprise(Long id) {
        return this.baseDataService.getObjById(EleEnterprise.class, id);
    }


}
