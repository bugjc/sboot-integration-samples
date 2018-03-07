package com.bugjc.aspect;

import com.bugjc.configurer.TxConfigurer;
import com.bugjc.ds.DataBaseContextHolder;
import com.bugjc.ds.TargetDataSource;
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

import javax.annotation.Resource;
import java.lang.reflect.Method;


/**
 * <p>切换数据源
 * <p>@author yangqing
 * <p>@date 2016年11月30日
 */
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    @Resource
    private TxConfigurer txConfigurer;

    @Pointcut("bean(*Mapper)")
    public void pointcutDataSource(){}

    @Before("pointcutDataSource()")
    public void changeDataSource(JoinPoint point) throws Throwable {

        Object target = point.getTarget();
        String method = point.getSignature().getName();
        logger.debug(method);
        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();

        if(classz[0].isAnnotationPresent(TargetDataSource.class)){
            TargetDataSource data = classz[0].getAnnotation(TargetDataSource.class);
            DataBaseContextHolder.setDBType(data.value());
        }

        Method m = classz[0].getMethod(method, parameterTypes);

        //方法级别
        if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
            TargetDataSource data = m.getAnnotation(TargetDataSource.class);
            DataBaseContextHolder.setDBType(data.value());
        }

    }

    @After("pointcutDataSource()")
    public void restoreDataSource(JoinPoint point) {
        DataBaseContextHolder.clearDBType();
    }

    public TxConfigurer getTxConfigurer() {
        return txConfigurer;
    }

}
