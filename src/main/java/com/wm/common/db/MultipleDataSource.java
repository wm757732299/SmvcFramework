package com.wm.common.db;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多个数据源配置类
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2016.09.23
 *
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
	private static final Logger LOGGER = Logger
			.getLogger(MultipleDataSource.class);

	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

	public static void setDataSourceKey(String dataSource) {

		dataSourceKey.set(dataSource);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		LOGGER.info("使用数据源：【" + dataSourceKey.get()+ "】");
		return dataSourceKey.get();
	}

}
