package com.bugjc.tx.core.util;

import com.xiaoleilu.hutool.cache.Cache;
import com.xiaoleilu.hutool.cache.CacheUtil;

public class MyCache {


    public static MyCache getInstance() {
        return HelperHolder.INSTANCE;
    }

    private static class HelperHolder {
        private static final MyCache INSTANCE = new MyCache();
    }

    private MyCache(){};

    public static Cache<String, String> lfuCache = CacheUtil.newFIFOCache(10000);
}
