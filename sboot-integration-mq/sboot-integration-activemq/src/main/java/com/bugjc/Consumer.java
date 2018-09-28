package com.bugjc;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qingyang
 */
@Component
public class Consumer {
    /**
     * 使用@JmsListener注解来监听指定的某个队列内的消息
     **/
    @JmsListener(destination = "XXX.QUEUE")
    public void removeMessage(String msg) {
        System.out.println("["+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"]Receive:"+msg);
    }
}
