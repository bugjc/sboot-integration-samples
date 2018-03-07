package com.bugjc.configurer;

import com.bugjc.aspect.DynamicDataSourceAspect;
import com.bugjc.aspect.MyTransactionalAspect;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;


//@Component
public class TransPointcutBeanInit2 implements BeanFactoryPostProcessor {

    private Logger logger = Logger.getLogger(getClass());




    /**
     * 修改注解元数据
     * @param c
     * @param method
     * @param value
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void updAnnotationPropertyByMethod(Class c, String method, String value) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        Method pointcutMethod = c.getMethod(method,null);
        Pointcut pointcut = pointcutMethod.getAnnotation(Pointcut.class);
        //获取 foo 这个代理实例所持有的 InvocationHandler
        InvocationHandler h = Proxy.getInvocationHandler(pointcut);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField = h.getClass().getDeclaredField("memberValues");
        // 因为这个字段事 private final 修饰，所以要打开权限
        hField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) hField.get(h);
        // 修改 value 属性值
        memberValues.put("value", value);
        // 获取 foo 的 value 属性值
        value = pointcut.value();
        System.out.println(value);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        MyTransactionalAspect myTransactionalAspect = beanFactory.getBean("myTransactionalAspect",MyTransactionalAspect.class);
        try {
            updAnnotationPropertyByMethod(myTransactionalAspect.getClass(),"aroundPointcutDao","execution(* com.bugjc.dao..*.*(..))");
            updAnnotationPropertyByMethod(myTransactionalAspect.getClass(),"aroundPointcutService","execution(* com.bugjc.service..*.*(..))");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error("找不到MyTransactional.class中的pointcut方法。");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            logger.error("找不到MyTransactional.class中的pointcut字段。");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error("找不到MyTransactional.class中的pointcut方法。");
        }
    }
}
