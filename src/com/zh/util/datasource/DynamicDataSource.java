package com.zh.util.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
/**
 * �Զ�������Դ
 * @author WQ
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	private static final Logger log=LoggerFactory.getLogger(DynamicDataSource.class);
	
	/**
	 * ��д��ȡDataSource��key�ķ���
	 * ����Դÿ�λ�ȡ����ʱ����ø÷����õ�����Դ��Ӧ��key
	 * ��keyΪnull��ʹ��Ĭ������Դ
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String key = DynamicDatasourceHolder.getDataSourceKey();
		log.info("����Դ��Ӧ��key����Ϊ��"+key);
		return key;
	}
 
}

