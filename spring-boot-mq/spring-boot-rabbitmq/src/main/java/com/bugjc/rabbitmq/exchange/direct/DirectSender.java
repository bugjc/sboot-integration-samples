package com.bugjc.rabbitmq.exchange.direct;

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
public class DirectSender {
	
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    public void send(String messgae) {
    	log.info("Direct模式使用示例");
        amqpTemplate.convertAndSend(DirectConfig.QUEUE_NAME, messgae);
    }
    
}