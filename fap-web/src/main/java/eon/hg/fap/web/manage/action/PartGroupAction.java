package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.PartDetail;
import eon.hg.fap.db.model.primary.PartGroup;
import eon.hg.fap.db.service.IPartGroupService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.op.PartOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class PartGroupAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IPartGroupService partGroupService;
    @Autowired
    private PartOP partOp;

    @SecurityMapping(title = "权限组管理", value = "part_group:view")
    @RequestMapping("/partgroup_init.htm")
    public ModelAndView partgroup_init(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/partgroup_init.html", configService
                .getSysConfig(),this.userConfigService.getUserConfig(), 0, request,response);
        return mv;
    }

    /**
     * PartGroup树
     */
    @RequestMapping("/partgroup_tree.htm")
    public String partgroup_tree() {
        QueryObject qo = new QueryObject();
        List<PartGroup> pglist = this.partGroupService.find(qo);
        return JsonHandler.toJson(partOp.PartGroupList2DtoList(pglist));
    }

    /**
     * PartGroup录入页面
     * @param request
     * @param response
     * @param op_flag 操作类型
     * @return
     */
    @SecurityMapping(title = "权限组管理", value = "part_group:view")
    @RequestMapping("/partgroup_input.htm")
    public ModelAndView partgroup_input(HttpServletRequest request,HttpServletResponse response,
                                        String obj_id, String op_flag) {
        ModelAndView mv = new JModelAndView("fap/ftl/partgroup_input.ftl", configService
                .getSysConfig(),this.userConfigService.getUserConfig(), 0, request,response);
        if (op_flag!=null) {
            if (op_flag.equals("edit")) {
                PartGroup partgroup = this.partGroupService.getObjById(CommUtil.null2Long(obj_id));
                mv.addObject("obj",partgroup);
            }
            mv.addObject("op_flag",op_flag);
        } else {
            mv.addObject("op_flag","add");
        }
        return mv;
    }

    /**
     *
     * @param request
     * @param response
     * @param obj_id
     * @return
     */
    @SecurityMapping(title = "权限组管理", value = "part_group:view")
    @RequestMapping("/partgroup_view.htm")
    public ModelAndView partgroup_view(HttpServletRequest request,HttpServletResponse response,
                                       String obj_id) {
        ModelAndView mv = new JModelAndView("fap/ftl/partgroup_view.ftl", configService
                .getSysConfig(),this.userConfigService.getUserConfig(), 0, request,response);
        PartGroup partgroup = this.partGroupService.getObjById(CommUtil.null2Long(obj_id));
        mv.addObject("obj",partgroup);
        return mv;
    }

    /**
     * partgroup保存管理
     *
     * @param id
     * @return
     */
    @SecurityMapping(title = "权限组新增", value = "part_group:insert")
    @RequestMapping("/partgroup_save.htm")
    public ResultBody partgroup_save(@RequestParam Map<String, Object> mapPara, String id, String belong_value) {
        PartGroup partgroup;
        if (CommUtil.isEmpty(id)) {
            partgroup = WebHandler.toPo(mapPara, PartGroup.class);
            partgroup.setAddTime(new Date());
        }else{
            PartGroup obj=this.partGroupService.getObjById(Long.parseLong(id));
            partgroup = WebHandler.toPo(mapPara, obj);
            partgroup.getDetails().clear();
        }

        List detail = JsonHandler.parseList(belong_value);
        for (int i=0; detail!=null && i<detail.size(); i++) {
            Dto map = (Dto) detail.get(i);
            String ele_values = CommUtil.null2String(map.get("eleValue"));
            if (CommUtil.isNotEmpty(ele_values)) {
                String[] ele_ids = CommUtil.null2String(map.get("eleValue")).split(",");
                for (String ele_id : ele_ids) {
                    PartDetail partdetail = new PartDetail();
                    partdetail.setAddTime(new Date());
                    partdetail.setPg(partgroup);
                    partdetail.setEleCode(CommUtil.null2String(map.get("eleCode")));
                    partdetail.setValue(ele_id);
                    partgroup.getDetails().add(partdetail);
                }
            }
        }

        if (CommUtil.isEmpty(id)) {
            this.partGroupService.save(partgroup);
        } else
            this.partGroupService.update(partgroup);
        return ResultBody.success("保存成功！");
    }

    /**
     * partgroup删除管理
     */
    @SecurityMapping(title = "权限组删除", value = "part_group:delete")
    @RequestMapping("/partgroup_del.htm")
    public ResultBody partgroup_del(String mulitId) {
        String[] ids = mulitId.split(",");
        for (String id : ids) {
            if (!id.equals("")) {
                this.partGroupService.delete(Long.parseLong(id));
            }
        }
        return ResultBody.success("删除partgroup成功！");
    }


}
