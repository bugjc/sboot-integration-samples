package com.bugjc.rabbitmq.rpc.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

@Slf4j
@Component
public class RpcClient {

	@Autowired
	AsyncRabbitTemplate asyncRabbitTemplate;

	@Autowired
	AmqpTemplate amqpTemplate;

	public String sendSync(String routingKey,String message) {
		return(String) amqpTemplate.convertSendAndReceive(routingKey, message);
	}

	@Async
	public Future<String> sendAsync(String routingKey,String message) {
		String result = (String) amqpTemplate.convertSendAndReceive(routingKey, message);
		return new AsyncResult<String>(result);
	}

	public Future<String> sendWithFixedReplay(String routingKey,String message) {
		return asyncRabbitTemplate.convertSendAndReceive(routingKey, message);
	}

}
