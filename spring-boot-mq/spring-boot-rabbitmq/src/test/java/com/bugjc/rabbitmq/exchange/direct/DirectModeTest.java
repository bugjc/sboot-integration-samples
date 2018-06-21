package com.bugjc.rabbitmq.exchange.direct;


import com.bugjc.rabbitmq.Tester;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试
 * @author : aoki
 */
public class DirectModeTest extends Tester {
    
	@Autowired
	private DirectSender sender;

	@Test
	public void send() throws Exception {
	    String message = "你好！青木";
		sender.sendMessage(message);
		sender.sendMessage(message);
	}
}