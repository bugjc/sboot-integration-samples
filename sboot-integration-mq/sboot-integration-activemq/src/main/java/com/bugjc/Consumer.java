package com.bugjc;

import org.slf4j.logger;
import org.slf4j.loggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Consumer {

    logger logGER = loggerFactory.getlogger(this.getClass());

    /**
     * 使用@JmsListener注解来监听指定的某个队列内的消息
     **/
    @JmsListener(destination = "GPS.QUEUE")
    public void removeMessage(String msg) {
        System.out.println("["+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"]Receive:"+msg);
    }
}
