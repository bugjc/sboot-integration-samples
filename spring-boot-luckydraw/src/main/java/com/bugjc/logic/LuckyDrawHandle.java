package com.bugjc.logic;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.grocery.GlobalCountService;
import com.bugjc.logic.config.GlobalProperty;
import com.bugjc.util.LuckyDrawQueueUtil;
import com.bugjc.util.MyCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 幸运抽奖处理
 * @Auther: qingyang
 * @Date: 2018/6/5 23:04
 * @Description:
 */
@Slf4j
@Component
public class LuckyDrawHandle implements Runnable{

    @Autowired
    private GlobalCountService globalCountService;
    @Autowired
    private GlobalProperty globalProperty;
    @Autowired
    private DrawRedisResult drawRedisResult;

    @Override
    public void run() {
        log.info("------抽奖处理工作线程------");
        LuckyDrawQueueUtil luckyDrawQueueUtil = Singleton.get(LuckyDrawQueueUtil.class);

        while (true){

            JSONObject param = luckyDrawQueueUtil.getLuckyDrawQueue().poll();
            if (param == null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            String userId = param.getString("userId");
            String queryId = param.getString("queryId");
            int total = globalCountService.everyDayUserDrawCountByUserId(userId);
            if (total < 0){
                log.info("用户"+userId+"已用完抽奖次数");
                drawRedisResult.setQueryResult(queryId,LogicError.ERROR_201,"已用完抽奖次数");
                continue;
            }

            int awardSink = globalCountService.awardSinkCount(false);
            List<Integer> drawAwards = genDrawAwards(awardSink,globalProperty.luckyDramProbability);
            int index = RandomUtil.randomInt(0,drawAwards.size());
            if (drawAwards.get(index) == 0){
                log.info("用户"+userId+"未抽中奖品");
                drawRedisResult.setQueryResult(queryId,LogicError.ERROR_203,"未抽中奖品");
                continue;
            }

            //发送已获得抽奖资格的用户
            luckyDrawQueueUtil.getLuckyDrawLssAwardsQueue().offer(param);
            log.info("用户"+userId+"已获取抽奖资格");
        }
    }

    /**
     * 动态生成抽奖奖品箱，可缓存抽奖箱提高性能
     * @param award
     * @param luckyDramProbability
     * @return
     */
    public static List<Integer> genDrawAwards(int award, int luckyDramProbability){
        String key = "DRAW_AWARDS";
        List<Integer> luckyDraw = MyCache.lfuCache.get(key);
        if (luckyDraw != null){
            return luckyDraw;
        }

        luckyDraw = new ArrayList<>();

        //添加干扰项
        int size = award * (luckyDramProbability - 1);
        for (int i = 0; i < size; i++) {
            luckyDraw.add(i,0);
        }

        //均匀分布抽奖产品
        for (int i = 0; i < award; i++) {
            int index = ((i + 1) * luckyDramProbability -1);
            luckyDraw.add(index,1);
        }

        //0:未中奖，1:中奖了（实际可对奖项进行细分）
        MyCache.lfuCache.put(key,luckyDraw);
        return luckyDraw;
    }



}
