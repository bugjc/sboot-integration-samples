package com.bugjc.aspect;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.transaction.TransactionStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 事务组切面：定义事务的起点，即事务发起方
 */
public class TransactionGroupAspect {

    private Logger logger = Logger.getLogger(getClass());

    /**
     * 事务发起方创建事务组控制事务的提交与回滚
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object doConcurrentOperationService(ProceedingJoinPoint pjp) throws Throwable {

        String groupId = null;//定义事务组发起方id
        Stack<Map<String, Object>> txMaps = null;//定义LIFO的事务组对象
        try {

            //获取事务组对象
            txMaps = TransactionContextHolder.getTxObject();
            if(txMaps == null){
                txMaps = new Stack<>();
                groupId = pjp.getThis().getClass().getName().hashCode()+"";
                TransactionContextHolder.setTxGroupId(groupId);
                //初始化事务组对象
                TransactionContextHolder.setTxObject(txMaps);
            }

            //执行业务
            Object obj = pjp.proceed();

            groupId = pjp.getThis().getClass().getName().hashCode()+"";
            txMaps = TransactionContextHolder.getTxObject();
            String currentTxGroupId = TransactionContextHolder.getTxGroupId();
            if (currentTxGroupId !=null && currentTxGroupId.equals(groupId)){ //确定事务发起方

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
                ex = TransactionRuleUtil.getInstance().matcherException(TransactionRuleUtil.exMap,ruleAttributeMap.getString("rollback"),ex.getMessage());
                //事务回滚
                dataSourceTransactionManager.rollback(status);
            }

            //清理事务组对象和事务发起方id
            TransactionContextHolder.clearTxObject();
            TransactionContextHolder.clearTxGroupId();
            logger.error(ex.getMessage());
            throw ex;
        }

    }

    /**
     * 手动开启事物
     */
    public void manualStartTrans(){
        //开启前清除上一次副本变量
        TransactionContextHolder.clearTxObject();
        TransactionContextHolder.clearTxGroupId();
        Stack<Map<String, Object>> txMaps = TransactionContextHolder.getTxObject();
        if(txMaps == null){
            txMaps = new Stack<>();
        }
        TransactionContextHolder.setTxObject(txMaps);
    }

    /**
     * 手动提交事物，针对在service层做循环任务处理的方法
     */
    public void manualCommit() {
        Stack<Map<String, Object>> txMaps = TransactionContextHolder.getTxObject();
        while (txMaps != null && !txMaps.empty()) {
            Map<String, Object> hashMap = txMaps.pop();
            DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) hashMap.get("dstm");
            TransactionStatus status = (TransactionStatus) hashMap.get("ts");
            dataSourceTransactionManager.commit(status);
        }
        //提交事务后清除
        TransactionContextHolder.clearTxObject();
        TransactionContextHolder.clearTxGroupId();
    }

    /**
     * 手动回滚事物
     */
    public void manualRollback(){
        Stack<Map<String, Object>> map = TransactionContextHolder.getTxObject();
        while (map != null && !map.empty()){
            Map<String,Object> hashMap = (HashMap) map.pop();
            DataSourceTransactionManager dataSourceTransactionManager = (DataSourceTransactionManager) hashMap.get("dstm");
            TransactionStatus status = (TransactionStatus) hashMap.get("ts");
            dataSourceTransactionManager.rollback(status);
        }
        //提交事务后清除
        TransactionContextHolder.clearTxObject();
        TransactionContextHolder.clearTxGroupId();
    }





}
