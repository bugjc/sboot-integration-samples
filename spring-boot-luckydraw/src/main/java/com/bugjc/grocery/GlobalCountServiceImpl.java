package com.bugjc.grocery;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
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

        String date = DateUtil.formatDate(new Date());
        String key = GlobalKeyConstants.getActivityEverydayUserDrawCountKey(userId);

        AtomicInteger total = null;
        AtomicInteger currentTotal = null;

        String value = stringRedisTemplate.opsForValue().get(key);
        if (value != null){
            JSONObject jsonObject = JSON.parseObject(value);
            String oDate = jsonObject.getString("time");
            //每隔一天重置抽奖次数
            if (oDate.compareTo(date) < 0){
                stringRedisTemplate.delete(key);
                return this.everyDayUserDrawCountByUserId(userId);
            }

            total =  new AtomicInteger(jsonObject.getInteger("total"));
            currentTotal = new AtomicInteger((jsonObject.getInteger("currentTotal")));
        }else{
            //设置用户抽奖次数
            total = new AtomicInteger(this.drawCount(false));
            currentTotal = new AtomicInteger(0);
        }

        currentTotal.getAndIncrement();
        //设置缓存
        JSONObject result = new JSONObject();
        result.put("time",date);
        result.put("total", total.get());
        result.put("currentTotal",currentTotal.get());

        if (total.get() < currentTotal.get()) {
            result.put("currentTotal", -1);
        }else {
            stringRedisTemplate.opsForValue().set(key,result.toJSONString());
        }

        return currentTotal.get();
    }
}
