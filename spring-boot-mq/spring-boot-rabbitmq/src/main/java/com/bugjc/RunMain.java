package com.bugjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

/**
 * 配置类
 * @author : aoki
 */
@SpringBootApplication
public class RunMain {
    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }
}