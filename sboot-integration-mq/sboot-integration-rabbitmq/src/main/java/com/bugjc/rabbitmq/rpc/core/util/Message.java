package com.bugjc.rabbitmq.rpc.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;


public class Message {
    @Getter
    @Setter
    private JSONObject root = new JSONObject();
    @Getter
    @Setter
    private JSONObject params = new JSONObject();

    public Message putService(String serviceName){
        root.put("service",serviceName);
        root.put("params",params);
        return this;
    }

    public Message putMethod(String methodName){
        root.put("method",methodName);
        root.put("params",params);
        return this;
    }

    public Message putParam(String key, Object value){
        params.put(key,value);
        return this;
    }

    public Message putParams(Object object){
        root.put("params",object);
        return this;
    }

    public String build(){
        return JSON.toJSONString(root);
    }
}
