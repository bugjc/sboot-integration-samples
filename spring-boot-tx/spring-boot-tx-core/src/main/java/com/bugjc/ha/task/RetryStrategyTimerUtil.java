package com.bugjc.ha.task;

import com.bugjc.ha.util.NettyTimerUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RetryStrategyTimerUtil {

    /**
     * 添加一个重试任务
     * 默认一秒后执行
     * @param map
     */
    public static void addRetryStrategyTimer(Map<String,Object> map){
        NettyTimerUtil.addTask(new RetryStrategyTask(map), 1, TimeUnit.SECONDS);
    }

    /**
     * 添加一个重试任务
     * 自定延迟时间
     * @param map
     */
    public static void addRetryStrategyTimer(Map<String,Object> map,int expTime,TimeUnit timeUnit){
        NettyTimerUtil.addTask(new RetryStrategyTask(map), expTime, timeUnit);
    }

}
