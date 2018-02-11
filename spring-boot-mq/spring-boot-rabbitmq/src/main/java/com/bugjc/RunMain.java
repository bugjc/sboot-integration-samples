package com.bugjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 配置类
 * @author : aoki
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.jicun.springboot" })
public class RunMain {
    public static void main(String[] args) {
        SpringApplication.run(RunMain.class, args);
    }
}