package eon.hg.fap.web.manage.action;

import cn.hutool.core.date.DateUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.properties.PropertiesFactory;
import eon.hg.fap.common.properties.PropertiesFile;
import eon.hg.fap.common.properties.PropertiesHelper;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.service.*;
import eon.hg.fap.web.manage.dto.CodeStoreTagDto;
import eon.hg.fap.web.manage.dto.EleRenderTagDto;
import eon.hg.fap.web.manage.dto.HtmlTagDto;
import eon.hg.fap.web.manage.dto.ViewPortTagDto;
import eon.hg.fap.web.manage.op.ElementOP;
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
	private IBaseTreeService baseTreeService;
	@Autowired
	private IRelationMainService relationMainService;
	@Autowired
	private EnumerateOP enumOP;
	@Autowired
	private ElementOP eleOP;
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
		HtmlTagDto dto= WebHandler.toAPo(request, HtmlTagDto.class);
		mv.addObject("htmltag",dto);
		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.MANAGE);
		mv.addObject("extMode", pHelper.getValue("extMode","run"));
		String micolor = pHelper.getValue("micolor", "blue");
		mv.addObject("micolor", micolor);
		String urlSecurity = pHelper.getValue("urlSecurity", "1");
		mv.addObject("urlSecurity", urlSecurity);
		mv.addObject("ajaxErrCode", AeonConstants.Ajax_Timeout);
		mv.addObject("relationList", relationMainService.query("select obj from RelationMain obj",null,-1,1));
		return mv;
	}

	@RequestMapping("/codestore_tag.htm")
	public ModelAndView codestore_tag(HttpServletRequest request,
                                      HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("common/codestore_tag.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		CodeStoreTagDto dto=WebHandler.toAPo(request, CodeStoreTagDto.class);
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

	@RequestMapping("/code_render.htm")
	public ModelAndView code_render(HttpServletRequest request,
									HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("common/code_render.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String fields = (String) request.getAttribute("fields");
		String[] arrayFields = fields.split(",");
		List<SysMap> fieldList = new ArrayList<SysMap>();
		for (String field : arrayFields) {
			SysMap map = new SysMap(field,this.enumOP.getCodeList(field));
			fieldList.add(map);
		}
		mv.addObject("fieldList", fieldList);
		return mv;
	}

	@RequestMapping("/ele_render.htm")
	public ModelAndView ele_render(HttpServletRequest request,
								   HttpServletResponse response) throws Exception {
		ModelAndView mv = new JModelAndView("common/ele_render.html",
				configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		EleRenderTagDto eleDto=WebHandler.toAPo(request, EleRenderTagDto.class);
		String[] arrayFields = eleDto.getFields().split(",");
		List<SysMap> fieldList = new ArrayList<SysMap>();
		for (int i = 0; i < arrayFields.length; i++) {
			Element ele = eleOP.getEleSource(arrayFields[i]);
			if (ele==null) {
				continue;
			}
			//只要要素设置了类名，必须使用类方式获取树
			boolean istable = false;
			Dto dtoTemp = new HashDto();
			if (CommUtil.isEmpty(ele.getClass_name())) {
				istable = true;
			} else {
				dtoTemp.put("class_name",ele.getClass_name());
			}
			dtoTemp.put("table_name",ele.getEle_source());

			dtoTemp.put("source", arrayFields[i]);
			List lstEle;
			if (istable) {
				lstEle = baseTreeService.getCache(dtoTemp);
			} else {

				lstEle = eleOP.getObject(dtoTemp);
			}
			SysMap fields = new SysMap(arrayFields[i],getRenderShowList(lstEle));
			fieldList.add(fields);
		}
		mv.addObject("iscode", eleDto.getIscode());
		mv.addObject("fieldList", fieldList);
		return mv;
	}

	private List getRenderShowList(List lstEle) {
		List lstV = new ArrayList();
		for(int i=0; lstEle!=null && i<lstEle.size(); i++){
			Dto rn = (Dto) lstEle.get(i);
			List innerList = (List)rn.get("children");
			lstV.add(rn);
			if (innerList.size()>=0) {
				lstV.addAll(getRenderShowList((List)rn.get("children")));
			}
		}
		return lstV;
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
		ViewPortTagDto dto=WebHandler.toAPo(request, ViewPortTagDto.class);
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
