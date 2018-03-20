package com.bugjc.constant;

import java.util.Map;
import java.util.Stack;

/**
 * 定义事务线程同步变量
 */
public class TransactionContextHolder {

    /**
     * 事务组存储对象
     */
    private static final ThreadLocal<Stack<Map<String,Object>>> TX_OBJECT= new ThreadLocal<>();

    /**
     * 事务组ID存储对象
     */
    private static final ThreadLocal<String> TX_GROUP_ID = new ThreadLocal<>();

    /**
     * 记录事务参与者数量，也用于在递归调用时确定谁时事务发起方
     */
    private static final ThreadLocal<Integer> TX_ACTOR_METHOD_NUMBER = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    /**
     * 线程内部唯一序列号：自增+1
     * @return
     */
    public static int getIncrement() {
        TX_ACTOR_METHOD_NUMBER.set(TX_ACTOR_METHOD_NUMBER.get() + 1);
        return TX_ACTOR_METHOD_NUMBER.get();
    }

    /**
     * 线程内部唯一序列号：自减-1
     * @return
     */
    public static int getDecrement() {
        TX_ACTOR_METHOD_NUMBER.set(TX_ACTOR_METHOD_NUMBER.get() - 1);
        return TX_ACTOR_METHOD_NUMBER.get();
    }


    public static void setTxObject(Stack<Map<String,Object>> txObject) {
        TX_OBJECT.set(txObject);
    }

    public static Stack<Map<String,Object>> getTxObject() {
        return TX_OBJECT.get();
    }

    public static void clearTxObject() {
        TX_OBJECT.remove();
    }

    public static void setTxGroupId(String txGroupId) {
        TX_GROUP_ID.set(txGroupId);
    }

    public static String getTxGroupId() {
        return TX_GROUP_ID.get();
    }

    public static void clearTxGroupId() {
        TX_GROUP_ID.remove();
    }

    public static void clearTxActorMethodNum() {
        TX_GROUP_ID.remove();
    }

}
