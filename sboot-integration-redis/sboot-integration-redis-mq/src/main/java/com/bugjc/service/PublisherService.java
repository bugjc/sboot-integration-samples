package com.bugjc.service;

/**
 * @author qingyang
 * @date 2018/9/28 16:14
 */
public interface PublisherService {

    /**
     * 发送消息
     * @param channel
     * @return
     */
    void sendMsg(String channel,String msg);
}
