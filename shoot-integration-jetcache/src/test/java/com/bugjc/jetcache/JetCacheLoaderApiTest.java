package com.bugjc.jetcache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheGetResult;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.ResultData;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CreateCache;
import com.bugjc.jetcache.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * 自动load
 * @author qingyang
 * @date 2018/8/12 19:41
 */
@Slf4j
public class JetCacheLoaderApiTest extends Tester {

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

    @PostConstruct
    public void init(){
        //指定每分钟刷新一次，30分钟如果没有访问就停止刷新。
        RefreshPolicy policy = RefreshPolicy.newPolicy(1, TimeUnit.MINUTES)
                .stopRefreshAfterLastAccess(30, TimeUnit.MINUTES);
        //缓存未命中的情况下，会调用loader
        cache.config().setLoader(this::loadMemberFromDatabase);
        cache.config().setRefreshPolicy(policy);
    }

    //缓存未命中的情况下，会调用该方法
    //需要注意：
    //GET、GET_ALL这类大写API只纯粹访问缓存，不会调用loader。
    //如果使用多级缓存，loader应该安装在MultiLevelCache上，不要安装在底下的缓存上。
    private Member loadMemberFromDatabase(String key) {
        if (key.equals(KEY_1)){
            Member member = new Member();
            member.setCreateDate(new Date());
            member.setAge(12);
            member.setNickname("aoki");
            return member;
        }
        return null;
    }

    @Before
    public void testBefore(){
        Member member = new Member();
        member.setCreateDate(new Date());
        member.setAge(10);
        member.setNickname("jack");
        cache.put(KEY_1, member);
    }

    @Test
    public void testLoadMember() {
        Member member = cache.get(KEY_1);
        log.info("获取缓存结果："+member.toString());
    }
}
