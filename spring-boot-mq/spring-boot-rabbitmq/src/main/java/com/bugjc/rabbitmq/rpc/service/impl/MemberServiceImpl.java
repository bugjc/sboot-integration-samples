package com.bugjc.rabbitmq.rpc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bugjc.rabbitmq.rpc.core.dto.Result;
import com.bugjc.rabbitmq.rpc.core.dto.ResultGenerator;
import com.bugjc.rabbitmq.rpc.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service("memberService")
public class MemberServiceImpl implements MemberService {


    @Override
    public Result findByMemberId(JSONObject params) {

        log.info("获得参数："+params.toJSONString());
        try {
            //模拟耗时任务
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResultGenerator.genSuccessResult(new JSONObject(){{
            put("memberId","10001");
            put("nickName","青木");
        }});
    }

    @Override
    public Result findBalance(JSONObject params) {

        log.info("获得参数："+params.toJSONString());

        try {
            //模拟耗时任务
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult(new BigDecimal(10.5));
    }

}
