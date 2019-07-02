package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.OrgType;
import eon.hg.fap.db.service.IOrgTypeService;
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
public class OrgTypeAction extends BizAction {

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IOrgTypeService orgTypeService;

    @SecurityMapping(title = "机构管理", value = "org_type:view")
    @RequestMapping("/org_type_manage.htm")
    public ModelAndView orgType_manage(HttpServletRequest request,
                                    HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/org_type_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping("org_type:view")
    @RequestMapping("/org_type_list.htm")
    public String orgType_list(int page, int limit) {
        QueryObject qo = new QueryObject("orgCode", "asc");
        qo.setCurrentPage(page);
        qo.setPageSize(limit);
        IPageList list = this.orgTypeService.list(qo);
        return JsonHandler.toPageJson(list);
    }

    @SecurityMapping(title = "机构新增", value = "org_type:insert")
    @PostMapping("/org_type_save.htm")
    public Dto orgType_save(@RequestParam Map<String, Object> mapPara,
                         @RequestParam("orgCode") String code) {
        OrgType vf = this.orgTypeService.getObjByProperty(null, "orgCode", code);
        if (vf == null) {
            OrgType orgType = WebHandler.toPo(mapPara, OrgType.class);
            this.orgTypeService.save(orgType);
            return OkTipMsg("数据保存成功！");
        } else {
            return ErrTipMsg("机构编码不能重复");
        }
    }

    @SecurityMapping(title = "机构修改", value = "org_type:update")
    @PostMapping("/org_type_update.htm")
    public Dto orgType_update(@RequestParam Map<String, Object> mapPara,
                           @RequestParam("id") Long id,
                           @RequestParam("orgCode") String code) {
        OrgType vf = this.orgTypeService.getObjByProperty(null, "orgCode", code);
        if (vf != null && !vf.getId().equals(id)) {
            return ErrTipMsg("机构编码不能重复");
        } else {
            OrgType orgType = this.orgTypeService.getObjById(id);
            if (orgType == null) {
                return ErrTipMsg("数据修改失败：没有找到对应机构！");
            }
            orgType = WebHandler.toPo(mapPara, orgType);
            this.orgTypeService.update(orgType);
            return OkTipMsg("数据修改成功！");
        }
    }

    @SecurityMapping(title = "机构删除", value = "org_type:delete")
    @PostMapping("/org_type_delete.htm")
    public Dto orgType_delete(@RequestParam("ids") List<Long> idlist) {
        this.orgTypeService.batchDelete(idlist);
        return OkTipMsg("机构删除成功！");
    }

}
