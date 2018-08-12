package com.bugjc.grocery.task;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Auther: qingyang
 * @Date: 2018/6/11 23:17
 * @Description:
 */
@Data
public class UserDrawCountTask implements TimerTask {

    private String key;
    private String value;
    private StringRedisTemplate stringRedisTemplate;
    public UserDrawCountTask(StringRedisTemplate stringRedisTemplate,String key,String value){
        this.key = key;
        this.value = value;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        //同步计数并隔天失效
        //stringRedisTemplate.opsForValue().set(getKey(), getValue(), DataTimeUtil.getRemainSecondsOneDay(new Date()),TimeUnit.SECONDS);
    }
}
