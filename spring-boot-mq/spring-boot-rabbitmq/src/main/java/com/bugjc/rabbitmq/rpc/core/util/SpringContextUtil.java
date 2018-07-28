package com.bugjc.rabbitmq.rpc.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 利用静态内部类初始化ApplicationContext对象
 * @author aoki
 */
@Slf4j
public class SpringContextUtil{

    private static ApplicationContext context = null;


    public static ApplicationContext getApplicationContext() {
        return context;
    }

    private static void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }


    static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) context.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return context.isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return context.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return context.getAliases(name);
    }

    @Component
    static class MyApplicationContext implements ApplicationContextAware {
        @Override
        public synchronized void setApplicationContext(ApplicationContext applicationContextAware) throws BeansException {
            log.info("设置ApplicationContext");
            SpringContextUtil.setApplicationContext(applicationContextAware);
            this.log();
        }

        public void log(){
            log.info("设置ApplicationContext成功！");
        }

    }

}
