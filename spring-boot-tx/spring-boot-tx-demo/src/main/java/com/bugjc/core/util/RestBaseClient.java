package com.bugjc.core.util;
import org.apache.log4j.Logger;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author 作者 yangqing
 * @version 创建时间：2016年11月28日
 */
public class RestBaseClient {

    public Logger logger = Logger.getLogger(getClass());

    /**
     * get
     * @param url
     */
    public static String get(String url) {
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target.request().get();
        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            return response.readEntity(String.class);
        } finally {
            response.close();
            client.close();
        }
    }

    /**
     * post
     * @param type --类型
     * @param t --实体参数
     * @param url -- 全球资源定位器
     */
    @SuppressWarnings("unchecked")
    public static <T> T post(T t,String type,String url){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target.request().buildPost(Entity.entity(t, type)).invoke();
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
        }
        return (T) response.readEntity(t.getClass());
    }


    /**
     * post
     * @param type --类型
     * @param t --实体参数
     * @param url -- 全球资源定位器
     */
    public static <T> T post(T t, String type, String url, String token){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);

        System.out.println("bearer "+token);
        Response response = target
                .request()
                .header("Authorization","bearer "+token)
                .buildPost(Entity.entity(t, type)).invoke();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
        }
        return (T) response.readEntity(t.getClass());
    }


}
