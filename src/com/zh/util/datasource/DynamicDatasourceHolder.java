package com.zh.util.datasource;

/**
 * ����������Դkeyά����
 * @author WQ
 *
 */
public class DynamicDatasourceHolder {
	private static ThreadLocal<String> holder=new ThreadLocal<String>();
	
	/**
	 * ��������������Դ��Ӧ��key
	 * @param key
	 */
	public static void setDataSourceKey(String key){
		holder.set(key);
	}
	
	/**
	 * ��ȡ����������Դ��Ӧ��key
	 * @return
	 */
	public static String getDataSourceKey(){
		return holder.get();
	}
	
	/**
	 * �������������Դkey
	 */
	public static void removeDataSourceKey(){
		holder.remove();
	}
}

