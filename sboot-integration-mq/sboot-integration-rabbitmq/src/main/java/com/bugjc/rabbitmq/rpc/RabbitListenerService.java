package com.bugjc.rabbitmq.rpc;

import com.bugjc.rabbitmq.rpc.config.RabbitConfig;
import com.bugjc.rabbitmq.rpc.core.util.RpcServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

@Slf4j
@Component
public class RabbitListenerService {

    @Resource
    AmqpTemplate amqpTemplate;
    @Resource
    RpcServiceUtil rpcServiceUtil;

    /**
     * 同步rpc
     * @param message
     * @return
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_SYNC_RPC_TEST)
    public String syncCall(String message) {
        return rpcServiceUtil.syncCall(message);
    }

    /**
     * 临时队列或直接回复
     * @param message
     * @param replyTo
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_ASYNC_RPC_TEST)
    public void asyncCall(String message, @Header(AmqpHeaders.REPLY_TO) String replyTo) {
        log.info("recevie message {} and reply to {}", message, replyTo);
        if(replyTo.startsWith("amq.rabbitmq.reply-to")) {
            log.debug("starting with version 3.4.0, the RabbitMQ server now supports Direct reply-to");
        }else {
            log.info("fall back to using a temporary reply queue");
        }
        ListenableFuture<String> asyncResult = rpcServiceUtil.asyncCall(message);
        asyncResult.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                amqpTemplate.convertAndSend(replyTo, result);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("响应消息失败，错误信息：",ex);
            };
        });
    }


    /**
     * 异步rpc
     * @param message
     //* @param replyTo
     * @param correlationId
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_ASYNC_RPC_WITH_FIXED_REPLY_TEST)
    public void asyncCall(
            String message,
            @Header(AmqpHeaders.REPLY_TO) String replyTo,
            @Header(AmqpHeaders.CORRELATION_ID) String correlationId) {

        ListenableFuture<String> asyncResult = rpcServiceUtil.asyncCall(message);
        asyncResult.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                amqpTemplate.convertAndSend(replyTo, (Object) result, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //https://stackoverflow.com/questions/42382307/messageproperties-setcorrelationidstring-is-not-working
                        message.getMessageProperties().setCorrelationId(correlationId);
                        message.getMessageProperties().setContentType("application/json");
                        log.info("响应消息："+message.toString());
                        return message;
                    }
                });
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("响应消息失败，错误信息：",ex);
            };
        });
    }
}
