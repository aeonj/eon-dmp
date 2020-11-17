package eon.hg.fap.loader;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.beans.SpringUtils;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.db.model.primary.*;
import eon.hg.fap.db.service.*;
import eon.hg.fap.security.annotation.SecurityMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 后台管理控制,用来添加、编辑管理员信息，包括给管理员分配权限，初始化系统权限等等
 * @author AEON
 *
 */
@Slf4j
@Component
public class ResourceLoader {
	@Autowired
	private IResService resService;
	@Autowired
	private IUserService userService;
    @Autowired
    private IRoleService roleService;
	@Autowired
	private IPermissionsService permissionsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private IResourceLoaderService loaderService;
	@Autowired
    private ISysConfigService sysConfigService;
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	@Autowired
	private SpringUtils springUtils;

	@PostConstruct
	public void init_user_role() {
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

		SysConfig sc = sysConfigService.getSysConfig();
		if (sc.getRequest_count()!=map.size()) {
			sc.setRequest_count(map.size());
			sysConfigService.update(sc);
			for (Map.Entry<RequestMappingInfo, HandlerMethod> me : map.entrySet()) {
				RequestMappingInfo info = me.getKey();
				HandlerMethod method = me.getValue();
				PatternsRequestCondition p = info.getPatternsCondition();
				String value = "";
				for (String url : p.getPatterns()) {
					value = url;
				}

				Annotation[] annotation = method.getMethod().getAnnotations();
				for (Annotation tag : annotation) {
					if (SecurityMapping.class.isAssignableFrom(tag
							.annotationType())) {
						Map params = new HashMap();
						params.put("value", value+"*");
						List<Res> ress = this.resService
								.query("select obj from Res obj where obj.value=:value",
										params, -1, -1);
						String permissionTitle = ((SecurityMapping) tag).title();
						String[] perms = ((SecurityMapping) tag).value();
						if (ress.size() == 0) {
							Res res = new Res();
							res.setValue(value+"*");
							res.setAddTime(new Date());
							res.setResName(permissionTitle);
							res.setType("URL");
							res.setPermission(ArrayUtil.join(perms,","));
							this.resService.save(res);
							log.info("初始化资源："+res.getResName());
						} else {
							Res res = ress.get(0);
							String str_perms = CommUtil.null2String(ArrayUtil.join(perms,","));
							if (!str_perms.equals(res.getPermission())) {
								res.setPermission(str_perms);
								this.resService.update(res);
								log.info("更新资源："+res.getResName());
							}
						}
						for (String strtag: perms) {
							Permissions permissions = this.permissionsService.getObjByProperty(null,"value",strtag);
							if (permissions==null) {
								permissions = new Permissions();
								if (StrUtil.isNotBlank(permissionTitle)) {
									permissions.setTitle(permissionTitle);
								}
								permissions.setValue(strtag);
								this.permissionsService.save(permissions);
							} else {
								if (StrUtil.isBlank(permissions.getTitle()) && StrUtil.isNotBlank(permissionTitle)) {
									permissions.setTitle(permissionTitle);
									this.permissionsService.update(permissions);
								}
							}
						}
					}
				}
			}
		}

		// 添加默认超级管理员并赋予所有权限
		User user = this.userService.getObjByProperty(null, "userName",
				AeonConstants.SUPER_USER);
		if (user == null) {
			user = new User();
			user.setUserName(AeonConstants.SUPER_USER);
			user.setUserRole("MANAGE");
			user.setTrueName("超级管理员");
			user.setRg_code(AeonConstants.SUPER_RG_CODE);
			user.setPassword(passwordEncoder.encode("123456"));
			user.setManageType(1);
			this.userService.save(user);
			log.info("初始化超级管理员super！");
		}
        // 添加默认公众角色并赋予所有权限
        Role role = this.roleService.getObjByProperty(null, "roleCode",
                "ROLE_PUBLIC_DEFAULT");
        if (role == null) {
            role = new Role();
            role.setAddTime(new Date());
            role.setBuiltin(true);
            role.setRoleCode("ROLE_PUBLIC_DEFAULT");
            role.setRoleName("公众角色");
            role.setType("PUBLIC");
            this.roleService.save(role);
        }
	}

    @PostConstruct
	public void init_sys_table() throws Exception {
		sysConfigService.reset();
		SysConfig sc = sysConfigService.getSysConfig();
	    if (sc.isDb_reset()) {
			loaderService.initSysTable();
			sc.setDb_reset(false);
			sysConfigService.update(sc);
        }
    }


}
