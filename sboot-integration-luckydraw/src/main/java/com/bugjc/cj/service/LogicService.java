package com.bugjc.cj.service;

import com.bugjc.cj.core.dto.Result;

/**
 * @Auther: qingyang
 * @Date: 2018/6/5 22:58
 * @Description:
 */
public interface LogicService {


    /**
     * 幸运抽奖
     * @param userId
     * @return
     */
    Result luckyDraw(String userId);


    /**
     * 查询抽奖结果
     * @param queryId
     * @return
     */
    Result queryLuckDraw(String queryId);
}
