package com.bugjc.rabbitmq.rpc.service;


import com.alibaba.fastjson.JSONObject;
import com.bugjc.rabbitmq.rpc.core.dto.Result;

/**
 * 会员服务
 */
public interface MemberService {

    /**
     * 获取会员信息
     * @param params
     * @return
     */
    Result findByMemberId(JSONObject params);

    /**
     * 获取会员余额
     * @param params
     * @return
     */
    Result findBalance(JSONObject params);

}
