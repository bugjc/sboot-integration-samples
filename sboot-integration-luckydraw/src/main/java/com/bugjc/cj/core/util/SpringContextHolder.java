package com.bugjc.cj.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(SpringContextHolder.applicationContext == null){
			SpringContextHolder.applicationContext = applicationContext;
		}
		
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	
	public static Object getBean(String name){
		return getApplicationContext().getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz){
		return getApplicationContext().getBean(clazz);
	}
	
}
