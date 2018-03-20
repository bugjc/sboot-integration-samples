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

}
