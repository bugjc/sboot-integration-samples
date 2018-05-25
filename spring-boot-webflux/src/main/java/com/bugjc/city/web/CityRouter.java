package com.bugjc.city.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@Configuration
public class CityRouter {

    /**
     * RouterFunctions 对请求路由处理类，即将请求路由到处理器。
     * 这里将一个 GET 请求 /hello 路由到处理器 cityHandler 的 helloCity 方法上。
     * 跟 Spring MVC 模式下的 HandleMapping 的作用类似。
     * @param cityHandler
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> routeCity(CityHandler cityHandler) {

        RequestPredicate requestPredicate = RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN));
        HandlerFunction handlerFunction = cityHandler::helloCity;

        return RouterFunctions.route(requestPredicate,handlerFunction);
    }
}
