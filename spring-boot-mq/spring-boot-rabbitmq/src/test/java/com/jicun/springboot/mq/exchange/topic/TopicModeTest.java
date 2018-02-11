package com.jicun.springboot.mq.exchange.topic;



import com.bugjc.RunMain;
import com.bugjc.rabbitmq.exchange.topic.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试
 * @author : aoki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RunMain.class)
public class TopicModeTest {
    
	@Autowired
	private TopicSender sender;

	@Test(timeout = 10000)
	public void send() throws Exception {
		while (true){
			sender.send();
		}

	}
}