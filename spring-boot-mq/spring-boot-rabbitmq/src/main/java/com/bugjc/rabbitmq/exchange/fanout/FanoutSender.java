package com.bugjc.rabbitmq.exchange.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送消息
 * @author : aoki
 */
//@Service
public class FanoutSender {
	
    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    public void send(String msg) {
        System.out.println("发送消息："+msg);
        rabbitTemplate.convertAndSend(FanoutConfig.TOPIC_EXCHANGE, msg);
    }
    
}