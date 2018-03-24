package com.bugjc.tx.rabbitmq.exchange.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发送消息
 * @author : aoki
 */
//@Service
public class DirectSender {
	
    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    public void send() {
    	System.out.println("Direct模式使用示例");
        rabbitTemplate.convertAndSend(DirectConfig.QUEUE_NAME, "你好， 青木!");
    }
    
}