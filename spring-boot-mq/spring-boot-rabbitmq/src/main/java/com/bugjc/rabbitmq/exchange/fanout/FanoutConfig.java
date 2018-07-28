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
    public static final String INFO_log_QUEUE = "fanout.info.log";
    public static final String ERROR_log_QUEUE = "fanout.error.log";

    @Bean
    public Queue fanoutInfologQueue() {
        return new Queue(INFO_log_QUEUE);
    }

    @Bean
    public Queue fanoutErrorlogQueue() {
        return new Queue(ERROR_log_QUEUE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(TEST_FANOUT_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessage(Queue fanoutInfologQueue) {
        return BindingBuilder.bind(fanoutInfologQueue).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeMessage1(Queue fanoutErrorlogQueue) {
        return BindingBuilder.bind(fanoutErrorlogQueue).to(fanoutExchange());
    }
}
