package eon.hg.fap.core.annotation;

import java.lang.annotation.*;

/**
 * 系统自动生成模板标签，通过该标签控制POJO中的自动和html模板中的中文列名对应
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Title {
	/**
	 * 
	 * @return
	 */
	public String value() default "";
}
