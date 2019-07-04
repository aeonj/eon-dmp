package eon.hg.fap.web.manage.action;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.tools.json.JsonIncludePreFilter;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.model.primary.*;
import eon.hg.fap.db.service.*;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.op.ElementOP;
import eon.hg.fap.web.manage.op.UserOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class UserManageAction extends BizAction {
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IOrgTypeService orgTypeService;
    @Autowired
    private IBaseTreeService baseTreeService;
    @Autowired
    private ISysLogService sysLogService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserOP userOp;
    @Autowired
    private ElementOP eleOp;

    @SecurityMapping(title = "用户管理", value = "user:view")
    @RequestMapping("/user_manage.htm")
    public ModelAndView user_manage(HttpServletRequest request,
                                    HttpServletResponse response) {
        ModelAndView mv = new JModelAndView("fap/user_manage.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        return mv;
    }

    @RequestMapping("/user_tree.htm")
    public String user_tree(String node) {
        List list = userOp.getUserTreeList();
        return JsonHandler.toJson(list);
    }

    @RequestMapping("/user_browse.htm")
    public String user_browse(Long user_id) {
        User user = userService.getObjById(user_id);
        OrgType orgType = this.orgTypeService.getObjById(user.getOrgtype_id());
        Map map = BeanUtil.beanToMap(user);
        map.put("orgtypename",orgType.getOrgName());
        String roles = "";
        for (Role role : user.getRoles()) {
            roles += role.getRoleCode()+" "+role.getRoleName()+"\n";
        }
        map.put("roles", roles);
        return JsonHandler.toExtJson(map, true, new JsonIncludePreFilter( "id", "username", "userName", "nickName", "trueName", "orgtypename", "orgtype_ele_id", "roles", "birthday", "telephone", "QQ", "years", "address", "sex", "email", "mobile", "card"));
    }

    @RequestMapping("user_query_edtinfo.htm")
    public String user_query_edtinfo(Long user_id) {
        User user = userService.getObjById(user_id);
        return JsonHandler.toExtJson(user,true,new JsonIncludePreFilter(User.class, "id", "username", "nickName", "trueName", "orgtype_id", "orgtype_ele_id", "birthday", "telephone", "QQ", "years", "address", "sex", "email", "mobile", "card"));
    }

    @RequestMapping("user_query_edtroleinfo.htm")
    public String user_query_roleinfo(Long user_id) {
        User user = userService.getObjById(user_id);
        String strrole = "";
        for (Role role: user.getRoles()) {
            strrole+= Convert.toStr(role.getId())+",";
        }
        return strrole.substring(0,strrole.length()-1);
    }

    @RequestMapping("/user_query_orgtype.htm")
    public List<OrgType> user_query_orgtype() {
        QueryObject qo = new QueryObject();
        return this.orgTypeService.find(qo);
    }

    @RequestMapping("/user_ele_by_orgtype.htm")
    public String user_ele_by_orgtype(Long user_id) {
        User user = userService.getObjById(user_id);
        if (CommUtil.isNotEmpty(user.getOrgtype_id())) {
            OrgType orgType = this.orgTypeService.getObjById(user.getOrgtype_id());
            return orgType.getEleCode();
        }
        return "";
    }

    @RequestMapping("/user_query_org_single_tree.htm")
    public String user_query_org_single_tree(@RequestParam("orgtype") Long orgtype_id,String node) throws Exception {
        if (CommUtil.isNotEmpty(orgtype_id)) {
            OrgType orgType = this.orgTypeService.getObjById(orgtype_id);
            if (StrUtil.isNotBlank(orgType.getEleCode())) {
                List<Dto> list;
                Element ele = eleOp.getEleSource(orgType.getEleCode());
                if (ele == null) {
                    return "[]";
                }
                Dto dto = new HashDto();
                //只要要素设置了类名，必须使用类方式获取树
                boolean istable = false;
                if (CommUtil.isEmpty(ele.getClass_name())) {
                    istable = true;
                } else {
                    dto.put("class_name", ele.getClass_name());
                }
                boolean isChecked = dto.getString("selectmodel").equalsIgnoreCase("multiple");
                dto.put("table_name", ele.getEle_source());
                if (istable) {
                    dto.put("source",orgType.getEleCode());
                    list = baseTreeService.getCache(dto);
                    if (isChecked && dto.get("checkids") != null) {
                        dto.put("values", dto.get("checkids"));
                    }
                } else {
                    list = eleOp.getObject(dto);
                }
                if (isChecked && dto.get("values") != null) {
                    eleOp.setCheckNodeList(list, dto.getString("values"));
                }
                String jsonString = JsonHandler.toJson(list);
                return jsonString;
            }
        }
        return "[]";
    }

    @SecurityMapping(title = "用户新增", value = "user:insert")
    @RequestMapping("/user_save.htm")
    public Dto user_save(@RequestParam Map<String, Object> mapPara,
                         @RequestParam("username") String userName,
                         @RequestParam("password") String password,
                         @RequestParam("rolenodes") String roleids) {
        if (CommUtil.null2String(userName).equalsIgnoreCase("super")) {
            return ErrTipMsg("super是系统内置用户，不允许维护");
        }
        if (CommUtil.isEmpty(password)) {
            return ErrTipMsg("请设置新增用户的初始密码");
        }
        User vf = this.userService.getObjByProperty(null, "userName", userName);
        if (vf != null) {
            return ErrTipMsg("用户登录名不能重复");
        }
        if (CommUtil.isNotEmpty(mapPara.get("mobile"))) {
            List lsvf = this.userService.query("select obj.id from User obj where obj.mobile=:mobile", new HashMap<String,Object>(){{
                put("mobile", mapPara.get("mobile"));
            }}, -1, -1);
            if (lsvf.size()>0) {
                return ErrTipMsg("手机号已存在，不能使用");
            }
        }
        if (CommUtil.isNotEmpty(mapPara.get("email"))) {
            List lsvf = this.userService.query("select obj.id from User obj where obj.email=:email", new HashMap<String,Object>(){{
                put("email", mapPara.get("email"));
            }}, -1, -1);
            if (lsvf.size()>0) {
                return ErrTipMsg("Email已存在，不能使用");
            }
        }
        User user = BeanUtil.mapToBeanIgnoreCase(mapPara, User.class, true);
        //用户角色处理
        String[] arr_roleid = roleids.split(",");
        user.getRoles().clear();
        for (String roleid : arr_roleid) {
            Role role = this.roleService.getObjById(CommUtil.null2Long(roleid));
            user.getRoles().add(role);
        }

        user.setRg_code(AeonConstants.SUPER_RG_CODE);
        user.setUserRole("MANAGE");
        user.setPassword(passwordEncoder.encode(password));
        user.setId(null);
        this.userService.save(user);
        return OkTipMsg("数据保存成功！");
    }

    @SecurityMapping(title = "用户修改", value = "user:update")
    @RequestMapping("/user_update.htm")
    public Dto user_update(@RequestParam Map<String, Object> mapPara,
                           @RequestParam("id") Long id,
                           @RequestParam("username") String userName,
                           @RequestParam("password") String password,
                           @RequestParam("rolenodes") String roleids) {
        if (CommUtil.null2String(userName).equalsIgnoreCase(AeonConstants.SUPER_USER)) {
            return ErrTipMsg("super是系统内置用户，不允许维护");
        }
        User vf = this.userService.getObjByProperty(null, "userName", userName);
        if (vf != null && !vf.getId().equals(id)) {
            return ErrTipMsg("用户登录名不能重复");
        }
        if (CommUtil.isNotEmpty(mapPara.get("mobile"))) {
            List lsvf = this.userService.query("select obj.id from User obj where obj.mobile=:mobile", new HashMap<String, Object>() {{
                put("mobile", mapPara.get("mobile"));
            }}, -1, -1);
            for (int i = 0; i < lsvf.size(); i++) {
                Long userid = (Long) lsvf.get(i);
                if (!userid.equals(CommUtil.null2Long(id))) {
                    return ErrTipMsg("手机号已存在，不能使用");
                }
            }
        }
        if (CommUtil.isNotEmpty(mapPara.get("email"))) {
            List lsvf = this.userService.query("select obj.id from User obj where obj.email=:email", new HashMap<String,Object>(){{
                put("email", mapPara.get("email"));
            }}, -1, -1);
            for (int i=0; i<lsvf.size(); i++) {
                Long userid = (Long) lsvf.get(i);
                if (!userid.equals(CommUtil.null2Long(id))) {
                    return ErrTipMsg("Email已存在，不能使用");
                }
            }
        }
        User user = this.userService.getObjById(id);
        user = BeanUtil.fillBeanWithMapIgnoreCase(mapPara, user, true);
        //用户角色处理
        String[] arr_roleid = roleids.split(",");
        user.getRoles().clear();
        for (String roleid : arr_roleid) {
            Role role = this.roleService.getObjById(CommUtil.null2Long(roleid));
            user.getRoles().add(role);
        }
        if (StrUtil.isNotBlank(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userService.update(user);
        return OkTipMsg("数据修改成功！");
    }

    @SecurityMapping(title = "用户删除", value = "user:delete")
    @RequestMapping("/user_delete.htm")
    public Dto user_delete(Long user_id) {
        User user = this.userService.getObjById(user_id);
        if (CommUtil.null2String(user.getUsername()).equalsIgnoreCase(AeonConstants.SUPER_USER)) {
            return ErrTipMsg("super是系统内置用户，不允许维护");
        }
            // 删除管理员操作日志
            Map params = new HashMap();
            params.put("user_id", user_id);
            List<SysLog> logs = this.sysLogService
                    .query("select obj from SysLog obj where obj.user_id=:user_id",
                            params, -1, -1);
            List list = new ArrayList();
            for (SysLog log : logs) {
                list.add(log.getId());
            }
            this.sysLogService.batchDelete(list);
            // 管理员伪删除,取消所有管理员权限
            user.setIs_deleted(true);
            user.getRoles().clear();
            user.setUserName("_" + user.getUsername());
            user.setMobile("_" + user.getMobile());
            user.setEmail("_" + user.getEmail());
            this.userService.update(user);
        return OkTipMsg("用户删除成功！");
    }

}
