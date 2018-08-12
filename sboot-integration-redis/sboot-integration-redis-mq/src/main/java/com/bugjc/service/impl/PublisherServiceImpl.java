package com.bugjc.service.impl;

import com.bugjc.service.PublisherService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息发送
 */
@Slf4j
@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendMsg(String channel, String msg) {
        log.info("发送消息到主题"+channel);
        log.info("消息内容"+msg);
        stringRedisTemplate.convertAndSend(channel,msg);
        log.info("发送消息成功！ ");
    }
}
