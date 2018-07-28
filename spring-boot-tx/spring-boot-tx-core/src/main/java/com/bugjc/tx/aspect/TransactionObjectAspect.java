package com.bugjc.tx.aspect;

import com.alibaba.fastjson.JSONObject;
import com.bugjc.tx.configurer.TxConfigurer;
import com.bugjc.tx.constant.TransactionContextHolder;
import com.bugjc.tx.util.TransactionRuleUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
 * @author aoki
 */
@Slf4j
public class TransactionObjectAspect {

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
        TransactionStatus status = null;

        try {
            JSONObject ruleAttribute = TransactionRuleUtil.getInstance().matcherReadOnly(methodName,rules);
            if (ruleAttribute != null && !ruleAttribute.isEmpty()){
                log.debug(pjp.getClass()+"方法："+methodName+"加入事务组");
                //设置事务属性
                TransactionDefinition transactionDefinition = new TransactionDefinition() {
                    @Override
                    public int getPropagationBehavior() {
                        return TransactionDefinition.PROPAGATION_REQUIRES_NEW;
                    }

                    @Override
                    public int getIsolationLevel() {
                        return TransactionDefinition.ISOLATION_READ_COMMITTED;
                    }

                    @Override
                    public int getTimeout() {
                        return txConfigurer.timeout;
                    }

                    @Override
                    public boolean isReadOnly() {
                        return false;
                    }

                    @Override
                    public String getName() {
                        return null;
                    }
                };
                DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition(transactionDefinition);
                //获取事物状态
                status = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
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
            log.debug(ex.getMessage(),ex);
            throw ex;
        }
    }






}
