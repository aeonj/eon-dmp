package eon.hg.fap.log;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.ip.IPSeeker;
import eon.hg.fap.core.annotation.Log;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.LogType;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.enums.LogFieldType;
import eon.hg.fap.db.model.primary.SysLog;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IRoleService;
import eon.hg.fap.db.service.ISysLogService;
import eon.hg.fap.db.service.IUserService;
import eon.hg.fap.security.annotation.SecurityMapping;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 
 * Description: 系统日志管理类，这里使用Spring环绕通知和异常通知进行动态管理,系统只记录管理员操作记录，对访问列表不进行记录
 * @author aeon
 */

@Aspect
@Component
public class SystemLogAdvice {
	@Autowired
	private ISysLogService sysLogService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userSerivce;

	// 记录管理员操作日志
	@After(value = "execution(* eon.hg.*.web.manage..*.*(..))")
	public void admin_op_log(JoinPoint joinPoint) throws Exception {
		if (Globals.SAVE_LOG) {
			ServletRequestAttributes attributes =
					(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			saveLog(joinPoint, request);
		}
	}

	@Before(value = "execution(* eon.hg.fap.web.manage.action.BaseManageAction.logout_success(..))")
	public void logout_log(JoinPoint joinPoint) throws Exception {
		if (Globals.SAVE_LOG) {
			ServletRequestAttributes attributes =
					(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			saveLog(joinPoint, request);
		}
	}

	private void saveLog(JoinPoint joinPoint, HttpServletRequest request) throws Exception {
		if (SecurityUserHolder.getOnlineUser() != null) {
			User user = SecurityUserHolder.getCurrentUser();
			Method method = getMethod(joinPoint);
			for (Annotation anno : method.getDeclaredAnnotations()) {
				if (anno instanceof Log) {
					Log annotation = (Log) anno;
					StringBuilder description = new StringBuilder();
					if (user.getTrueName() != null) {
						description.append(user.getUsername()).append("（").append(user.getTrueName()).append("）");
					} else {
						description.append(user.getUsername());
					}
					description.append("于").append(CommUtil.formatTime("yyyy-MM-dd HH:mm:ss", new Date()));

					String current_url = request.getRequestURI();
					String current_ip = CommUtil.getIpAddr(request);// 获得本机IP
					String ip_city = "未知地区";
					if (CommUtil.isIp(current_ip)) {
						IPSeeker ip = new IPSeeker(null, null);
						ip_city = ip.getIPLocation(current_ip).getCountry();
					}
					SysLog log = new SysLog();
					log.setTitle(annotation.title());
					log.setType(0);
					log.setOp_type(annotation.type());
					log.setAddTime(new Date());
					log.setUser_id(user.getId());
					log.setUser_name(user.getUsername());

					log.setIp(current_ip);
					log.setIp_city(ip_city);
					log.setOp_url(current_url);
					if (annotation.type()== LogType.LOGIN) {
						description.append("进入了系统！");
					} else if (annotation.type()== LogType.LOGOUT) {
						description.append("退出了系统！");
					} else {
						description.append("执行了").append(annotation.title()).append("操作。");
						//description.append("操作参数为：").append(method.getParameters().toString());
					}
					log.setContent(description.toString());
					this.sysLogService.save(log);
				} else if (anno instanceof SecurityMapping) {
					SecurityMapping annotation = (SecurityMapping) anno;
					StringBuilder description = new StringBuilder();
					if (user.getTrueName() != null) {
						description.append(user.getUsername()).append("（").append(user.getTrueName()).append("）");
					} else {
						description.append(user.getUsername());
					}
					description.append("于").append(CommUtil.formatTime("yyyy-MM-dd HH:mm:ss", new Date()));
					if (method.getReturnType().isAssignableFrom(ModelAndView.class)) {
						if (request.getParameter("menu_id") != null) {
							description.append("进入了【").append(annotation.title()).append("】菜单");
							String ids = request.getParameter("menu_id");
							String current_url = request.getRequestURI();
							String current_ip = CommUtil.getIpAddr(request);// 获得本机IP
							String ip_city = "局域网";
							SysLog log = new SysLog();
							log.setTitle(annotation.title());
							log.setType(0);
							log.setAddTime(new Date());
							log.setUser_id(user.getId());
							log.setUser_name(user.getUsername());
							log.setContent(description.toString());
							log.setIp(current_ip);
							log.setIp_city(ip_city);
							log.setOp_url(current_url);
							log.setIds(ids);
							this.sysLogService.save(log);
						}
					} else {
						// 获取操作内容
						String id = request.getParameter("id");
						String ids = request.getParameter("ids");
						// 不记录访问列表记录，只记录操作记录
						if (id != null || ids != null
								|| existsKeys(annotation.value(), "save")
								|| existsKeys(annotation.value(), "edit")
								|| existsKeys(annotation.value(), "insert")
								|| existsKeys(annotation.value(), "update")
								|| existsKeys(annotation.value(), "delete")
								|| existsKeys(annotation.value(), "audit")
								|| existsKeys(annotation.value(), "unaudit")
								|| existsKeys(annotation.value(), "check")
								|| existsKeys(annotation.value(), "uncheck")
								|| existsKeys(annotation.value(), "discard")
								|| existsKeys(annotation.value(), "back")
								|| existsKeys(annotation.value(), "make")) {
							String option1 = "执行";
							String option2 = "操作";
							description.append("执行了【").append(annotation.title()).append("】操作");
							if (ids != null && !"".equals(ids)) {
								description.append("。操作数据id为：").append(ids);
							} else {
								if (request.getParameter("id") != null
										&& !"".equals(request.getParameter("id"))) {
									description.append("。操作数据id为：").append(id);
								}
							}
							String title = annotation.title();
							String current_url = request.getRequestURI();
							String current_ip = CommUtil.getIpAddr(request);// 获得本机IP
							String ip_city = "局域网";
							SysLog log = new SysLog();
							log.setTitle(title);
							log.setType(0);
							log.setAddTime(new Date());
							log.setUser_id(user.getId());
							log.setUser_name(user.getUsername());
							log.setContent(description.toString());
							log.setIp(current_ip);
							log.setIp_city(ip_city);
							log.setOp_url(current_url);
							log.setIds(ids);
							this.sysLogService.save(log);
						}
					}
				}
			}
		}
	}

	// 异常日志记录
	@AfterThrowing(value = "execution(* eon.hg.*.web.manage..*.*(..))&&args(request,..) ", throwing = "exception")
	public void exceptionLog(HttpServletRequest request, Throwable exception) {
		if (Globals.SAVE_LOG) {
			if (SecurityUserHolder.getOnlineUser() != null) {
				User user = SecurityUserHolder.getCurrentUser();
				String current_ip = CommUtil.getIpAddr(request);// 获得本机IP
				String ip_city = "未知地区";
				if (CommUtil.isIp(current_ip)) {
					IPSeeker ip = new IPSeeker(null, null);
					ip_city = ip.getIPLocation(current_ip).getCountry();
				}
				SysLog log = new SysLog();
				log.setTitle("系统异常");
				log.setType(1);
				log.setAddTime(new Date());
				log.setUser_id(user.getId());
				log.setUser_name(user.getUsername());
				log.setContent(log.getAddTime() + "执行"
						+ request.getRequestURI() == null ? "" : request
						.getRequestURI()
						+ "时出现异常，异常代码为:"
						+ exception.getMessage());
				log.setIp(current_ip);
				log.setIp_city(ip_city);
				this.sysLogService.save(log);
			}
		}
	}

	// 记录用户登录日志

	public void loginLog() {
		System.out.println("用户登录");
	}

	private boolean existsKeys(String[] values, String key) {
		for (String value : values) {
			if (value.indexOf(key)>=0) {
				return true;
			}
		}
		return false;
	}

	private Method getMethod(JoinPoint joinPoint) {
		// 获取连接点的方法签名对象
		MethodSignature joinPointObject = (MethodSignature) joinPoint
				.getSignature();
		// 连接点对象的方法
		Method method = joinPointObject.getMethod();
//		// 连接点方法方法名
//		String name = method.getName();
//		Class<?>[] parameterTypes = method.getParameterTypes();
//		// 获取连接点所在的目标对象
//		Object target = joinPoint.getTarget();
//		// 获取目标方法
//		try {
//			method = target.getClass().getMethod(name, parameterTypes);
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			method = null;
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return method;
	}

	public String adminOptionContent(Object[] args, String mName)
			throws Exception {

		if (args == null) {
			return null;
		}
		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {
			// 获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			boolean cal = false;
			LogFieldType[] types = LogFieldType.values();
			for (LogFieldType type : types) {
				if (type.toString().equals(className)) {
					cal = true;
				}
			}
			if (cal) {
				rs.append("[参数" + index + "，类型：" + className + "，值：");
				// 获取对象的所有方法
				Method[] methods = info.getClass().getDeclaredMethods();
				// 遍历方法，判断get方法
				for (Method method : methods) {
					String methodName = method.getName();
					// 判断是不是get方法
					if (methodName.indexOf("get") == -1) {// 不是get方法
						continue;// 不处理
					}
					Object rsValue = null;
					try {
						// 调用get方法，获取返回值
						rsValue = method.invoke(info);
						if (rsValue == null) {// 没有返回值
							continue;
						}
					} catch (Exception e) {
						continue;
					}
					// 将值加入内容中
					rs.append("(" + methodName + " : " + rsValue.toString()
							+ ")");
				}
				rs.append("]");
				index++;
			}
		}
		return rs.toString();
	}
}
