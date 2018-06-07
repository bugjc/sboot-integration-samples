package com.bugjc.logic;

import cn.hutool.core.date.DateUnit;
import com.bugjc.grocery.GlobalKeyConstants;
import com.bugjc.util.dto.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: qingyang
 * @Date: 2018/6/6 23:24
 * @Description:
 */
@Component
public class DrawRedisResult {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置抽奖结果
     * @param code
     * @param msg
     */
    public void setQueryResult(String queryId,int code,String msg){
        String key = GlobalKeyConstants.getActivityDrawResultQueryKey(queryId);
        stringRedisTemplate.opsForValue().set(key,new Result().setCode(code).setMessage(msg).toString(),DateUnit.MINUTE.getMillis());
    }

    /**
     * 获取抽奖结果
     * @param queryId
     * @return
     */
    public String getQueryResult(String queryId){
        String key = GlobalKeyConstants.getActivityDrawResultQueryKey(queryId);
        return stringRedisTemplate.opsForValue().get(key);
    }
}
