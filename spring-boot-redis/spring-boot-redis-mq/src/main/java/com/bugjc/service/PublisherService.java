package com.bugjc.service;

public interface PublisherService {

    /**
     * 发送消息
     * @param channel
     * @return
     */
    void sendMsg(String channel,String msg);
}
