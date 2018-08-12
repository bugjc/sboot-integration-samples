package com.bugjc.grocery.service.impl;

import com.bugjc.grocery.task.UserDrawCountTask;
import com.bugjc.grocery.util.NettyTimerUtil;
import com.bugjc.logic.util.MyCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户抽奖次数
 * @Auther: qingyang
 * @Date: 2018/6/10 23:21
 * @Description:
 */
@Component
public class UserDrawCountComponent {

    private static String totalKey = "user:draw:total";
    private static String keyPrefix = "user:draw:count:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**用户抽奖次数集合**/
    static Map<String,AtomicInteger> count = new HashMap<>();

    /**
     * 初始化用户抽奖次数
     * @param userId
     * @param total
     */
    public void init(String userId,int total){
        Integer userDrawTotal = MyCache.lfuCacheInteger.get(totalKey);
        if (userDrawTotal == null){
            MyCache.lfuCacheInteger.put(totalKey,total);
        }

        AtomicInteger originalTotal = count.get(userId);
        if (originalTotal == null){
            count.put(userId,new AtomicInteger(total));
            //1秒后同步用户抽奖计数
            NettyTimerUtil.addTaskBySeconds(syncData(userId, String.valueOf(userDrawTotal)),1);
        }
        //TODO count对象只存热点用户或可抽奖的用户
    }

    /**
     * 用户抽奖次数减1
     */
    public int decrementAndGet(String userId) {
        AtomicInteger total = count.get(userId);
        if (total.get() <= 0){
            return -1;
        }

        int number = total.decrementAndGet();
        syncData(userId, String.valueOf(number));
        return number;
    }

    /**
     * 清除用户抽奖次数
     * @param userId
     */
    public void clear(String userId){
        count.remove(userId);
    }

    /**
     * 删除抽奖次数缓存
     */
    public void removeCacheByUserDrawCount(){
        MyCache.lfuCacheInteger.remove(totalKey);
    }


    /**
     * 同步数据
     * @param key
     * @param value
     * @return
     */
    private UserDrawCountTask syncData(String key, String value){
        key = keyPrefix + key;
        return new UserDrawCountTask(stringRedisTemplate,key,value);
    }

}
