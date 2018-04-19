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
@Configuration
public class FanoutConfig {

    public static final String TEST_FANOUT_EXCHANGE = "test.fanout";
    public static final String INFO_LOG_QUEUE = "fanout.info.log";
    public static final String ERROR_LOG_QUEUE = "fanout.error.log";

    @Bean
    public Queue fanoutInfoLogQueue() {
        return new Queue(INFO_LOG_QUEUE);
    }

    @Bean
    public Queue fanoutErrorLogQueue() {
        return new Queue(ERROR_LOG_QUEUE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(TEST_FANOUT_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessage(Queue fanoutInfoLogQueue) {
        return BindingBuilder.bind(fanoutInfoLogQueue).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeMessage1(Queue fanoutErrorLogQueue) {
        return BindingBuilder.bind(fanoutErrorLogQueue).to(fanoutExchange());
    }
}
