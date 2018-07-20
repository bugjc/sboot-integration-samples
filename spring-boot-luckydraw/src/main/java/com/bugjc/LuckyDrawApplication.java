package com.bugjc;


import com.bugjc.logic.LssAwardHandle;
import com.bugjc.logic.LuckyDrawHandle;
import com.bugjc.logic.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用入口
 * @author : aoki
 */
@Slf4j
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class LuckyDrawApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LuckyDrawApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("启动工作线程");
        LuckyDrawHandle luckyDrawHandleA = SpringContextHolder.getBean(LuckyDrawHandle.class);
        Thread luckyDrawThreadA = new Thread(luckyDrawHandleA,"LuckyDrawThread-A");
        luckyDrawThreadA.start();

        LuckyDrawHandle luckyDrawHandleB = SpringContextHolder.getBean(LuckyDrawHandle.class);
        Thread luckyDrawThreadB = new Thread(luckyDrawHandleB,"LuckyDrawThread-B");
        luckyDrawThreadB.start();

        LuckyDrawHandle luckyDrawHandleC = SpringContextHolder.getBean(LuckyDrawHandle.class);
        Thread luckyDrawThreadC = new Thread(luckyDrawHandleC,"LuckyDrawThread-C");
        luckyDrawThreadC.start();
        log.info("抽奖工作线程启动成功！");

        LssAwardHandle lssAwardHandle = SpringContextHolder.getBean(LssAwardHandle.class);
        Thread lssAwardsThreadA = new Thread(lssAwardHandle,"LssAwardThread-A");
        lssAwardsThreadA.start();
        log.info("中奖工作线程启动成功！");

    }
}

