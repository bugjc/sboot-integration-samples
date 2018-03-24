package com.bugjc.tx.rabbitmq.exchange.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * 接收消息
 * @author : aoki
 */
//@Service
public class FanoutReceiver {
    /**
     * 监听器监听指定的Queue
     * @param str
     */
    @RabbitListener(queues="4200.Acc.TxnData")
    public void process1(String str) {
        System.out.println("Topic Received:"+str);
    }

    /**
     * 监听器监听指定的Queue
     * @param str
     */
    @RabbitListener(queues="4300.Acc.TxnData")
    public void process2(String str) {
        System.out.println("Topic Received Wildcard :"+str);
    }


}