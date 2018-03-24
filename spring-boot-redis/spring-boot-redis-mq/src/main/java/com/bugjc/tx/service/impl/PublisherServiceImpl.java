package com.bugjc.tx.service.impl;

import com.bugjc.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息发送
 */
@Service
public class PublisherServiceImpl implements PublisherService {
    private static final Logger log = LoggerFactory.getLogger(PublisherServiceImpl.class);

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
