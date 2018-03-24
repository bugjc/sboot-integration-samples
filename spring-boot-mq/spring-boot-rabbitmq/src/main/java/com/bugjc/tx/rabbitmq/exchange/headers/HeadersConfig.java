package com.bugjc.tx.rabbitmq.exchange.headers;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.Map;

/**
 * 配置类
 * @author : aoki
 */
//@Configuration
public class HeadersConfig {

    @Bean(name="message-a")
    public Queue messageA() {
        return new Queue("headers.a");
    }

    @Bean
    HeadersExchange headersExchange() {
        // 是否持久化
        boolean durable = true;
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = false;
        //设置消息头键值对信息
        Map<String, Object> headers = new Hashtable<String,Object>();
        //这里x-match有两种类型
        //all:表示所有的键值对都匹配才能接受到消息
        //any:表示只要有键值对匹配就能接受到消息
        headers.put("x-match", "any");
        headers.put("name", "jack");
        headers.put("age" , 31);

        return new HeadersExchange("headersExchange",durable,autoDelete,headers);
    }

    @Bean
    BindingBuilder.HeadersExchangeMapConfigurer bindingExchangeA(@Qualifier("message-a") Queue messageA,HeadersExchange headersExchange) {
        return BindingBuilder.bind(messageA).to(headersExchange);
    }
}
