package com.bugjc.tx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 应用入口
 * @author : aoki
 */
@ImportResource(locations = {"classpath:tx-group-aspect.xml","classpath:tx-object-aspect.xml"})
@SpringBootApplication
public class DSApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(DSApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DSApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("服务启动完成!");
    }
}

