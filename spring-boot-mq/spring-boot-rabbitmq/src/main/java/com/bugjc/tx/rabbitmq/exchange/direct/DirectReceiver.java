package com.bugjc.tx.rabbitmq.exchange.direct;

import org.springframework.amqp.rabbit.annotation.RabbitListener;


/**
 * 接收消息
 * @author : aoki
 */
//@Service
public class DirectReceiver {
    
    @RabbitListener(queues = DirectConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Direct Received <" + message + ">");
    }
}