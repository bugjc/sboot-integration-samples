package com.bugjc.aspect;

import com.alibaba.fastjson.JSONObject;
import com.bugjc.configurer.TxConfigurer;
import com.bugjc.constant.TransactionContextHolder;
import com.bugjc.exception.TransactionException;
import com.bugjc.util.TransactionRuleUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 事务组切面：定义事务的起点，即事务发起方
 * @author aoki
 * @create 2018-03-21
 */
public class TransactionGroupAspect {

    private Logger log = Logger.getLogger(getClass());
    @Resource
    private TxConfigurer txConfigurer;

    /**
     * 事务发起方创建事务组控制事务的提交与回滚
     * @param pjp
     * @throws Throwable
     */
    public Object doConcurrentOperationService(ProceedingJoinPoint pjp) throws Throwable {

        int groupId = 0;//默认0为事务发起方
        Stack<Map<String, Object>> txMaps = null;//定义LIFO的事务组对象
        try {
            //获取事务组对象
            txMaps = TransactionContextHolder.getTxObject();
            if(txMaps == null){
                log.debug("开启事务");
                //记录方法开始执行的时间
                TransactionContextHolder.setTime(System.currentTimeMillis());
                //初始化事务组对象
                TransactionContextHolder.setTxObject(new Stack<>());
            }
            //计算线程内总共执行的方法数量
            TransactionContextHolder.getIncrement();

            //执行业务
            Object obj = pjp.proceed();

            //计算线程内总共执行的方法数量
            groupId = TransactionContextHolder.getDecrement();
            txMaps = TransactionContextHolder.getTxObject();
            if (groupId == 0){ //确定事务发起方

                //计算事务组总执行时间加上偏移时间并确定事务组内事务是否超时
                long second = (System.currentTimeMillis() - TransactionContextHolder.getTime()) / 1000 + txConfigurer.offsetTime;
                if (second >= txConfigurer.timeout){
                    throw TransactionException.TX_GROUP_TIMEOUT;
                }

                log.debug("提交事务");
                while (txMaps != null && !txMaps.empty()){
                    //获取事务对象
                    Map<String,Object> hashMap = txMaps.pop();
                    //获取事务管理器
                    DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) hashMap.get("dstm");
                    //获取事务状态
                    TransactionStatus status = (TransactionStatus) hashMap.get("ts");
                    //事务提交
                    dataSourceTransactionManager.commit(status);
                }
                //清理事务组对象和事务发起方id
                TransactionContextHolder.clearTxObject();
                TransactionContextHolder.clearTxGroupId();
            }

            return obj;
        }catch(Exception ex) {

            log.debug("回滚事务");
            txMaps = TransactionContextHolder.getTxObject();
            while (txMaps != null && !txMaps.empty()){
                //获取事务对象
                Map<String,Object> hashMap = txMaps.pop();
                //获取事务管理器
                DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) hashMap.get("dstm");
                //获取事务状态
                TransactionStatus status = (TransactionStatus) hashMap.get("ts");
                //获取事务失败的方法
                JSONObject ruleAttributeMap = (JSONObject) hashMap.get("ma");
                //获取失败方法匹配抛出的异常
                //ex = TransactionRuleUtil.getInstance().matcherException(TransactionRuleUtil.exMap,ruleAttributeMap.getString("rollback"),ex);
                //事务回滚
                dataSourceTransactionManager.rollback(status);
            }

            //清理事务组对象和事务发起方id
            TransactionContextHolder.clearTxObject();
            TransactionContextHolder.clearTxGroupId();
            log.error(ex.getMessage(),ex);
            throw ex;
        }

    }


}
