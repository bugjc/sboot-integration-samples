package com.bugjc.rabbitmq.exchange.fanout;

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
public class FanoutSender {
	
    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public FanoutSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String msg) {
        log.info("Fanout模式使用示例");
        rabbitTemplate.convertAndSend(FanoutConfig.TEST_FANOUT_EXCHANGE, msg);
    }
    
}