package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.Element;
import eon.hg.fap.db.service.IElementService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class ElementManageAction extends BizAction {

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IElementService elementService;

    @SecurityMapping(title = "要素管理", value = "element:view")
    @RequestMapping("/element_manage.htm")
    public ModelAndView element_manage(HttpServletRequest request,
                                    HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/element_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping("element:view")
    @RequestMapping("/element_list.htm")
    public String element_list(int page, int limit) {
        QueryObject qo = new QueryObject("ele_code", "asc");
        qo.setCurrentPage(page);
        qo.setPageSize(limit);
        IPageList list = this.elementService.list(qo);
        return JsonHandler.toPageJson(list);
    }

    @SecurityMapping(title = "要素新增", value = "element:insert")
    @PostMapping("/element_save.htm")
    public Dto element_save(@RequestParam Map<String, Object> mapPara,
                         @RequestParam("ele_code") String code) {
        Element vf = this.elementService.getObjByProperty(null, "ele_code", code);
        if (vf == null) {
            Element element = WebHandler.toPo(mapPara, Element.class);
            this.elementService.save(element);
            return OkTipMsg("数据保存成功！");
        } else {
            return ErrTipMsg("要素编码不能重复");
        }
    }

    @SecurityMapping(title = "要素修改", value = "element:update")
    @PostMapping("/element_update.htm")
    public Dto element_update(@RequestParam Map<String, Object> mapPara,
                           @RequestParam("id") Long id,
                           @RequestParam("ele_code") String code) {
        Element vf = this.elementService.getObjByProperty(null, "ele_code", code);
        if (vf != null && !vf.getId().equals(id)) {
            return ErrTipMsg("要素编码不能重复");
        } else {
            Element element = this.elementService.getObjById(id);
            if (element == null) {
                return ErrTipMsg("数据修改失败：没有找到对应要素！");
            }
            element = WebHandler.toPo(mapPara, element);
            this.elementService.update(element);
            return OkTipMsg("数据修改成功！");
        }
    }

    @SecurityMapping(title = "要素删除", value = "element:delete")
    @PostMapping("/element_delete.htm")
    public Dto element_delete(@RequestParam("ids") List<Long> idlist) {
        this.elementService.batchDelete(idlist);
        return OkTipMsg("要素删除成功！");
    }

}
