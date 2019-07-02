package eon.hg.fap.web.manage.action;

import cn.hutool.core.date.DateUtil;
import eon.hg.fap.common.properties.PropertiesFactory;
import eon.hg.fap.common.properties.PropertiesFile;
import eon.hg.fap.common.properties.PropertiesHelper;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.tools.WebForm;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.db.service.IUserService;
import eon.hg.fap.web.manage.dto.CodeStoreTagDto;
import eon.hg.fap.web.manage.dto.HtmlTagDto;
import eon.hg.fap.web.manage.dto.ViewPortTagDto;
import eon.hg.fap.web.manage.op.EnumerateOP;
import eon.hg.fap.web.manage.op.MenuOP;
import eon.hg.fap.web.manage.op.TplManageOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于Ext处理页面
 * @author aeon
 *
 */
@Controller
@RequestMapping("/manage")
public class TplManageAction {
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	@Autowired
	private IUserService userService;
	@Autowired
	private EnumerateOP enumOP;
	@Autowired
	private TplManageOp tplOp;
	@Autowired
	private MenuOP menuOP;

	@RequestMapping("/html_tag.htm")
	public ModelAndView html_tag(HttpServletRequest request,
                                 HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("common/html_tag.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		HtmlTagDto dto= WebForm.toAPo(request, HtmlTagDto.class);
		mv.addObject("htmltag",dto);
		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.MANAGE);
		mv.addObject("extMode", pHelper.getValue("extMode","run"));
		String micolor = pHelper.getValue("micolor", "blue");
		mv.addObject("micolor", micolor);
		String urlSecurity = pHelper.getValue("urlSecurity", "1");
		mv.addObject("urlSecurity", urlSecurity);
		mv.addObject("ajaxErrCode", AeonConstants.Ajax_Timeout);
		return mv;
	}

	@RequestMapping("/codestore_tag.htm")
	public ModelAndView codestore_tag(HttpServletRequest request,
                                      HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("common/codestore_tag.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		CodeStoreTagDto dto=WebForm.toAPo(request, CodeStoreTagDto.class);
		String[] arrayFields = dto.getFields().split(",");
		List<String> fieldList = new ArrayList<String>();
		for (int i = 0; i < arrayFields.length; i++) {
			fieldList.add(arrayFields[i]);
		}
		mv.addObject("enumOP", enumOP);
		mv.addObject("showCode", dto.getShowCode());
		mv.addObject("fieldList", fieldList);
		return mv;
	}

	@RequestMapping("/ui_grant_tag.htm")
	public ModelAndView ui_grant_tag(HttpServletRequest request,
                                     HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("common/ui_grant_tag.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		List grantList = new ArrayList();
		mv.addObject("grantList", grantList);
		return mv;
	}

	@RequestMapping("/viewport_tag.htm")
	public ModelAndView viewport_tag(HttpServletRequest request,
                                     HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("common/view_port_tag.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		ViewPortTagDto dto=WebForm.toAPo(request, ViewPortTagDto.class);
		mv.addObject("viewporttag",dto);
		mv.addObject("tplOp",tplOp);
		String currDate = DateUtil.today();
		mv.addObject("dateweek",currDate+" "+DateUtil.thisDayOfWeek());
//		User user =  SecurityUserHolder.getCurrentUser();
//		Map<String,String> menumap = new HashMap<>();
//		for (MenuGroup menuGroup : user.getMgs()) {
//			String menuListJson = JsonHelper.toJson(menuOP.getCardMenuList(menuGroup.getAuthmenus()));
//			menumap.put(menuGroup.getId().toString(),menuListJson);
//		}
		mv.addObject("cardmgs",menuOP.getCardMgs());
		return mv;
	}

	@RequestMapping("/myux_tag.htm")
	public ModelAndView myux_tag(HttpServletRequest request,
                                 HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("common/myux_tag.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		mv.addObject("uxType",request.getParameter("uxType"));
		return mv;
	}

    @RequestMapping("/jsload.htm")
    public ModelAndView jsload(HttpServletRequest request,
                               HttpServletResponse response) {
        String name = (String) request.getAttribute("name");
        ModelAndView mv = new JModelAndView(name,
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

}
