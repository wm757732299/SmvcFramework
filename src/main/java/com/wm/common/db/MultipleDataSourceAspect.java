package com.wm.common.db;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.wm.annotation.DataSourceChoose;

/**
 * 根据Mapper的名称，动态匹配它要使用的数据源
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2016.09.23
 *
 */
@Component
@Aspect
public class MultipleDataSourceAspect implements Ordered {

	private static final Logger LOGGER = Logger
			.getLogger(MultipleDataSourceAspect.class);

	/**
	 * AOP切入点
	 * 
	 * @param jp
	 * @throws Throwable
	 *             [MyNotes_AloneRow_WMing] JoinPoint jp :AOP的术语“接入点”。>>>
	 *             [MyNotes_AloneRow_WMing] 意思是你可以在某个点上拦截方法的调用/执行，>>>
	 *             [MyNotes_AloneRow_WMing] 然后再方法的调用前后加入你自定的代码，比如：事务，权限验证等等。
	 *             [MyNotes_AloneRow_WMing] 即是com.wm.service.impl包下所有的类中的所有方法
	 */
	@Before("execution(* com.wm.service.impl.*.*(..))")
	public void doBefore(JoinPoint jp) throws Throwable {
		DataSourceChoose choose = jp.getTarget().getClass()
				.getAnnotation(DataSourceChoose.class);

		String simpleName = jp.getTarget().getClass().getSimpleName();
		if (choose == null) {
			LOGGER.info(simpleName + " 未选择数据源");
		} else if (DataSourceChoose.SourceKey.DSK1.equals(choose.sourceKey())) {
			MultipleDataSource.setDataSourceKey("DSK1");
			LOGGER.info(simpleName + " 选择DSK1数据源");
		} else if (DataSourceChoose.SourceKey.DSK2.equals(choose.sourceKey())) {
			MultipleDataSource.setDataSourceKey("DSK2");
			LOGGER.info(simpleName + " 选择DSK2数据源");
		} else {
			LOGGER.error(simpleName + " 未选择数据源");
			throw new RuntimeException("Mapper未指定使用的数据源！");
		}

	}

	public int getOrder() {
		//定义拦截的次序，切换数据源必须在开始事务
		return 1;
	}

}
