package com.bugjc.jwt;


import org.slf4j.logger;
import org.slf4j.loggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口
 * @author : aoki
 */
@SpringBootApplication
public class JwtServerApplication implements CommandLineRunner {

    private logger logger = loggerFactory.getlogger(JwtServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JwtServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("服务启动完成!");
    }

}

