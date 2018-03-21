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
     * 记录事务参与者数量，也用于确定谁时事务发起方
     */
    private static final ThreadLocal<Integer> TX_GROUP_ID = new ThreadLocal<Integer>(){
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
        TX_GROUP_ID.set(TX_GROUP_ID.get() + 1);
        return TX_GROUP_ID.get();
    }

    /**
     * 线程内部唯一序列号：自减-1
     * @return
     */
    public static int getDecrement() {
        TX_GROUP_ID.set(TX_GROUP_ID.get() - 1);
        return TX_GROUP_ID.get();
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


    public static void clearTxGroupId() {
        TX_GROUP_ID.remove();
    }
}
