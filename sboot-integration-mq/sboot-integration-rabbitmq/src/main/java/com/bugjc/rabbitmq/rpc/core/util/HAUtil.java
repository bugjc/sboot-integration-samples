package com.bugjc.rabbitmq.rpc.core.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class HAUtil {

    /**默认5毫秒检查一次过期**/
    private static TimedCache<String, Object> timedCache = CacheUtil.newTimedCache(5);

    /**初始化1000个格子并以每1秒执行一个格子**/
    private static Timer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 1000);

    /**获取缓存**/
    public static Object getCacheByKey(String key){
        return timedCache.get(key);
    }

    /**获取缓存**/
    public static boolean containsCacheKey(String key){
        return timedCache.containsKey(key);
    }

    /**添加缓存**/
    public static void addCache(String key, Object object, long timeout){
        timedCache.put(key,object,timeout);
    }

    /**删除缓存**/
    public static void delCache(String key){
        timedCache.remove(key);
    }


    /**根据任务的过期时间计算出格子位置并添加(分钟)**/
    public static Timeout addTask(TimerTask task, int expTime){
        return timer.newTimeout(task, expTime, TimeUnit.MINUTES);
    }

    /**根据任务的过期时间计算出格子位置并添加(自定义)**/
    public static Timeout addTask(TimerTask task,int expTime,TimeUnit timeUnit){
        return timer.newTimeout(task, expTime, timeUnit);
    }

}
