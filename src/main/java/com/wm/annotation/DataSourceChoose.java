 package com.wm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记Mapper使用哪个数据源
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2016.09.24
 *
 *       [MyNotes_AloneRow_WMing] Retention 定义注解类声明周期 {@interface} 定义注解类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataSourceChoose {
	
	/**
	 * 
	 * 名称与spring-core中dataSource中的配置项要一致
	 */
	public enum SourceKey {
		DSK1, DSK2
	};
	
	/**
	 * 
	 * 方法名为注解的属性
	 */
    public SourceKey sourceKey() default SourceKey.DSK1;
}
