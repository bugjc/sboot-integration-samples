package com.bugjc.rabbitmq.exchange.headers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送消息
 * @author : aoki
 */
@Slf4j
@Service
public class HeadersSender {
	
    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public HeadersSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send() {
    	log.info("Headers模式使用示例");
        rabbitTemplate.convertAndSend("headersExchange", "你好， 青木!");
    }
    
}