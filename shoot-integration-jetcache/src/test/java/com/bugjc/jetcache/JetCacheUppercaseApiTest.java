package com.bugjc.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheGetResult;
import com.alicp.jetcache.CacheResultCode;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CreateCache;
import com.bugjc.jetcache.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 大写API
 * @author qingyang
 * @date 2018/8/12 19:41
 */
@Slf4j
public class JetCacheUppercaseApiTest extends Tester {

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

    @Test
    public void testUppercaseApi() throws InterruptedException {
        CacheGetResult<Member> r = cache.GET(KEY_1);
        if (r.isSuccess()) {
            Member member = r.getValue();
            log.info("cache:" + member.toString());
        } else if (r.getResultCode() == CacheResultCode.NOT_EXISTS) {
            log.info("cache miss:" + KEY_1);


        } else if (r.getResultCode() == CacheResultCode.EXPIRED) {
            log.info("cache expired:" + KEY_1);
        } else {
            log.info("cache get error:" + KEY_1);

        }
    }
}
