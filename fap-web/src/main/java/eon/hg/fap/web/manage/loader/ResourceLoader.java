package eon.hg.fap.web.manage.loader;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.db.model.primary.Permissions;
import eon.hg.fap.db.model.primary.Res;
import eon.hg.fap.db.model.primary.SysConfig;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.*;
import eon.hg.fap.security.annotation.SecurityMapping;
import eon.hg.fap.web.manage.action.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;


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
	private IPermissionsService permissionsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private IResourceLoaderService loaderService;
	@Autowired
    private ISysConfigService sysConfigService;

	@PostConstruct
	public void init_role() throws Exception {
		List<Class> clzs = new ArrayList<Class>();
		// 后台业务系统管理权限加载
		clzs.add(BaseManageAction.class);
		clzs.add(IndexAction.class);
		clzs.add(UIManageAction.class);
		clzs.add(RoleManageAction.class);
		clzs.add(MenuManageAction.class);
		int sequence = 0;
		for (Class clz : clzs) {
			try {
				String url_value = "";
				//检索类上的请求路径
				Annotation[] c_annotations = clz.getAnnotations();
				for (Annotation c_tag : c_annotations) {
					if (RequestMapping.class.isAssignableFrom(c_tag.annotationType())) {
						url_value += ((RequestMapping) c_tag).value()[0];
					}
				}
				Method[] ms = clz.getMethods();
				for (Method m : ms) {
					String value = "";
					Annotation[] annotation = m.getAnnotations();
					//检索方法上的请求路径
					for (Annotation tag : annotation) {
						if (RequestMapping.class.isAssignableFrom(tag
								.annotationType())) {
							value = url_value + ((RequestMapping) tag).value()[0];
						} else if (PostMapping.class.isAssignableFrom(tag
								.annotationType())) {
							value = url_value + ((PostMapping) tag).value()[0];
						}
					}
					for (Annotation tag : annotation) {
						if (SecurityMapping.class.isAssignableFrom(tag
								.annotationType())) {
							Map params = new HashMap();
							params.put("value", value+"*");
							List<Res> ress = this.resService
									.query("select obj from Res obj where obj.value=:value",
											params, -1, -1);
							String permissionTitle = ((SecurityMapping) tag).title();
							if (ress.size() == 0) {
								Res res = new Res();
								res.setValue(value+"*");
								res.setAddTime(new Date());
								res.setResName(permissionTitle);
								res.setType("URL");
								res.setPermission(ArrayUtil.join(((SecurityMapping) tag).value(),","));
								this.resService.save(res);
								log.info("初始化资源："+res.getResName());
							}
							for (String strtag: ((SecurityMapping) tag).value()) {
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
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.toString());
			}
			sequence++;
		}
		// 添加默认超级管理员并赋予所有权限
		User user = this.userService.getObjByProperty(null, "userName",
				"super");
		if (user == null) {
			user = new User();
			user.setUserName("super");
			user.setUserRole("MANAGE");
			user.setTrueName("超级管理员");
			user.setRg_code(AeonConstants.SUPER_RG_CODE);
			user.setPassword(passwordEncoder.encode("123456"));
			user.setManageType(1);
			this.userService.save(user);
			log.info("初始化超级管理员super！");
		}
	}

    @PostConstruct
	public void init_sys_table() throws Exception {
		SysConfig sc = sysConfigService.getSysConfig();
	    if (sc.isDb_reset()) {
			loaderService.initSysTable();
			sc.setDb_reset(false);
			sysConfigService.update(sc);
        }
    }


}
