package com.bugjc.rabbitmq.exchange.headers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * 接收消息
 * @author : aoki
 */
@Slf4j
@Service
public class HeadersReceiver {

    //@RabbitListener(queues="{age:31}")
    public void processA(String str) {
        log.info("接收Headers模式的消息："+str);
    }

}