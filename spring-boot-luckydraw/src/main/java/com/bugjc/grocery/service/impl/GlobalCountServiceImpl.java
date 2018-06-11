package com.bugjc.grocery.service.impl;

import com.bugjc.grocery.GlobalKeyConstants;
import com.bugjc.grocery.service.GlobalCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: qingyang
 * @Date: 2018/6/5 22:52
 * @Description:
 */
@Service
public class GlobalCountServiceImpl implements GlobalCountService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String,Integer> redisTemplate;
    @Autowired
    private UserDrawCountComponent userDrawCountComponent;

    @Override
    public int drawCount(boolean isDel) {

        String key = GlobalKeyConstants.getActivityDrawCountKey();
        if (isDel){
            redisTemplate.delete(key);
        }

        Integer value = 10;//10次抽奖机会
        Integer total = redisTemplate.opsForValue().getAndSet(key,value);
        if (total == null){
            total = value;
        }

        return total;
    }

    @Override
    public int awardSinkCount(boolean isDel) {
        String key = GlobalKeyConstants.getActivityEverydayAwardTotalKey();
        if (isDel){
            redisTemplate.delete(key);
        }

        Integer value = 200;//200个奖品
        Integer total = redisTemplate.opsForValue().getAndSet(key,value);
        if (total == null){
            total = value;
        }

        return total;
    }

    @Override
    public int awardSinkCountByGiftId(String giftId) {
        return 0;
    }

    @Override
    public int everyDayUserDrawCountByUserId(String userId) {
        userDrawCountComponent.init(userId,new AtomicInteger(this.drawCount(false)));
        return userDrawCountComponent.decrementAndGet(userId);
    }
}
