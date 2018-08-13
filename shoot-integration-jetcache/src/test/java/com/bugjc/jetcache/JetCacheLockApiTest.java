package com.bugjc.jetcache;

import com.alicp.jetcache.AutoReleaseLock;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CreateCache;
import com.bugjc.jetcache.model.Member;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 自动load
 * @author qingyang
 * @date 2018/8/12 19:41
 */
@Slf4j
public class JetCacheLockApiTest extends Tester {

    /**
     * Common Thread Pool
     */
    private static ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());



    private static final String KEY_1 = "member:1";

    /**
     * @CreateCache 创建缓存实例
     * @CacheRefresh refresh + timeUnit = 60秒刷新，stopRefreshAfterLastAccess = 如果key长时间未被访问，则相关的刷新任务就会被自动移除。
     * @CachePenetrationProtec 表示在多线程环境中同步加载数据
     */
    @CreateCache
    @CacheRefresh(refresh = 60,stopRefreshAfterLastAccess = 120,timeUnit = TimeUnit.SECONDS)
    @CachePenetrationProtect
    private Cache<String, Member> cache;


    @Before
    public void testBefore(){
        Member member = new Member();
        member.setCreateDate(new Date());
        member.setAge(10);
        member.setNickname("jack");
        cache.put(KEY_1, member);
    }

    @Test
    public void testLoadMember() throws InterruptedException {

        try (AutoReleaseLock lock = cache.tryLock("LockKey1", 200, TimeUnit.HOURS)) {
            Assert.assertNotNull(lock);
            Assert.assertNull(cache.tryLock("LockKey1", 200, TimeUnit.HOURS));
            Assert.assertNotNull(cache.tryLock("LockKey2", 200, TimeUnit.MILLISECONDS));
        }
        Assert.assertNotNull(cache.tryLock("LockKey1", 50, TimeUnit.MILLISECONDS));
        Assert.assertNull(cache.tryLock("LockKey1", 50, TimeUnit.MILLISECONDS));
        Thread.sleep(51);
        Assert.assertNotNull(cache.tryLock("LockKey1", 50, TimeUnit.MILLISECONDS));

        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        int[] runCount = new int[2];
        Runnable runnable = () -> {
            boolean b = cache.tryLockAndRun("LockKeyAndRunKey", 10, TimeUnit.SECONDS,
                    () -> {
                        runCount[1]++;
                        while (countDownLatch.getCount() > 1) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                            }
                        }
                    });
            if (b)
                runCount[0]++;
            countDownLatch.countDown();
        };
        for (int i = 0; i < count; i++) {
            new Thread(runnable).start();
        }
        countDownLatch.await();
        Assert.assertEquals(1, runCount[0]);
        Assert.assertEquals(1, runCount[1]);

        try {
            cache.tryLockAndRun("LockKeyAndRunKey", 10, TimeUnit.SECONDS, () -> {
                throw new RuntimeException();
            });
            Assert.fail();
        } catch (Exception e) {
            try (AutoReleaseLock lock = cache.tryLock("LockKeyAndRunKey", 1, TimeUnit.SECONDS)) {
                Assert.assertNotNull(lock);
            }
            ;
        }



//        int count = 10;
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
//        for (int i = 0; i < count; i++) {
//            pool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        //等待所有任务
//                        cyclicBarrier.await();
//                        boolean flag = cache.tryLockAndRun(KEY_1, 60, TimeUnit.SECONDS, () -> {
//                            log.info("编号：【 "+count+" 】获取到锁");
//                        });
//
//                        if (flag){
//                            log.info("编号：【 "+count+" 】获取到锁");
//                        }else {
//                            log.info("编号：【 "+count+" 】未获取到锁");
//                        }
//
//                    } catch (InterruptedException | BrokenBarrierException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//
//        pool.shutdown();
//
//
//        while (!pool.isTerminated()){
//            Thread.sleep(10);
//            System.out.println("任务暂停10毫秒");
//        }
        Thread.sleep(10000);
    }
}
