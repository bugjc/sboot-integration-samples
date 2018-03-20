package com.bugjc.core.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 切换数据源
 * @author yangqing
 * @create 2016年11月30日
 */
@Aspect
@Order(-10)
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);


    @Pointcut("execution(* com.bugjc.dao..*.*(..))")
    public void dynamicDataSource(){}

    @Before("dynamicDataSource()")
    public void changeDataSource(JoinPoint point) throws Throwable {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        logger.debug(method);
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
