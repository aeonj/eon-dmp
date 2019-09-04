package eon.hg.fap.web.manage.action;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.domain.virtual.SysMap;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.model.primary.Operate;
import eon.hg.fap.db.model.primary.Role;
import eon.hg.fap.db.model.primary.RoleOperate;
import eon.hg.fap.db.service.*;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.op.MenuOP;
import eon.hg.fap.web.manage.op.OperateOP;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class RoleMenuAction extends BizAction {

    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IOperateService operateService;
    @Autowired
    private IRoleOperateService roleOperateService;
    @Autowired
    private MenuOP menuOP;
    @Autowired
    private OperateOP operateOP;

    @SecurityMapping(title = "角色菜单管理", value = "rolemenu:view")
    @RequestMapping("/rolemenu_manage.htm")
    public ModelAndView rolemenu_manage(HttpServletRequest request,
                                        HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/rolemenu_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @SecurityMapping(title = "角色菜单保存", value = "rolemenu:save_rm")
    @RequestMapping("/rolemenu_save_rm.htm")
    public ResultBody rolemenu_save_rm(Long role_id, Long menu_id, Boolean checked) {
        if (checked) {
            Role role = this.roleService.getObjById(role_id);
            if (role != null) {
                Menu menu = this.menuService.getObjById(menu_id);
                role.getMenus().add(menu);
                roleService.update(role);
                QueryObject qo = new QueryObject();
                qo.addQuery("obj.role.id", new SysMap("role_id", role_id), "=");
                qo.addQuery("obj.operate.menu.id", new SysMap("menu_id", menu_id), "=");
                List<RoleOperate> ros = this.roleOperateService.find(qo);
                for (Operate operate : menu.getOperates()) {
                    boolean is_exists = false;
                    for (RoleOperate ro : ros) {
                        if (operate.getId().equals(ro.getOperate().getId())) {
                            is_exists = true;
                            break;
                        }
                    }
                    if (!is_exists) {
                        RoleOperate ro = new RoleOperate();
                        ro.setAddTime(new Date());
                        ro.setOperate(operate);
                        ro.setRole(role);
                        ro.setDispname(operate.getName());
                        this.roleOperateService.save(ro);
                    }
                }
            }
        } else {
            Role role = this.roleService.getObjById(role_id);
            if (role != null) {
                Menu menu = this.menuService.getObjById(menu_id);
                role.getMenus().remove(menu);
                roleService.update(role);
                QueryObject qo = new QueryObject();
                qo.addQuery("obj.role.id", new SysMap("role_id", role_id), "=");
                qo.addQuery("obj.operate.menu.id", new SysMap("menu_id", menu_id), "=");
                List<RoleOperate> ros = this.roleOperateService.find(qo);
                for (Operate operate : menu.getOperates()) {
                    Long oid = -1l;
                    for (RoleOperate ro : ros) {
                        if (operate.getId().equals(ro.getOperate().getId())) {
                            oid = ro.getId();
                            break;
                        }
                    }
                    if (!oid.equals(-1l)) {
                        this.roleOperateService.delete(oid);
                    }
                }
            }
        }
        return ResultBody.success();
    }

    @SecurityMapping(title = "角色操作保存", value = "rolemenu:save_ro")
    @RequestMapping("/rolemenu_save_ro.htm")
    public ResultBody rolemenu_save_ro(Long role_id, Long operate_id, Boolean checked, String fieldName,
                                       String value) throws ClassNotFoundException {
        QueryObject qo = new QueryObject();
        qo.addQuery("obj.role.id", new SysMap("role_id", CommUtil.null2Long(role_id)), "=");
        qo.addQuery("obj.operate.id", new SysMap("operate_id", CommUtil.null2Long(operate_id)), "=");
        List<RoleOperate> ros = this.roleOperateService.find(qo);
        if (checked) {

            if (ros != null && ros.size() > 0) {
                RoleOperate ro = ros.get(0);
                setRoleOperateValue(ro, fieldName, value);
                this.roleOperateService.update(ro);
                return ResultBody.success(ro);
            } else {
                Role role = this.roleService.getObjById(CommUtil.null2Long(role_id));
                if (role == null) {
                    return ResultBody.failed("未找到对应角色");
                }

                Operate operate = this.operateService.getObjById(CommUtil.null2Long(operate_id));
                if (operate == null) {
                    return ResultBody.failed("未找到对应操作");
                }
                RoleOperate ro = new RoleOperate();
                ro.setAddTime(new Date());
                ro.setOperate(operate);
                ro.setRole(role);
                setRoleOperateValue(ro, fieldName, value);
                this.roleOperateService.save(ro);
                return ResultBody.success(ro);
            }
        } else {
            if (ros != null && ros.size() > 0) {
                RoleOperate ro = ros.get(0);
                setRoleOperateValue(ro, fieldName, value);
                this.roleOperateService.delete(ro);
                return ResultBody.success(ro);
            }
        }
        return ResultBody.success();
    }

    private void setRoleOperateValue(RoleOperate ro, String fieldName, String value) throws ClassNotFoundException {
        Field[] fields = RoleOperate.class.getDeclaredFields();
        BeanWrapper wrapper = new BeanWrapperImpl(ro);
        Object val = null;
        for (Field field : fields) {
            Lock lock = field.getAnnotation(Lock.class);
            if (lock == null && field.getName().equals(fieldName)) {
                Class clz = Class.forName("java.lang.String");
                if (field.getType().getName().equals("int")) {
                    clz = Class.forName("java.lang.Integer");
                }
                if (field.getType().getName().equals("boolean")) {
                    clz = Class.forName("java.lang.Boolean");
                }
                val = Convert.convert(clz, value);
                wrapper.setPropertyValue(fieldName, val);
            }
        }
    }

    @RequestMapping("/rolemenu_menu_tree.htm")
    public List<Dto> menu_tree_all(Long role_id) {
        Dto dto = new HashDto();
        List<Dto> dtoList = menuOP.getMenuTreeList(dto, true);
        //roleService.query("select obj from role obj join fetch obj.menus where obj.id=:role_id",new HashMap(){{put("role_id",role_id);}},-1,-1);
        Role role = this.roleService.getObjById(role_id);
        List<Long> ids = new ArrayList<>();
        if (role!=null) {
            for (Menu menu : role.getMenus()) {
                ids.add(menu.getId());
            }
            setCheckNodeList(dtoList, ids);
        }
        return dtoList;
    }

    @RequestMapping("/rolemenu_operate_tree.htm")
    public List<Dto> operate_tree_all(Long role_id, Long menu_id) {
        Menu menu = this.menuService.getObjById(menu_id);
        if (menu==null) {
            return new ArrayList<>();
        }
        List<Dto> dtoList = operateOP.getOperateTreeList(menu);
        QueryObject qo = new QueryObject();
        qo.addQuery("obj.role.id", new SysMap("role_id", CommUtil.null2Long(role_id)), "=");
        List<RoleOperate> ros = this.roleOperateService.find(qo);
        List<Long> ids = new ArrayList<>();
        for (RoleOperate ro : ros) {
            ids.add(ro.getOperate().getId());
        }
        setCheckNodeList(dtoList, ids);
        return dtoList;
    }

    /**
     * 多选树设置选择节点
     *
     * @param list
     * @param idList
     * @return
     */
    public boolean setCheckNodeList(List list, List<Long> idList) {
        boolean isChecked = false;
        for (int i = 0; list != null && i < list.size(); i++) {
            Dto rn = (Dto) list.get(i);
            List innerList = rn.getList("children");
            rn.put("checked", false);
            //判断是否已选
            if (innerList==null || innerList.size() == 0) {
                boolean is_exists = false;
                for (Long id : idList) {
                    if (id.longValue() == rn.getLong("id").longValue()) {
                        is_exists = true;
                    }
                }
                if (is_exists) {
                    isChecked = true;
                    rn.put("checked", true);
                }
            } else {
                if (setCheckNodeList(rn.getList("children"), idList)) {
                    isChecked = true;
                    rn.put("checked", true);
                }
            }
        }
        return isChecked;
    }

}
