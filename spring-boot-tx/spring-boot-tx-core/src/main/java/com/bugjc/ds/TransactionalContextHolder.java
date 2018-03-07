package com.bugjc.ds;

import java.util.Map;
import java.util.Stack;

/**
 * <p>数据源
 * <p>@author yangqing
 * <p>@date 2016年11月30日
 */
public class TransactionalContextHolder {

    private static final ThreadLocal<Stack<Map<String,Object>>> CONTEXT_HOLDER= new ThreadLocal<>();

    public static void setTransMethod(Stack<Map<String,Object>> methodMap) {
        CONTEXT_HOLDER.set(methodMap);
    }

    public static Stack<Map<String,Object>> getTransMethod() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearTransMethod() {
        CONTEXT_HOLDER.remove();
    }

}
