package com.bugjc.rabbitmq.rpc.core.rabbitmq;

import cn.hutool.core.date.DateUnit;
import com.bugjc.rabbitmq.rpc.core.util.HAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Component
public class RabbitSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    /***延迟一分钟*/
    private final static int DELAY = 1;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            if (correlationData !=null && HAUtil.containsCacheKey(correlationData.getId())){
                HAUtil.delCache(correlationData.getId());
            }

        } else {
            if (correlationData != null && cause.contains("NOT_FOUND")){
                HAUtil.addTask(new RabbitSenderRetryTask(correlationData.getId(),this),DELAY);
                log.info("消息发送失败:" + cause+",等待重发");
            }

        }

    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
        log.info("messageId:"+message.getMessageProperties().getCorrelationId() + " 发送失败");
    }

    /**发送消息**/
    public void convertAndSend(String exchange,String routeKey,String msgId,Object msg){
        HAUtil.addCache(msgId,msg,DateUnit.MINUTE.getMillis()*3);
        rabbitTemplate.convertAndSend(exchange,routeKey,msg,new CorrelationData(msgId));
    }

}
