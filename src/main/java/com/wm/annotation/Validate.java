package com.wm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义方法注解:权限校验
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2018.01.31
 *
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Validate {
	/**
	 * 参数索引位置 默认为0，表示第一个参数的位置，-1则表示未提供，无法进行校验
	 */
	int posiIndex() default 0;

	public enum Permission {
		/**
		 * 内置管理员验证
		 */
		SUPER,
		/**
		 * 用户权限验证
		 */
		ORDINARY
	}

	public Permission permission() default Permission.ORDINARY;
}
