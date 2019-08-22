package eon.hg.fap.web.manage.action;

import cn.hutool.core.bean.BeanUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.model.primary.MenuGroup;
import eon.hg.fap.db.service.IMenuGroupService;
import eon.hg.fap.db.service.IMenuService;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.op.MenuOP;
import eon.hg.fap.web.manage.op.UserOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class MenuManageAction extends BizAction {

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuGroupService menuGroupService;
    @Autowired
    private UserOP userOp;
    @Autowired
    private MenuOP menuOp;

    @SecurityMapping(title = "菜单管理", value = "menu:view")
    @RequestMapping("/menu_manage.htm")
    public ModelAndView menu_manage(HttpServletRequest request,
                                  HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/menu_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping({"menu:view","operate:view"})
    @RequestMapping("/menu_tree_all.htm")
    public List<Dto> menu_tree_all() {
        Dto dto = new HashDto();
        List<Dto> dtoList = menuOp.getMenuTreeList(dto, true);
        return dtoList;
    }

    @SecurityMapping("menu:view")
    @RequestMapping("/menu_tree_mg.htm")
    public List<Dto> menu_tree_mg(Long mg_id) {
        QueryObject qo = new QueryObject();
        qo.addQuery("obj.mg.id",new SysMap("mgid",mg_id),"=");
        List<Menu> menus = this.menuService.find(qo);
        List<Dto> dtoList = menuOp.getCardMenuList(menus);
        return dtoList;
    }

    @SecurityMapping("menu:view")
    @RequestMapping("/menu_list.htm")
    public String menu_list(String menu_type, Long parent_id, int page, int limit) {
        QueryObject qo = new QueryObject();
        qo.setCurrentPage(page);
        qo.setPageSize(limit);
        if ("1".equals(menu_type)) {
            qo.setFetchs(" join fetch obj.mg as mg");
            qo.addQuery("mg.id", new SysMap("id",parent_id), "=");
        } else {
            qo.addQuery("and (obj.parent_id=:id or obj.id=:id)", new HashMap(){{put("id",parent_id);}});
        }
        IPageList pList = this.menuService.list(qo);
        return JsonHandler.toPageJson(pList);
    }

    /**
     * 关联型的实体类不建议使用，暂不支持
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/menu_list1.htm")
    public Page<Menu> menu_list1(int page, int limit) {
        Pageable pageable = PageRequest.of(page,limit);
        Page<Menu> pageList = this.menuService.list(pageable);
        return pageList;
    }

    @SecurityMapping(title = "菜单新增", value = "menu:insert")
    @PostMapping("/menu_save.htm")
    public Dto menu_save(@RequestParam Map<String,Object> mapPara,
                                       @RequestParam("mg_id") Long menuGroupId,
                                       @RequestParam("menuCode") String code) {
        if (CommUtil.isEmpty(menuGroupId)) {
            return ErrTipMsg("请指定菜单组");
        }
        Menu vf = this.menuService.getObjByProperty(null, "menuCode", code);
        if (vf==null) {
            Menu menu = BeanUtil.mapToBeanIgnoreCase(mapPara, Menu.class,true);
            MenuGroup menuGroup = this.menuGroupService.getObjById(menuGroupId);
            menu.setMg(menuGroup);
            menu.setType("MANAGE");
            this.menuService.save(menu);
            return OkTipMsg("数据保存成功！");
        } else {
            return ErrTipMsg("菜单编码不能重复");
        }
    }

    @SecurityMapping(title = "菜单修改", value = "menu:update")
    @PostMapping("/menu_update.htm")
    public Dto menu_update(@RequestParam Map<String,Object> mapPara,
                                         @RequestParam("id") Long id,
                                         @RequestParam("menuCode") String code) {
        Menu vf = this.menuService.getObjByProperty(null, "menuCode", code);
        if (vf!=null && !vf.getId().equals(id)) {
            return ErrTipMsg("菜单编码不能重复");
        } else {
            Menu menu = this.menuService.getObjById(id);
            if (menu == null) {
                return ErrTipMsg("数据修改失败：没有找到对应菜单！");
            }
            menu = BeanUtil.fillBeanWithMapIgnoreCase(mapPara, menu,true);
            this.menuService.update(menu);
            return OkTipMsg("数据修改成功！");
        }
    }

    @SecurityMapping(title = "菜单删除", value = "menu:delete")
    @PostMapping("/menu_delete.htm")
    public Dto menu_delete(@RequestParam("ids") List<Long> idlist) {
        this.menuService.deleteAllChilds(idlist);
        return OkTipMsg("菜单删除成功！");
    }

    @SecurityMapping("menu:group_do")
    @RequestMapping("/menugroup_list.htm")
    public String menugroup_list() {
        QueryObject qo = new QueryObject();
        List<MenuGroup> pList = this.menuGroupService.find(qo);
        return JsonHandler.toPageJson(pList);
    }

    @SecurityMapping(title = "菜单组维护",value = "menu:group_do")
    @PostMapping("/menugroup_save.htm")
    public void menugroup_save(String insertdata, String updatedata, String removedata) {
        this.menuGroupService.saveDirtyData(insertdata,updatedata,removedata);
    }
}
