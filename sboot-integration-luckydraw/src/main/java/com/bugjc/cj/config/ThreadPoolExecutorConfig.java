package com.bugjc.cj.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * 线程池配置
 * @author aoki
 */
@Slf4j
@Configuration
@EnableAsync
public class ThreadPoolExecutorConfig {

    @Bean
    public Executor executor() {
        log.info("初始化线程池");
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("executor-pool-%d").build();
        //允许的空闲时间
        int keepAlive = 60;
        //线程池维护线程的最大数量
        int maxPoolSize = 200;
        //线程池维护线程的最少数量
        int corePoolSize = 10;
        //缓存队列
        int queueCapacity = 5000;
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAlive, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(queueCapacity), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

}
