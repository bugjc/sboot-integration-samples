package com.bugjc.rabbitmq.exchange.headers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送消息
 * @author : aoki
 */
//@Service
public class HeadersSender {
	
    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    public void send() {
    	System.out.println("Headers模式使用示例");
        rabbitTemplate.convertAndSend("headersExchange", "你好， 青木!");
    }
    
}