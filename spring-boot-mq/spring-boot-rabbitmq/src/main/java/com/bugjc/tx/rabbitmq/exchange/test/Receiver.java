package com.bugjc.tx.rabbitmq.exchange.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


/**
 * 监听的业务类，实现接口MessageListener
 */
@RabbitListener(containerFactory = "rabbitListenerContainer", queues = AmqpConfig.QUEUE_NAME)
public class Receiver implements ChannelAwareMessageListener {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate template;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        try {

            logger.info("##### = {}", new String(message.getBody()));

            ObjectMapper objectMapper = new ObjectMapper();
            String json =  new String(message.getBody());
            //模拟耗时操作
            //TimeUnit.SECONDS.sleep(10);
            boolean result = json == null ? false:true;
            if (!result) {
                logger.error("消息消费失败");
            } else {
                logger.info("消息消费成功");
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//手动消息应答
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);//true 重新放入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);//对于处理不了的异常消息
            ObjectMapper objectMapper = new ObjectMapper();
            String msg = "";
            //发送到失败队列
            template.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY_FAIL, msg);
        }
    }


}
