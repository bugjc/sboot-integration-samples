package com.bugjc.tx.core.ds;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 切换数据源
 * @author yangqing
 * @create 2016年11月30日
 */
@Slf4j
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {


    @Pointcut("execution(* com.bugjc.tx.dao..*.*(..))")
    public void dynamicDataSource(){}

    @Before("dynamicDataSource()")
    public void changeDataSource(JoinPoint point) throws Throwable {

        Object target = point.getTarget();
        String method = point.getSignature().getName();
        log.debug(method);
        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();

        if(classz[0].isAnnotationPresent(TargetDataSource.class)){
            TargetDataSource data = classz[0].getAnnotation(TargetDataSource.class);
            DataSourceContextHolder.setDBType(data.value());
        }

        Method m = classz[0].getMethod(method, parameterTypes);

        //方法级别
        if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
            TargetDataSource data = m.getAnnotation(TargetDataSource.class);
            DataSourceContextHolder.setDBType(data.value());
        }

    }

    @After("dynamicDataSource()")
    public void restoreDataSource(JoinPoint point) {
        DataSourceContextHolder.clearDBType();
    }

}
