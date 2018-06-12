package com.bugjc;


import com.alibaba.fastjson.JSONObject;
import com.bugjc.grocery.service.impl.AwardSinkComponent;
import com.bugjc.logic.LssAwardHandle;
import com.bugjc.logic.LuckyDrawHandle;
import com.bugjc.logic.service.LogicService;
import com.bugjc.logic.util.SpringContextHolder;
import com.bugjc.logic.util.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用入口
 * @author : aoki
 */
@Slf4j
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class ServiceApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(ServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("启动工作线程");
        LuckyDrawHandle luckyDrawHandle = SpringContextHolder.getBean(LuckyDrawHandle.class);
        Thread luckyDrawThread = new Thread(luckyDrawHandle);
        luckyDrawThread.start();
        log.info("抽奖处理线程启动完成");
        LssAwardHandle lssAwardHandle = SpringContextHolder.getBean(LssAwardHandle.class);
        Thread lssAwardsThread = new Thread(lssAwardHandle);
        lssAwardsThread.start();
        log.info("中奖处理线程启动完成");

        //测试程序
        List<String> list = new ArrayList<>();
        LogicService logicService = SpringContextHolder.getBean(LogicService.class);
        for (int i = 0; i < 200; i++) {
            Result result = logicService.luckyDraw(String.valueOf(i));
            if (result.getCode() == 200){
                JSONObject data = (JSONObject) result.getData();
                list.add(data.getString("queryId"));
            }
        }

        AwardSinkComponent awardSinkComponent = SpringContextHolder.getBean(AwardSinkComponent.class);
        System.out.println("剩余奖金数量："+awardSinkComponent.getAwardSink());

//        while (true){
//            for (int i = 0; i < list.size(); i++) {
//                Result result = logicService.queryLuckDraw(list.get(i));
//                if (result.getCode() == 200){
//                    list.remove(i);
//                }
//
//                if (result.getCode() == 1){
//                    continue;
//                }
//
//                log.info(result.toString());
//            }
//        }


    }
}

