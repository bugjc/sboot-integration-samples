package com.bugjc.rabbitmq.rpc.core.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.bugjc.rabbitmq.rpc.core.util.HAUtil;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.Getter;
import lombok.Setter;

public class RabbitSenderRetryTask implements TimerTask {

    @Getter
    @Setter
    private String messageKey;
    @Getter
    @Setter
    private RabbitSender rabbitSender;

    RabbitSenderRetryTask(String messageKey, RabbitSender rabbitSender){
        this.messageKey = messageKey;
        this.rabbitSender = rabbitSender;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        Object message = HAUtil.getCacheByKey(getMessageKey());
        if (!HAUtil.containsCacheKey(getMessageKey())){
            return;
        }

        if (message == null){
            return;
        }

        JSONObject messageJsonObject = (JSONObject) message;
        String exchange = messageJsonObject.getString("exchange");
        String routeKey = messageJsonObject.getString("routeKey");
        message = messageJsonObject.getString("message");

        //发送消息
        rabbitSender.convertAndSend(exchange,routeKey,getMessageKey(),message);
    }

}
