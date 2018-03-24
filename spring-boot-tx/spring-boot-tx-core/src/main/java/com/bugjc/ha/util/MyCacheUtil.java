package com.bugjc.ha.util;

import com.xiaoleilu.hutool.cache.Cache;
import com.xiaoleilu.hutool.cache.CacheUtil;
import com.xiaoleilu.hutool.cache.impl.TimedCache;

public class MyCacheUtil {

    /**
     * 初始化10000容量
     */
    public static Cache<String, String> lfuCache = CacheUtil.newFIFOCache(10000);

    /**
     * 默认5毫秒检查一次过期
     */
    public static TimedCache<String, String> timedCache = CacheUtil.newTimedCache(5);
}
