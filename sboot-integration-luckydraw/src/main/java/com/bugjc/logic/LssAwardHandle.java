package com.bugjc.logic;

import cn.hutool.core.lang.Singleton;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.logic.service.AwardService;
import com.bugjc.logic.util.LuckyDrawQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 中奖处理
 * @Auther: qingyang
 * @Date: 2018/6/5 23:07
 * @Description:
 */
@Slf4j
@Component
public class LssAwardHandle implements Runnable{

    @Resource
    private AwardService awardService;
    @Resource
    private DrawRedisResult drawRedisResult;

    @Override
    public void run() {
        log.info("------中奖工作线程:"+Thread.currentThread().getName()+"------"+this);
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
                continue;
            }

            log.info("用户"+userId+",抽到"+jsonObject.getString("awardName"));


            //实际还需将奖品发放给用户
            //通过对每个奖品唯一标示，只有成功发放的才算真的抽中。
            //最后设置抽奖结果
            drawRedisResult.setQueryResult(queryId,LogicError.ERROR_200,"抽到"+jsonObject.getString("awardName"));
        }
    }

}
