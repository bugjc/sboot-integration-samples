package com.bugjc.cj.core.util;


import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

import java.util.List;

public class MyCache {

    /**
     * 初始化10000容量
     */
    public static Cache<String, List<Integer>> lfuCache = CacheUtil.newFIFOCache(10000);


    /**
     * 初始化10000容量
     */
    public static Cache<String, Integer> lfuCacheInteger = CacheUtil.newFIFOCache(1000);


    /**
     * 用户请求次数限制队列
     */
    private static Cache<String, Integer> userRequestLimit = CacheUtil.newTimedCache(20);

    /**
     * 请求频次限制
     * @param key
     * @return
     */
    public static boolean userRequestLimit(String key){
        if (!userRequestLimit.containsKey(key)){
            userRequestLimit.put(key,null,400);
            return false;
        }
        return true;
    }

}
