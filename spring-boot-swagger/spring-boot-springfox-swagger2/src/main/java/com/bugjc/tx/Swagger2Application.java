package com.bugjc.tx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口
 * @author : aoki
 */
@SpringBootApplication
public class Swagger2Application implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Swagger2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Swagger2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("服务启动完成!");
    }
}

