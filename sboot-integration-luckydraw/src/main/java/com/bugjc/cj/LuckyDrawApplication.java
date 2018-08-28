package com.bugjc.cj;


import com.bugjc.cj.component.LssAwardHandleTask;
import com.bugjc.cj.component.LuckyDrawHandleTask;
import com.bugjc.cj.core.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * 应用入口
 * @author : aoki
 */
@Slf4j
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LuckyDrawApplication implements CommandLineRunner {

    @Resource
    private Executor executor;

    public static void main(String[] args) {
        SpringApplication.run(LuckyDrawApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("启动工作线程");
        for (int i = 0; i < 4; i++) {
            LuckyDrawHandleTask luckyDrawHandleTask = SpringContextHolder.getBean(LuckyDrawHandleTask.class);
            executor.execute(luckyDrawHandleTask);
        }
        log.info("抽奖工作线程启动成功！");

        for (int i = 0; i < 1; i++) {
            LssAwardHandleTask lssAwardHandleTask = SpringContextHolder.getBean(LssAwardHandleTask.class);
            executor.execute(lssAwardHandleTask);
        }
        log.info("中奖工作线程启动成功！");

    }
}

