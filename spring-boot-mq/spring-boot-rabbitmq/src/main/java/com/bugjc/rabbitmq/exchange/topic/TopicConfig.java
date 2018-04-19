package com.bugjc.rabbitmq.exchange.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * @author : aoki
 */
@Configuration
public class TopicConfig {

    public static final String TEST_TOPIC_EXCHANGE = "test.topic";
    public static final String INFO_LOG_QUEUE = "topic.info.log";
    public static final String ERROR_LOG_QUEUE = "topic.error.log";

    @Bean
    public Queue topicInfoLogQueue() {
        return new Queue(INFO_LOG_QUEUE);
    }

    @Bean
    public Queue topicErrorLogQueue() {
        return new Queue(ERROR_LOG_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TEST_TOPIC_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessage(Queue topicInfoLogQueue, TopicExchange exchange) {
        return BindingBuilder.bind(topicInfoLogQueue).to(exchange).with("*.topic.a");
    }

    @Bean
    Binding bindingExchangeMessages(Queue topicErrorLogQueue, TopicExchange exchange) {
        //*表示一个词,#表示零个或多个词
        return BindingBuilder.bind(topicErrorLogQueue).to(exchange).with("#.topic.b");
    }
}
