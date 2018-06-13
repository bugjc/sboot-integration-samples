package com.bugjc.logic.service.impl;

import cn.hutool.core.lang.Singleton;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.logic.DrawRedisResult;
import com.bugjc.logic.service.LogicService;
import com.bugjc.logic.util.IdWorker;
import com.bugjc.logic.util.LuckyDrawQueueUtil;
import com.bugjc.logic.util.dto.Result;
import com.bugjc.logic.util.dto.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: qingyang
 * @Date: 2018/6/6 09:24
 * @Description:
 */
@Service
public class LogicServiceImpl implements LogicService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DrawRedisResult drawRedisResult;

    @Override
    public Result luckyDraw(String userId) {
        //构建任务参数
        String queryId = IdWorker.getNextId();
        JSONObject result = new JSONObject(){{
            put("userId",userId);
            put("queryId",queryId);
        }};

        //加入队列
        LuckyDrawQueueUtil luckyDrawQueueUtil = Singleton.get(LuckyDrawQueueUtil.class);
        luckyDrawQueueUtil.getLuckyDrawQueue().offer(result);

        //快速返回受理结果
        return ResultGenerator.genSuccessResult(result);
    }

    @Override
    public Result queryLuckDraw(String queryId) {
        String json = drawRedisResult.getQueryResult(queryId);
        if (json == null){
            return new Result().setCode(1);
        }
        return JSON.parseObject(json,Result.class);
    }
}