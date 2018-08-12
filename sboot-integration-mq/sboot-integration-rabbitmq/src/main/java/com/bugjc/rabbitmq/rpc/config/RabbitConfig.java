package com.bugjc.rabbitmq.rpc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@Configuration
@EnableAsync
public class RabbitConfig {

	/**同步RPC队列**/
	public static final String QUEUE_SYNC_RPC_TEST = "rpc.sync.test";

	/**
	 * 异步RPC队列，使用临时回复队列，或者使用“Direct reply-to”特性
	 */
	public static final String QUEUE_ASYNC_RPC_TEST = "rpc.async.test";

	/**异步RPC队列，每个客户端使用不同的固定回复队列，需要额外提供correlationId以关联请求和响应**/
	public static final String QUEUE_ASYNC_RPC_WITH_FIXED_REPLY_TEST= "rpc.with.fixed.reply.test";

	/**交换器**/
	public static final String EXCHANGE_TEST = "member.topic.test";

	/**队列**/
	public static final String QUEUE_TEST = "member.queue.test.100";

	/**route key**/
	public static final String ROUTING_KEY_TEST = "member.queue.test.*";


	@Bean
	public Queue syncRPCQueue() {
		return new Queue(QUEUE_SYNC_RPC_TEST);
	}

	@Bean
	public Queue asyncRPCQueue() {
		return new Queue(QUEUE_ASYNC_RPC_TEST);
	}

	@Bean
	public Queue fixedReplyRPCCardQueue() {
		return new Queue(QUEUE_ASYNC_RPC_WITH_FIXED_REPLY_TEST);
	}

	@Bean
	public Queue repliesQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Queue testQueue() {
		return new Queue(QUEUE_TEST);
	}

	@Bean
	public TopicExchange testExchange() {
		return new TopicExchange(EXCHANGE_TEST);
	}

	@Bean
	Binding bindingExchangeMessage(Queue testQueue, TopicExchange testExchange) {
		return BindingBuilder.bind(testQueue).to(testExchange).with(ROUTING_KEY_TEST);
	}

	@Bean
	@Primary
	public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueueNames(repliesQueue().getName());
		return container;
	}

	@Bean
	public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template, SimpleMessageListenerContainer container) {
		return new AsyncRabbitTemplate(template, container);
	}

//	@Bean
//	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMandatory(true);
//		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//		//rabbitTemplate.setReplyAddress(repliesQueue());
//		return rabbitTemplate;
//	}


}
