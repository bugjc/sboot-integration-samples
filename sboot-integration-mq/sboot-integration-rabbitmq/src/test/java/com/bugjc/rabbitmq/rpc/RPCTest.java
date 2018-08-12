package com.bugjc.rabbitmq.rpc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.rabbitmq.Tester;
import com.bugjc.rabbitmq.exchange.topic.TopicSender;
import com.bugjc.rabbitmq.rpc.client.RpcClient;
import com.bugjc.rabbitmq.rpc.config.RabbitConfig;
import com.bugjc.rabbitmq.rpc.core.util.MessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

/**
 * 测试
 * @author : aoki
 */
@Slf4j
public class RPCTest extends Tester {
    
	@Autowired
	private RpcClient rpcClient;

	@Test
	public void sendWithFixedReplay() throws Exception {
		String serviceName = "memberService";
		String method = "findByMemberId";
		JSONObject params = new JSONObject(){{
			put("userId","10001");
		}};

		String message = MessageGenerator.genMessage(serviceName,method,params);
		Future<String> responseFuture = rpcClient.sendWithFixedReplay(RabbitConfig.QUEUE_ASYNC_RPC_WITH_FIXED_REPLY_TEST,message);

		Thread.sleep(1000);

		String result = responseFuture.get();
		JSONObject jsonObject = JSON.parseObject(result);
		log.info(jsonObject.toJSONString());
	}

	@Test
	public void sendAsync() throws Exception {
		String serviceName = "memberService";
		String method = "findByMemberId";
		JSONObject params = new JSONObject(){{
			put("userId","10001");
		}};

		String message = MessageGenerator.genMessage(serviceName,method,params);
		Future<String> responseFuture = rpcClient.sendAsync(RabbitConfig.QUEUE_ASYNC_RPC_TEST,message);
		Thread.sleep(1000);
		String result = responseFuture.get();
		if (result == null){
			log.info("timeout");
			return;
		}
		JSONObject jsonObject = JSON.parseObject(result);
		log.info(jsonObject.toJSONString());
	}

	@Test
	public void sendSync() throws Exception {
		String serviceName = "memberService";
		String method = "findByMemberId";
		JSONObject params = new JSONObject(){{
			put("userId","10001");
		}};

		String message = MessageGenerator.genMessage(serviceName,method,params);
		String result = rpcClient.sendSync(RabbitConfig.QUEUE_SYNC_RPC_TEST,message);
		if (result == null){
			log.info("timeout");
			return;
		}
		JSONObject jsonObject = JSON.parseObject(result);
		log.info(jsonObject.toJSONString());
	}
}