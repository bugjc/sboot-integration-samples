package com.bugjc.cj.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Auther: qingyang
 * @Date: 2018/6/6 18:00
 * @Description:
 */
public interface AwardService {

    /**
     * 获取奖品列表,可缓存起来提高性能
     * @return
     */
    List<JSONObject> findAwardList();

    /**
     * 真*抽奖
     * @return
     */
    JSONObject drawAward();
}
