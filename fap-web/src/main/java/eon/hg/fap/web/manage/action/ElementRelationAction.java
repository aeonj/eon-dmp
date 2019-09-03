package eon.hg.fap.web.manage.action;

import eon.hg.fap.core.body.PageBody;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.RelationDetail;
import eon.hg.fap.db.model.primary.RelationMain;
import eon.hg.fap.db.service.IRelationDetailService;
import eon.hg.fap.db.service.IRelationMainService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ElementRelationAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IRelationMainService relationMainService;
    @Autowired
    private IRelationDetailService relationDetailService;

    @SecurityMapping(title = "要素关联关系管理", value = "relation:view")
    @RequestMapping("/relation_manage.htm")
    public ModelAndView elements_relation(HttpServletRequest request,
                                       HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/element_relation.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping("relation:view")
    @RequestMapping("/relation_list.htm")
    public PageBody relation_list() {
        QueryObject qo = new QueryObject("pri_ele", "asc");
        List<RelationMain> list = this.relationMainService.find(qo);
        return PageBody.success().addObject(list);
    }

    @SecurityMapping("relation:view")
    @RequestMapping("relation_pri_check.htm")
    public List<RelationDetail> relation_pri_check(@RequestParam("relation_id") Long relation_id) {
        QueryObject qo = new QueryObject();
        qo.addQuery("main.id",new SysMap("main_id",relation_id),"=");
        return this.relationDetailService.find(qo);
    }

    @SecurityMapping("relation:view")
    @RequestMapping("relation_sec_check.htm")
    public List<RelationDetail> relation_sec_check(@RequestParam("relation_id") Long relation_id, String pri_ele_value) {
        QueryObject qo = new QueryObject();
        qo.addQuery("main.id",new SysMap("main_id",relation_id),"=");
        qo.addQuery("pri_ele_value",new SysMap("pri_ele_value",pri_ele_value),"=");
        return this.relationDetailService.find(qo);
    }

    @SecurityMapping(title = "要素关联新增", value = "relation:insert")
    @RequestMapping("relation_insert.htm")
    public ResultBody relation_insert(@RequestParam Map<String, Object> mapPara, @RequestParam("dirtydata") String detaiJson) {
        RelationMain vf = this.relationMainService.getObjByProperty("pri_ele",mapPara.get("pri_ele"),"sec_ele",mapPara.get("sec_ele"));
        if (vf!=null) {
            return ResultBody.failed("已存在的要素控制关系");
        } else {
            //要素关联主从表新增可以考虑不使用事务，因此业务逻辑没放进Service服务
            RelationMain rm = WebHandler.toPo(mapPara, RelationMain.class);
            rm.setId(null);
            rm = this.relationMainService.save(rm);
            List<RelationDetail> details = JsonHandler.parseList(detaiJson, RelationDetail.class);
            for (RelationDetail detail : details) {
                if (!detail.isIs_deleted()) {
                    detail.setId(null);
                    detail.setMain(rm);
                    this.relationDetailService.save(detail);
                }
            }
            return ResultBody.success();
        }
    }

    @SecurityMapping(title = "要素关联修改", value = "relation:update")
    @RequestMapping("relation_update.htm")
    public ResultBody relation_update(@RequestParam Map<String, Object> mapPara, @RequestParam("dirtydata") String detaiJson, @RequestParam("id") Long id) {
        RelationMain vf = this.relationMainService.getObjByProperty("pri_ele",mapPara.get("pri_ele"),"sec_ele",mapPara.get("sec_ele"));
        if (vf!=null && !vf.getId().equals(id)) {
            return ResultBody.failed("已存在的要素控制关系");
        } else {
            //要素关联主从表修改可以考虑不使用事务，因此业务逻辑没放进Service服务
            RelationMain rm = WebHandler.toPo(mapPara, RelationMain.class);
            rm = this.relationMainService.update(rm);
            List<RelationDetail> details = JsonHandler.parseList(detaiJson, RelationDetail.class);
            for (RelationDetail detail : details) {
                if (detail.isIs_deleted()) {
                    if (detail.getId() > 0l) {
                        this.relationDetailService.delete(detail);
                    }
                } else {
                    if (detail.getId() > 0l) {
                        detail.setMain(rm);
                        this.relationDetailService.update(detail);
                    } else {
                        detail.setId(null);
                        detail.setMain(rm);
                        this.relationDetailService.save(detail);
                    }
                }
            }
            return ResultBody.success();
        }
    }

    @SecurityMapping(title = "要素关联删除", value = "relation:delete")
    @RequestMapping("relation_delete.htm")
    public ResultBody relation_delete(@RequestParam("relation_id") Long id) {
        //暂不用事务，用事务需放进Service服务处理
        QueryObject qo = new QueryObject();
        qo.addQuery("main.id",new SysMap("main_id",id),"=");
        List<RelationDetail> rds = this.relationDetailService.find(qo);
        for (RelationDetail rd : rds) {
            this.relationDetailService.delete(rd);
        }
        this.relationMainService.delete(id);
        return ResultBody.success();
    }

}
