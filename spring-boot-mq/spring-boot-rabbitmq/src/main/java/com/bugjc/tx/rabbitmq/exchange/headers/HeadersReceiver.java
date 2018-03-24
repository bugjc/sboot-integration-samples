package com.bugjc.tx.rabbitmq.exchange.headers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * 接收消息
 * @author : aoki
 */
//@Service
public class HeadersReceiver {

    //@RabbitListener(queues="{name:'jack'}")
    public void processA(String str) {
        System.out.println("Headers ReceiveA:"+str);
    }

}