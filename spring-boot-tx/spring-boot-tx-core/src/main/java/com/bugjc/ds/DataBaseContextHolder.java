package com.bugjc.ds;

/**
 * <p>数据源
 * <p>@author yangqing
 * <p>@date 2016年11月30日
 */
public class DataBaseContextHolder {

    /**
     * 使用线程局部变量存储数据源BEAN标识
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER= new ThreadLocal<String>();

    public static void setDBType(String dbType) {
        CONTEXT_HOLDER.set(dbType);
    }

    public static String getDBType() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDBType() {
        CONTEXT_HOLDER.remove();
    }

}
