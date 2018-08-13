package com.bugjc.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CreateCache;
import com.bugjc.jetcache.model.Member;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分布式锁
 * @author qingyang
 * @date 2018/8/12 19:41
 */
@Slf4j
public class JetCacheLockApiTest extends Tester {

    /**
     * @CreateCache 创建缓存实例
     * @CacheRefresh refresh + timeUnit = 60秒刷新，stopRefreshAfterLastAccess = 如果key长时间未被访问，则相关的刷新任务就会被自动移除。
     * @CachePenetrationProtec 表示在多线程环境中同步加载数据
     */
    @CreateCache
    @CacheRefresh(refresh = 60,stopRefreshAfterLastAccess = 120,timeUnit = TimeUnit.SECONDS)
    @CachePenetrationProtect
    private Cache<String, Member> cache;


    @Test
    public void testLock() throws InterruptedException {
        int count = 100;
        AtomicInteger sum = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Runnable runnable = () -> {
            cache.tryLockAndRun("LockKeyAndRunKey", 2, TimeUnit.MILLISECONDS,() -> {
                log.info("获取锁成功，计数加1。");
                sum.incrementAndGet();

            });
            countDownLatch.countDown();
        };

        for (int i = 0; i < count; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();

        log.info(sum.get() + "");
        Thread.sleep(10000);
    }
}
