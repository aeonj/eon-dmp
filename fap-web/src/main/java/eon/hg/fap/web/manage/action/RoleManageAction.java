package eon.hg.fap.web.manage.action;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.core.tools.WebHandler;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.service.IRoleService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class RoleManageAction extends BizAction {

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IRoleService roleService;

    @SecurityMapping(title = "角色管理", value = "role:view")
    @RequestMapping("/role_manage.htm")
    public ModelAndView role_manage(HttpServletRequest request,
                                    HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/role_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping("role:view")
    @RequestMapping("/role_list.htm")
    public String role_list(int page, int limit) {
        QueryObject qo = new QueryObject("roleCode", "asc");
        qo.setCurrentPage(page);
        qo.setPageSize(limit);
        qo.addQuery("obj.builtin", new SysMap("builtin", false), "=");
        qo.addQuery("obj.type", new SysMap("type", "MANAGE"), "=");
        IPageList list = this.roleService.list(qo);
        return JsonHandler.toPageJson(list);
    }

    @SecurityMapping(title = "角色新增", value = "role:insert")
    @PostMapping("/role_save.htm")
    public Dto role_save(@RequestParam Map<String, Object> mapPara,
                         @RequestParam("roleCode") String code) {
        Role vf = this.roleService.getObjByProperty(null, "roleCode", code);
        if (vf == null) {
            Role role = WebHandler.toPo(mapPara, Role.class);
            role.setType("MANAGE");
            role.setBuiltin(false);
            this.roleService.save(role);
            return OkTipMsg("数据保存成功！");
        } else {
            return ErrTipMsg("角色编码不能重复");
        }
    }

    @SecurityMapping(title = "角色修改", value = "role:update")
    @PostMapping("/role_update.htm")
    public Dto role_update(@RequestParam Map<String, Object> mapPara,
                           @RequestParam("id") Long id,
                           @RequestParam("roleCode") String code) {
        Role vf = this.roleService.getObjByProperty(null, "roleCode", code);
        if (vf != null && !vf.getId().equals(id)) {
            return ErrTipMsg("角色编码不能重复");
        } else {
            Role role = this.roleService.getObjById(id);
            if (role == null) {
                return ErrTipMsg("数据修改失败：没有找到对应菜单！");
            }
            role = WebHandler.toPo(mapPara, role);
            this.roleService.update(role);
            return OkTipMsg("数据修改成功！");
        }
    }

    @SecurityMapping(title = "角色删除", value = "role:delete")
    @PostMapping("/role_delete.htm")
    public Dto role_delete(@RequestParam("ids") List<Long> idlist) {
        this.roleService.deleteAllChilds(idlist);
        return OkTipMsg("角色删除成功！");
    }


}
