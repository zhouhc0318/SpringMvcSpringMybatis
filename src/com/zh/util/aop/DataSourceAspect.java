package com.zh.util.aop;

import java.lang.reflect.Method;
 
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zh.util.annotation.DynamicDataSourceAnnotation;
import com.zh.util.datasource.DynamicDatasourceHolder;
 
@Component
@Aspect
@Order(1)
public class DataSourceAspect {
	private static final Logger blog=LoggerFactory.getLogger("com.zh.util.aop.DataSourceAspect.sourceBefore");
	private static final Logger alog=LoggerFactory.getLogger("com.zh.util.aop.DataSourceAspect.sourceAfter");
	
	/**
	 * ǰ����ǿ
	 * ��ȡע���е�����Դkey
	 * ��key���õ���������,�Թ�����ԴMap�������key�ҵ���Ӧ������Դ
	 * @param point
	 */
	@Before("@annotation(com.zh.util.annotation.DynamicDataSourceAnnotation)") //ƥ�������ע��ķ���
	public void sourceBefore(JoinPoint point){
		Class<? extends Object> targetClass = point.getTarget().getClass();
		DynamicDataSourceAnnotation dynamicDataSourceAnnotation = targetClass.getAnnotation(DynamicDataSourceAnnotation.class);
		if(dynamicDataSourceAnnotation!=null){ //���ϱ�Ǹ�ע��
			String methodName = point.getSignature().getName(); //��������
			Class[] parameterTypes = ((MethodSignature)point.getSignature()).getParameterTypes(); //�������������б�
			blog.info("========== "+targetClass.getName()+"."+methodName+" ==========");
			Method method=null;
			try {
				 method = targetClass.getMethod(methodName, parameterTypes); //��ȡ����
			} catch (Exception e) {
				blog.error("========== sourceBefore get method error ==========" ,e);
			} 
			if(method!=null && method.isAnnotationPresent(DynamicDataSourceAnnotation.class)){
				DynamicDataSourceAnnotation da = method.getAnnotation(DynamicDataSourceAnnotation.class);
				String dataSource = da.dataSource();
				DynamicDatasourceHolder.setDataSourceKey(dataSource);
				blog.info("========== 	set dataSource "+dataSource+" ==========");
			}
		}
		
	}
	
	/**
	 * ������ǿ
	 * ��ȡע��ֵ������������������ֵ
	 * @param point
	 */
	@After("@annotation(com.zh.util.annotation.DynamicDataSourceAnnotation)") //ƥ�������ע��ķ���
	public void sourceAfter(JoinPoint point){
		Class<? extends Object> targetClass = point.getTarget().getClass();
		DynamicDataSourceAnnotation dynamicDataSourceAnnotation = targetClass.getAnnotation(DynamicDataSourceAnnotation.class);
		if(dynamicDataSourceAnnotation!=null){  //���ϱ�Ǹ�ע��
			String methodName=point.getSignature().getName(); //��������
			Class[] parameterTypes = ((MethodSignature)point.getSignature()).getParameterTypes(); //�������������б�
			alog.info("========== "+targetClass.getName()+"."+methodName+" ==========");
			Method method=null;
			try{
				method=targetClass.getMethod(methodName, parameterTypes);
			}catch(Exception e){
				alog.error("========== sourceAfter get method error ==========" ,e);
			}
			if(method!=null && method.isAnnotationPresent(DynamicDataSourceAnnotation.class)){
				DynamicDataSourceAnnotation da = method.getAnnotation(DynamicDataSourceAnnotation.class);
				String dataSource = da.dataSource();
				alog.info("========== remove dataSource "+dataSource+" ==========");
				DynamicDatasourceHolder.removeDataSourceKey();
			}
		}
	}
}

