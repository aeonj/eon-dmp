package eon.hg.fap.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统权限资源标签，系统使用springSecurity作为权限框架，该注解用在需要纳入权限管理的action中
 * ， 通过AdminManageAction中init_role方法完成权限资源基础数据的导入以及不同用户角色权限的分配
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecurityMapping {

	String[] value() default {};//权限列表 权限组:权限格式，以:分隔

	String title() default "";// URL资源名称

}
