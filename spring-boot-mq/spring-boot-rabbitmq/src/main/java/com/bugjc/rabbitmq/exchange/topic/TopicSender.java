package com.bugjc.rabbitmq.exchange.topic;

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
public class TopicSender {
	
    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    public void send() {
    	log.info("Topic转发模式使用示例");
        rabbitTemplate.convertAndSend(TopicConfig.TEST_TOPIC_EXCHANGE,"test.topic.a", "你好， 青木!");
    }
    
}