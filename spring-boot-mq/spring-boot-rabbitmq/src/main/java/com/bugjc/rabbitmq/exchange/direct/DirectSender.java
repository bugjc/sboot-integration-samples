package com.bugjc.rabbitmq.exchange.direct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * 发送消息
 * @author : aoki
 */
@Slf4j
@Service
public class DirectSender {
	
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    public void send(String messgae) throws UnsupportedEncodingException {
    	log.info("Direct模式使用示例");
        amqpTemplate.convertAndSend(DirectConfig.QUEUE_NAME, messgae);
    }

    public void sendMessage(String messgae) throws UnsupportedEncodingException {
        //设置消息属性
        MessageProperties messageProperties = new MessageProperties();
        //默认就是消息持久的
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message(messgae.getBytes("UTF-8"),messageProperties);
        amqpTemplate.send(DirectConfig.QUEUE_NAME,message);
    }
    
}