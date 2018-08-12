package com.bugjc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口
 * @author : aoki
 */
@Slf4j
@SpringBootApplication
public class Swagger2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Swagger2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("服务启动完成!");
    }
}

