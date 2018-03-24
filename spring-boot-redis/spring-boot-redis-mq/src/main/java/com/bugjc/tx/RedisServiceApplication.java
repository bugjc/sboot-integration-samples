package com.bugjc.tx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 应用入口
 * @author : aoki
 */
@SpringBootApplication
/*@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})*/
public class RedisServiceApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(RedisServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RedisServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("服务启动完成!");
    }
}

