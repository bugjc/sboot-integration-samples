package com.bugjc.jetcache.dao;

import com.bugjc.jetcache.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qingyang
 * @date 2018/8/12 07:42
 */
@Slf4j
@Component
public class MemberDao {

    private static AtomicInteger sequence = new AtomicInteger(0);

    /**
     * 模拟数据库会员表
     */
    private static Map<Integer,Member> members = new HashMap<Integer, Member>(){{
       put(sequence.incrementAndGet(),new Member(sequence.get(),"aoki",24,new Date()));
    }};



    /**
     * 模拟插入数据
     * @param member
     * @return
     */
    public void insert(Member member){
        int memberId = sequence.getAndIncrement();
        member.setMemberId(memberId);
        log.info("插入一条记录："+member.toString());
        members.put(memberId,member);
    }

    /**
     * 模拟查询数据
     * @param memberId
     * @return
     */
    public Member selectByMemberId(int memberId){
        log.info("查询 MemberId 为【 "+memberId+" 】的记录");
        return members.get(memberId);
    }

    /**
     * 模拟更新数据
     * @param member
     */
    public void updateByMemberId(Member member){
        log.info("更新 MemberId 为【 "+member.getMemberId()+" 】的记录");
        members.put(member.getMemberId(),member);
    }

    /**
     * 模拟删除数据
     * @param memberId
     */
    public void deleteByMemberId(int memberId){
        log.info("删除 MemberId 为【 "+memberId+" 】的记录");
        members.remove(memberId);
    }

}
