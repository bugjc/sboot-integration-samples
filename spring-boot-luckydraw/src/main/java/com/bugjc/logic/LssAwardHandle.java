package com.bugjc.logic;

import cn.hutool.core.lang.Singleton;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.grocery.AwardSinkAtomic;
import com.bugjc.logic.service.AwardService;
import com.bugjc.util.LuckyDrawQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 中奖处理
 * @Auther: qingyang
 * @Date: 2018/6/5 23:07
 * @Description:
 */
@Slf4j
@Component
public class LssAwardHandle implements Runnable{

    @Autowired
    private AwardService awardService;
    @Autowired
    private DrawRedisResult drawRedisResult;

    @Override
    public void run() {
        log.info("------中奖处理工作线程------");
        LuckyDrawQueueUtil luckyDrawQueueUtil = Singleton.get(LuckyDrawQueueUtil.class);

        while (true){

            JSONObject param = luckyDrawQueueUtil.getLuckyDrawLssAwardsQueue().poll();
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

            JSONObject jsonObject = awardService.drawAward();
            if (jsonObject == null){
                log.info("奖品已被领完");
                drawRedisResult.setQueryResult(queryId,LogicError.ERROR_202,"奖品已被领完");
            }

            log.info("用户"+userId+",抽到"+jsonObject.getString("awardName"));

            AwardSinkAtomic.addCount();

            //实际还需将奖品发放给用户
            //通过对每个奖品唯一标示，只有成功发放的才算真的抽中。
            //最后设置抽奖结果
            drawRedisResult.setQueryResult(queryId,LogicError.ERROR_200,"抽到"+jsonObject.getString("awardName"));
        }
    }

}
