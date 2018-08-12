package com.bugjc.rabbitmq.rpc.core.util;

/**
 * 构建rpc调用格式
 */
public class MessageGenerator {

    /**
     * 生成单个参数的消息体
     * @param methodName
     * @param object
     * @return
     */
    public static String genMessage(String serviceName,String methodName, Object object) {
        return new Message()
                .putService(serviceName)
                .putMethod(methodName)
                .putParams(object)
                .build();
    }


}
