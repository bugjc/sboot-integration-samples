package com.bugjc.rabbitmq.exchange.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * 接收消息
 * @author : aoki
 */
@Slf4j
@Service
public class DirectReceiver {
    
    @RabbitListener(queues = DirectConfig.QUEUE_NAME)
    public void consumerC1(String message) {
        log.info("C1接收Direct模式消息：" + message);
    }

    @RabbitListener(queues = DirectConfig.QUEUE_NAME)
    public void consumerC2(String message) {
        log.info("C2接收Direct模式消息：" + message);
    }
}