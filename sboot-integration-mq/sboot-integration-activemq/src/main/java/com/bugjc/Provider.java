package com.bugjc;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MQ消息提供者
 *
 * @author leftso
 *
 */
@Component
@EnableScheduling // 测试用启用任务调度
public class Provider {
    /** MQ jms实例 **/
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    private static int count = 0;

    // 每5s执行1次-测试使用
    //@Scheduled(fixedDelay = 5000)
    public void send() {

        Destination destination = new ActiveMQQueue("GPS.QUEUE");// 这里定义了Queue的key
        String message = "Send AMQ Test ..." + count;
        System.out.println("[" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "]" + message);
        count++;
        this.jmsMessagingTemplate.convertAndSend(destination, message);
    }
}