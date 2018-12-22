package com.zh.util.datasource;

/**
 * 上下文数据源key维护类
 * @author WQ
 *
 */
public class DynamicDatasourceHolder {
	private static ThreadLocal<String> holder=new ThreadLocal<String>();
	
	/**
	 * 设置上下文数据源对应的key
	 * @param key
	 */
	public static void setDataSourceKey(String key){
		holder.set(key);
	}
	
	/**
	 * 获取上下文数据源对应的key
	 * @return
	 */
	public static String getDataSourceKey(){
		return holder.get();
	}
	
	/**
	 * 清除上下文数据源key
	 */
	public static void removeDataSourceKey(){
		holder.remove();
	}
}

