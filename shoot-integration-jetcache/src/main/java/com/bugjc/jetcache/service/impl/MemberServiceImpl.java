package com.bugjc.jetcache.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.*;
import com.bugjc.jetcache.dao.MemberDao;
import com.bugjc.jetcache.model.Member;
import com.bugjc.jetcache.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author qingyang
 * @date 2018/8/12 07:41
 */
@Service
public class MemberServiceImpl implements MemberService {


    /**
     * @CreateCache 创建缓存实例
     * @CacheRefresh refresh + timeUnit = 60秒刷新，stopRefreshAfterLastAccess = 如果key长时间未被访问，则相关的刷新任务就会被自动移除。
     * @CachePenetrationProtec 表示在多线程环境中同步加载数据
     */
    @CreateCache
    @CacheRefresh(refresh = 60,stopRefreshAfterLastAccess = 120,timeUnit = TimeUnit.SECONDS)
    @CachePenetrationProtect
    private Cache<String,Member> cache;

    @Resource
    private MemberDao memberDao;

    @Override
    public void createMember(Member member) {
        member.setCreateDate(new Date());
        memberDao.insert(member);
    }

    @Override
    public Member findByMemberId(Integer memberId) {
        return memberDao.selectByMemberId(memberId);
    }

    @Override
    public void updByMemberId(Member member) {
        memberDao.updateByMemberId(member);
    }

    @Override
    public void delByMemberId(Integer memberId) {
        memberDao.deleteByMemberId(memberId);
    }
}
