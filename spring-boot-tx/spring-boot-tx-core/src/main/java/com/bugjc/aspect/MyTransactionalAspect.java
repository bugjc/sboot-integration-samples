package com.bugjc.aspect;

import com.bugjc.configurer.TxConfigurer;
import com.bugjc.ds.TransactionalContextHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Aspect
@Order(1)
@Component
public class MyTransactionalAspect implements InstantiationAwareBeanPostProcessor {

    private Logger logger = Logger.getLogger(getClass());
    @Resource
    private TxConfigurer txConfigurer;
    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Pointcut("bean(*Mapper)")
    public void aroundPointcutDao(){}

    @Around("aroundPointcutDao()")
    public Object doConcurrentOperationDao(ProceedingJoinPoint pjp) throws Throwable {

        List<String> methods = txConfigurer.getMethod();
        TransactionalMethod.MethodAttribute methodAttribute = TransactionalMethod.getInstance().matcherRules(pjp.getSignature().getName(), methods);

        try {
            if (methodAttribute != null && !TransactionalMethod.getInstance().matcherReadOnly(methodAttribute.getReadOnly())){
                //事物定义类
                DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                //获取事物对象
                TransactionStatus status = dataSourceTransactionManager.getTransaction(transactionDefinition);
                logger.info(pjp.getSignature().getName());
                Stack<Map<String, Object>> transMap = TransactionalContextHolder.getTransMethod();
                Map<String,Object> hashMap = new HashMap<>();
                hashMap.put("dstm",dataSourceTransactionManager);
                hashMap.put("ts",status);
                hashMap.put("ma",methodAttribute);
                transMap.push(hashMap);
                TransactionalContextHolder.setTransMethod(transMap);
            }
            Object obj = pjp.proceed();
            return obj;
        }catch(Exception ex) {
            throw ex;
        }
    }


    @Pointcut("bean(*Service)")
    public void aroundPointcutService(){}

    @Around("aroundPointcutService()")
    public Object doConcurrentOperationService(ProceedingJoinPoint pjp) throws Throwable {
        try {
            logger.info("开启事物"+pjp.getSignature().getName());
            Stack<Map<String, Object>> transMap = TransactionalContextHolder.getTransMethod();
            if(transMap == null){
                transMap = new Stack<>();
            }
            TransactionalContextHolder.setTransMethod(transMap);
            Object obj = pjp.proceed();
            Stack<Map<String, Object>> map = TransactionalContextHolder.getTransMethod();
            while (!map.empty()){
                Map<String,Object> hashMap = (HashMap) map.pop();
                DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) hashMap.get("dstm");
                TransactionStatus status = (TransactionStatus) hashMap.get("ts");
                dataSourceTransactionManager.commit(status);
            }
            logger.info("提交事物");
            return obj;
        }catch(Exception ex) {
            Stack<Map<String, Object>> map = TransactionalContextHolder.getTransMethod();
            while (!map.empty()){
                Map<String,Object> hashMap = (HashMap) map.pop();
                DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) hashMap.get("dstm");
                TransactionStatus status = (TransactionStatus) hashMap.get("ts");
                TransactionalMethod.MethodAttribute methodAttribute = (TransactionalMethod.MethodAttribute) hashMap.get("ma");
                ex = TransactionalMethod.getInstance().matcherException(TransactionalMethod.exMap,methodAttribute.getRollbackFor());
                dataSourceTransactionManager.rollback(status);
            }
            logger.info("回滚事物");
            throw ex;
        }

    }

    public TxConfigurer getTxConfigurer() {
        return txConfigurer;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInstantiation");
        return beanClass;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInstantiation");
        return false;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.out.println("postProcessPropertyValues");
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization");
        return bean;
    }
}
