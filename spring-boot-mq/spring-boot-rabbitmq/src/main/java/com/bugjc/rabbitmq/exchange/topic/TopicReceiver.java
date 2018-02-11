package com.bugjc.rabbitmq.exchange.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;


/**
 * 接收消息
 * @author : aoki
 */
//@Service
public class TopicReceiver {
    /**
     * 监听器监听指定的Queue
     * @param str
     */
    @RabbitListener(queues="topic.message")
    public void process1(String str) {
        System.out.println("Topic Received:"+str);
    }

    /**
     * 监听器监听指定的Queue
     * @param str
     */
    @RabbitListener(queues="topic.messages")
    public void process2(String str) {
        System.out.println("Topic Received Wildcard :"+str);
    }


}