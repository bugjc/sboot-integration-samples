package com.bugjc.jib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qingyang
 * @date 2018/9/26 16:48
 */
@Slf4j
@SpringBootApplication
public class JibApplication {
    public static void main(String[] args) {
        SpringApplication.run(JibApplication.class, args);
    }

    @RestController
    class HomeController{

        @GetMapping("/")
        public String index(){
            log.info("执行时间：{}",System.currentTimeMillis());
            return "index";
        }
    }
}
