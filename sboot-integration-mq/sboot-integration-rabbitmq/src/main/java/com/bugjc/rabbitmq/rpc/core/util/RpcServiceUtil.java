package com.bugjc.rabbitmq.rpc.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.rabbitmq.rpc.core.dto.Result;
import com.bugjc.rabbitmq.rpc.core.dto.ResultGenerator;
import com.bugjc.rabbitmq.rpc.core.exception.RPCGlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
public class RpcServiceUtil {

    /**
     * 同步方法调用
     * @param message
     * @return
     */
    public String syncCall(String message){
        try {
            return this.businessLogic(message);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步方法调用
     * @param message
     * @return
     */
    @Async
    public ListenableFuture<String> asyncCall(String message) {
        try {
            String result = this.businessLogic(message);
            return new AsyncResult<String>(result);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String businessLogic(String message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info(message);
        Result result = null;
        JSONObject messageJsonObject = JSON.parseObject(message);
        if (messageJsonObject == null){
            return ResultGenerator.genFailResult(RPCGlobalException.PARAM_EMPTY.getMessage()).toString();
        }

        //获取参数
        String serviceName = messageJsonObject.getString("service");
        if (serviceName == null){
            result = ResultGenerator.genFailResult("调用服务名称不能为空！");
            return result.toString();
        }
        String methodName = messageJsonObject.getString("method");
        if (methodName == null){
            result = ResultGenerator.genFailResult("调用方法名称不能为空！");
            return result.toString();
        }
        JSONObject params = messageJsonObject.getJSONObject("params");
        //获取spring bean对象
        Object object = SpringContextUtil.getBean(serviceName);

        Class classes = object.getClass();
        //反射获取方法
        Method cMethod = classes.getMethod(methodName, new Class[]{JSONObject.class});
        //反射执行方法
        result = (Result) cMethod.invoke(object, new Object[]{params});
        return result.toString();
    }
}
