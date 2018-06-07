package com.bugjc.util;

import cn.hutool.core.collection.BoundedPriorityQueue;
import com.alibaba.fastjson.JSONObject;

import java.util.Comparator;

public class LuckyDrawQueueUtil {

    /**
     * 抽奖任务队列
     */
    private BoundedPriorityQueue<JSONObject> luckyDrawQueue = new BoundedPriorityQueue<JSONObject>(100000,new Comparator<JSONObject>(){

        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            return o1 == o2 ? 0 : -1;
        }

    });

    /**
     * 中奖任务队列
     */
    private BoundedPriorityQueue<JSONObject> LuckyDrawLssAwardsQueue = new BoundedPriorityQueue<JSONObject>(100000,new Comparator<JSONObject>(){

        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            return o1 == o2 ? 0 : -1;
        }

    });

    public BoundedPriorityQueue<JSONObject> getLuckyDrawQueue(){
        return luckyDrawQueue;
    }

    public BoundedPriorityQueue<JSONObject> getLuckyDrawLssAwardsQueue(){
        return LuckyDrawLssAwardsQueue;
    }

}
