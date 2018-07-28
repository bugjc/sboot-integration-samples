package com.bugjc.rabbitmq.exchange.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * 监听的业务类，实现接口MessageListener
 * @author aoki
 */
@Slf4j
@RabbitListener(containerFactory = "rabbitListenerContainer", queues = AmqpConfig.QUEUE_NAME)
public class Receiver implements ChannelAwareMessageListener {
    @Autowired
    private RabbitTemplate template;

    @Override
    public void onMessage(Message message, Channel channel){
        String json =  new String(message.getBody());
        log.info(json);
    }


}
