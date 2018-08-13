package com.bugjc.jetcache.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.bugjc.jetcache.model.Member;

/**
 * @author qingyang
 * @date 2018/8/12 07:33
 */
public interface MemberService {


    /**
     * 创建会员
     * @param member
     */
    void createMember(Member member);

    /**
     * 获取会员信息
     * @param memberId
     * @return
     */
    @Cached(name = "member:", key = "#memberId", expire = 300, cacheType = CacheType.BOTH)
    Member findByMemberId(Integer memberId);

    /**
     * 更新会员信息
     * @param member
     */
    @CacheUpdate(name="member:", key="#member.memberId", value="#member")
    void updByMemberId(Member member);

    /**
     * 删除会员信息
     * @param memberId
     */
    @CacheInvalidate(name="member:", key="#memberId")
    void delByMemberId(Integer memberId);


}
