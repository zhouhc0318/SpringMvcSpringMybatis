package com.zh.util.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
/**
 * 自定义数据源
 * @author WQ
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	private static final Logger log=LoggerFactory.getLogger(DynamicDataSource.class);
	
	/**
	 * 重写获取DataSource的key的方法
	 * 数据源每次获取连接时候调用该方法得到数据源对应的key
	 * 当key为null将使用默认数据源
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String key = DynamicDatasourceHolder.getDataSourceKey();
		log.info("数据源对应的key设置为："+key);
		return key;
	}
 
}

