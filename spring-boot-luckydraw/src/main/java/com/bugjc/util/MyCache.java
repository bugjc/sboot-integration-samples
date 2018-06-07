package com.bugjc.util;


import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

import java.util.List;

public class MyCache {

    /**
     * 初始化10000容量
     */
    public static Cache<String, List<Integer>> lfuCache = CacheUtil.newFIFOCache(10000);
}
