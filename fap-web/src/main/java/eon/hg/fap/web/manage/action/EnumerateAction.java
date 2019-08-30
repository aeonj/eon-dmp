package eon.hg.fap.web.manage.action;

import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.Enumerate;
import eon.hg.fap.db.service.IEnumerateService;
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
public class EnumerateAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IEnumerateService enumerateService;

    @SecurityMapping(title = "枚举值管理", value = "enumerate:view")
    @RequestMapping("/enumerate_manage.htm")
    public ModelAndView element_manage(HttpServletRequest request,
                                       HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/enumerate_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping("enumerate:view")
    @RequestMapping("/enumerate_list.htm")
    public String element_list(int page, int limit) {
        QueryObject qo = new QueryObject("field,sortno", "asc");
        qo.setCurrentPage(page);
        qo.setPageSize(limit);
        IPageList list = this.enumerateService.list(qo);
        return JsonHandler.toPageJson(list);
    }

    @SecurityMapping(title = "枚举值新增", value = "enumerate:insert")
    @PostMapping("/enumerate_save.htm")
    public ResultBody enumerate_save(@RequestParam Map<String, Object> mapPara) {
        QueryObject qo = new QueryObject();
        qo.addQuery("field", new SysMap("field",mapPara.get("field")),"=");
        qo.addQuery("code", new SysMap("code",mapPara.get("code")),"=");
        List<Enumerate> vfs = this.enumerateService.find(qo);
        if (vfs==null || vfs.size()==0) {
            Enumerate enumerate = WebHandler.toPo(mapPara, Enumerate.class);
            this.enumerateService.save(enumerate);
            return ResultBody.success("数据保存成功！");
        } else {
            return ResultBody.failed("枚举值编码不能重复");
        }
    }

    @SecurityMapping(title = "枚举值修改", value = "enumerate:update")
    @PostMapping("/enumerate_update.htm")
    public ResultBody enumerate_update(@RequestParam Map<String, Object> mapPara,
                              @RequestParam("id") Long id) {
        QueryObject qo = new QueryObject();
        qo.addQuery("field", new SysMap("field",mapPara.get("field")),"=");
        qo.addQuery("code", new SysMap("code",mapPara.get("code")),"=");
        List<Enumerate> vfs = this.enumerateService.find(qo);
        if (vfs!=null && vfs.size()>0 && !vfs.get(0).getId().equals(id)) {
            return ResultBody.failed("枚举值编码不能重复");
        } else {
            Enumerate enumerate = this.enumerateService.getObjById(id);
            if (enumerate == null) {
                return ResultBody.failed("数据修改失败：没有找到对应枚举值！");
            }
            enumerate = WebHandler.toPo(mapPara, enumerate);
            this.enumerateService.update(enumerate);
            return ResultBody.success("数据修改成功！");
        }
    }

    @SecurityMapping(title = "枚举值删除", value = "enumerate:delete")
    @PostMapping("/enumerate_delete.htm")
    public ResultBody enumerate_delete(@RequestParam("ids") List<Long> idlist) {
        this.enumerateService.batchDelete(idlist);
        return ResultBody.success("枚举值删除成功！");
    }

}
