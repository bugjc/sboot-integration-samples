package com.bugjc.cj.component;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.cj.core.constants.LogicError;
import com.bugjc.cj.service.GlobalCountService;
import com.bugjc.cj.config.GlobalProperty;
import com.bugjc.cj.core.util.LuckyDrawQueueUtil;
import com.bugjc.cj.core.util.MyCache;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 幸运抽奖处理
 * @author qingyang
 */
@Slf4j
@Scope("prototype")
@Component
public class LuckyDrawHandleTask implements Runnable{

    @Resource
    private GlobalCountService globalCountService;
    @Resource
    private GlobalProperty globalProperty;
    @Resource
    private DrawRedisResult drawRedisResult;

    public LuckyDrawHandleTask(){
        log.info("设置线程优先级");
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    }

    @Override
    public void run() {

        log.info("------抽奖工作线程:"+Thread.currentThread().getName());
        LuckyDrawQueueUtil luckyDrawQueueUtil = Singleton.get(LuckyDrawQueueUtil.class);

        while (true){

            JSONObject param = luckyDrawQueueUtil.getLuckyDrawQueue().poll();
            if (param == null){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            String userId = param.getString("userId");
            String queryId = param.getString("queryId");
            log.info("用户["+userId+"]抽奖中...");
            int total = globalCountService.everyDayUserDrawCountByUserId(userId);
            if (total < 0){
                log.info("用户"+userId+"已用完抽奖次数");
                drawRedisResult.setQueryResult(queryId, LogicError.ERROR_201,"已用完抽奖次数");
                continue;
            }

            int awardSink = globalCountService.awardSinkCount(false);
            List<Integer> drawAwards = genDrawAwards(awardSink,globalProperty.getLuckyDrawProbability());
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
