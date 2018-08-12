package com.bugjc.jetcache.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.bugjc.jetcache.dao.MemberDao;
import com.bugjc.jetcache.model.Member;
import com.bugjc.jetcache.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author qingyang
 * @date 2018/8/12 07:41
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;

    @Override
    public void createMember(Member member) {
        member.setCreateDate(new Date());
        memberDao.insert(member);
    }

    @Cached(name="Member-", key="#memberId", expire = 3600,cacheType = CacheType.LOCAL)
    @Override
    public Member findByMemberId(String memberId) {
        return memberDao.selectByMemberId(Integer.parseInt(memberId));
    }

    @Override
    public void updByMemberId(Member member) {
        memberDao.updateByMemberId(member);
    }

    @Override
    public void delByMemberId(int memberId) {
        memberDao.deleteByMemberId(memberId);
    }
}
