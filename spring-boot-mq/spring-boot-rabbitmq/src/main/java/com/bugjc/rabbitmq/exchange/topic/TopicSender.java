package com.bugjc.rabbitmq.exchange.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发送消息
 * @author : aoki
 */
//@Service
public class TopicSender {
	
    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    public void send() {
    	System.out.println("Topic转发模式使用示例");
        rabbitTemplate.convertAndSend("exchange","topic.message", "你好， 青木!");
    }
    
}