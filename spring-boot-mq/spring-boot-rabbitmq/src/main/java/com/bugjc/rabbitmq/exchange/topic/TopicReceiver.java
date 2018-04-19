package com.bugjc.rabbitmq.exchange.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * 接收消息
 * @author : aoki
 */
@Slf4j
@Service
public class TopicReceiver {

    /**
     * 监听器监听指定的Queue
     * @param str
     */
    @RabbitListener(queues=TopicConfig.INFO_LOG_QUEUE)
    public void process1(String str) {
        log.info("接收Topic模式INFO.LOG队列消息:"+str);
    }

    /**
     * 监听器监听指定的Queue
     * @param str
     */
    @RabbitListener(queues=TopicConfig.ERROR_LOG_QUEUE)
    public void process2(String str) {
        log.info("接收Topic模式ERROR.LOG队列消息:"+str);
    }


}