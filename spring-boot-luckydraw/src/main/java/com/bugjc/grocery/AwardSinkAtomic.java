package com.bugjc.grocery;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 奖品池计数
 * @Auther: qingyang
 * @Date: 2018/6/10 23:21
 * @Description:
 */
public class AwardSinkAtomic {

    static AtomicInteger count = new AtomicInteger(0);

    /**
     * 初始化奖品池
     * @param total
     */
    public static void init(int total){
        count = new AtomicInteger(total);
    }

    /**
     * 奖品池-1
     */
     public static int addCount() {
        return count.incrementAndGet();
    }


}
