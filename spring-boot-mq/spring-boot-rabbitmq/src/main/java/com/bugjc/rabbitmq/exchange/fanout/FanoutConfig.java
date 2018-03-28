package com.bugjc.rabbitmq.exchange.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * @author : aoki
 */
//@Configuration
public class FanoutConfig {

    public static final String TOPIC_EXCHANGE = "4140.AccnData";
    public static final String ROUTING_KEY_1 = "*.AccnData";
    public static final String ROUTING_KEY_2 = "#.AccnData";

    @Bean(name="message3")
    public Queue queueMessage() {
        return new Queue("4200.AccnData");
    }

    @Bean(name="message4")
    public Queue queueMessage1() {
        return new Queue("4300.AccnData");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("message3") Queue queueMessage) {
        return BindingBuilder.bind(queueMessage).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeMessage1(@Qualifier("message4") Queue queueMessage) {
        return BindingBuilder.bind(queueMessage).to(fanoutExchange());
    }
}
