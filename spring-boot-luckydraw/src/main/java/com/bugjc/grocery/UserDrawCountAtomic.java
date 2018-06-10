package com.bugjc.grocery;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户抽奖次数
 * @Auther: qingyang
 * @Date: 2018/6/10 23:21
 * @Description:
 */
public class UserDrawCountAtomic {

    static Map<String,AtomicInteger> count = new HashMap<>();

    /**
     * 初始化用户抽奖次数
     * @param total
     */
    public static void init(String userId,AtomicInteger total){
        AtomicInteger originalTotal = count.get(userId);
        if (originalTotal == null){
            count.put(userId,total);
        }
        //TODO 发起异步任务：同步抽奖次数到redis
        //TODO count对象只存热点用户或可抽奖的用户
    }

    /**
     * 用户抽奖次数减1
     */
    public static int decrementAndGet(String userId) {
        AtomicInteger total = count.get(userId);
        return total.decrementAndGet();
    }

    /**
     * 清除用户抽奖次数
     * @param userId
     */
    public static void clear(String userId){
        if (count.containsKey(userId)){
            count.remove(userId);
        }
    }

}
