package com.bugjc.grocery.service.impl;

import com.bugjc.logic.util.MyCache;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 奖品池计数
 * @Auther: qingyang
 * @Date: 2018/6/10 23:21
 * @Description:
 */
@Component
public class AwardSinkComponent {

    private static String awardSinkKey = "award:sink";
    static AtomicInteger count = new AtomicInteger();

    /**
     * 初始化奖品池
     * @param total
     */
    public static void init(int total){

        Integer awardSinkTotal = MyCache.lfuCacheInteger.get(awardSinkKey);
        if (awardSinkTotal == null){
            MyCache.lfuCacheInteger.put(awardSinkKey,total);
        }

        if (count == null){
            count = new AtomicInteger(total);
        }
    }

    /**
     * 获取奖金池
     * @return
     */
    public int getAwardSink(){
        return count.get();
    }

    /**
     * 奖品池-1
     */
    public int decrementAndGet() {
        return count.decrementAndGet();
    }

    /**
     * 删除奖金池
     */
    public void removeCacheByAwardSink(){
        MyCache.lfuCacheInteger.remove(awardSinkKey);
    }


}
