package com.bugjc.rabbitmq.exchange.topic;


import com.bugjc.rabbitmq.Tester;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试
 * @author : aoki
 */
public class TopicModeTest extends Tester {
    
	@Autowired
	private TopicSender sender;

	@Test(timeout = 10000)
	public void send() throws Exception {

		String routingKey = "test.topic.info.log";
		sender.send(routingKey);

		Thread.sleep(2000);
//		while (true){
//			sender.send();
//		}
	}
}