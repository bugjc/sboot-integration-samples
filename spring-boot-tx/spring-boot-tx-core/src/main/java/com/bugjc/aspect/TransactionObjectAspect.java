package com.bugjc.aspect;

import com.alibaba.fastjson.JSONObject;
import com.bugjc.configurer.TxConfigurer;
import com.bugjc.constant.TransactionContextHolder;
import com.bugjc.util.TransactionRuleUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 开启一个新的事务并追加到事务组对象
 */
public class TransactionObjectAspect {

    private Logger logger = Logger.getLogger(getClass());
    @Resource
    private TxConfigurer txConfigurer;
    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 在数据访问层为匹配到的方法开启事务
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object doConcurrentOperationDao(ProceedingJoinPoint pjp) throws Throwable {

        //获取执行的方法名称
        String methodName = pjp.getSignature().getName();
        //获取事务所有规则方法
        List<String> rules = txConfigurer.getRule();

        try {
            JSONObject ruleAttribute = TransactionRuleUtil.getInstance().matcherReadOnly(methodName,rules);
            if (ruleAttribute != null && !ruleAttribute.isEmpty()){
                //开启一个新的事务
                DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                //获取事物状态
                TransactionStatus status = dataSourceTransactionManager.getTransaction(transactionDefinition);
                logger.info(pjp.getSignature().getName());
                Stack<Map<String, Object>> transMap = TransactionContextHolder.getTxObject();
                //保存事务管器、事务状态和所匹配到的规则到线程变量
                Map<String,Object> hashMap = new HashMap<>();
                hashMap.put("dstm",dataSourceTransactionManager);
                hashMap.put("ts",status);
                hashMap.put("ma",ruleAttribute);
                transMap.push(hashMap);
                TransactionContextHolder.setTxObject(transMap);
            }
            Object obj = pjp.proceed();
            return obj;
        }catch(Exception ex) {
            throw ex;
        }
    }






}
