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
	 * 前置增强
	 * 获取注解中的数据源key
	 * 把key设置到上下文中,以供数据源Map根据这个key找到对应的数据源
	 * @param point
	 */
	@Before("@annotation(com.zh.util.annotation.DynamicDataSourceAnnotation)") //匹配有这个注解的方法
	public void sourceBefore(JoinPoint point){
		Class<? extends Object> targetClass = point.getTarget().getClass();
		DynamicDataSourceAnnotation dynamicDataSourceAnnotation = targetClass.getAnnotation(DynamicDataSourceAnnotation.class);
		if(dynamicDataSourceAnnotation!=null){ //类上标记该注解
			String methodName = point.getSignature().getName(); //方法名称
			Class[] parameterTypes = ((MethodSignature)point.getSignature()).getParameterTypes(); //方法参数类型列表
			blog.info("========== "+targetClass.getName()+"."+methodName+" ==========");
			Method method=null;
			try {
				 method = targetClass.getMethod(methodName, parameterTypes); //获取方法
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
	 * 后置增强
	 * 获取注解值，在上下文中清除这个值
	 * @param point
	 */
	@After("@annotation(com.zh.util.annotation.DynamicDataSourceAnnotation)") //匹配有这个注解的方法
	public void sourceAfter(JoinPoint point){
		Class<? extends Object> targetClass = point.getTarget().getClass();
		DynamicDataSourceAnnotation dynamicDataSourceAnnotation = targetClass.getAnnotation(DynamicDataSourceAnnotation.class);
		if(dynamicDataSourceAnnotation!=null){  //类上标记该注解
			String methodName=point.getSignature().getName(); //方法名称
			Class[] parameterTypes = ((MethodSignature)point.getSignature()).getParameterTypes(); //方法参数类型列表
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

