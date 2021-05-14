package eon.hg.fap.web.manage.action;

import cn.hutool.core.bean.BeanUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.model.primary.Operate;
import eon.hg.fap.db.service.*;
import eon.hg.fap.security.annotation.SecurityMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class OperateManageAction extends BizAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IOperateService operateService;
    @Autowired
    private IPermissionsService permissionsService;

    @SecurityMapping(title = "操作管理", value = "operate:view")
    @RequestMapping("/operate_manage.htm")
    public ModelAndView operate_manage(HttpServletRequest request,
                                       HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/operate_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping("operate:view")
    @RequestMapping("/operate_list.htm")
    public String operate_list(Long menu_id, int page, int limit) {
        QueryObject qo = new QueryObject("sequence", "asc");
        qo.setCurrentPage(page);
        qo.setPageSize(limit);
        qo.addQuery("obj.menu.id", new SysMap("menu_id", menu_id), "=");
        IPageList list = this.operateService.list(qo);
        return JsonHandler.toPageJson(list);
    }

    @SecurityMapping(title = "操作新增", value = "operate:insert")
    @PostMapping("/operate_save.htm")
    public Dto operate_save(@RequestParam Map<String, Object> mapPara,
                            @RequestParam("menu_id") Long menu_id,
                            @RequestParam("name") String name) {
        Menu menu = this.menuService.getObjById(menu_id);
        if (menu == null) {
            return ErrTipMsg("没有找到对应的菜单");
        }
        QueryObject qo = new QueryObject();
        qo.addQuery("obj.menu.id", new SysMap("menu_id", menu_id), "=");
        qo.addQuery("obj.name", new SysMap("name", name), "=");
        List<Operate> vfs = this.operateService.find(qo);
        if (vfs == null || vfs.size() == 0) {
            Operate operate = BeanUtil.mapToBeanIgnoreCase(mapPara,Operate.class,true);
            operate.setMenu(menu);
            this.operateService.save(operate);
            return OkTipMsg("数据保存成功！");
        } else {
            return ErrTipMsg("操作名称不能重复");
        }
    }

    @SecurityMapping(title = "操作修改", value = "operate:update")
    @PostMapping("/operate_update.htm")
    public Dto operate_update(@RequestParam Map<String, Object> mapPara,
                              @RequestParam("menu_id") Long menu_id,
                              @RequestParam("id") Long id,
                              @RequestParam("name") String name) {
        QueryObject qo = new QueryObject();
        qo.addQuery("obj.menu.id", new SysMap("menu_id", menu_id), "=");
        qo.addQuery("obj.name", new SysMap("name", name), "=");
        List<Operate> vfs = this.operateService.find(qo);
        if (vfs != null && vfs.size()>0 && !vfs.get(0).getId().equals(id)) {
            return ErrTipMsg("操作名称不能重复");
        } else {
            Operate operate = this.operateService.getObjById(id);
            if (operate == null) {
                return ErrTipMsg("数据修改失败：没有找到对应操作！");
            }
            operate = BeanUtil.fillBeanWithMapIgnoreCase(mapPara, operate,true);
            this.operateService.update(operate);
            return OkTipMsg("数据修改成功！");
        }
    }

    @SecurityMapping(title = "操作删除", value = "operate:delete")
    @PostMapping("/operate_delete.htm")
    public Dto operate_delete(@RequestParam("ids") List<Long> idlist) {
        this.operateService.deleteAllChilds(idlist);
        return OkTipMsg("操作删除成功！");
    }

    @SecurityMapping("operate:view")
    @RequestMapping("/operate_permissions_list.htm")
    public String operate_permissions_list(Long menu_id, int page, int limit) {
        QueryObject qo = new QueryObject(null,"value", "asc",page,limit);
        qo.addQuery("obj.value not like :per_value", new HashMap(){{put("per_value","%:view");}});
        IPageList list = this.permissionsService.list(qo);
        return JsonHandler.toPageJson(list);
    }

}


