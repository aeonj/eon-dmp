package eon.hg.fap.core.annotation;

import eon.hg.fap.core.domain.LogType;

import java.lang.annotation.*;

/**
 *  系统日志记录注解，该注解用在需要记录操作日志的action中，使用Spring AOP结合该注解完成操作日志记录
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {
	/**
	 * 
	 * @return
	 */
	String title() default "";

	/**
	 * 
	 * @return
	 */
	String entityName() default "";

	/**
	 * 
	 * @return
	 */
	LogType type();

	/**
	 * 方法描述
	 * 
	 * @return
	 */
	String description() default "";

}
