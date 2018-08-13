package com.bugjc.jetcache.web;

import com.bugjc.jetcache.model.Member;
import com.bugjc.jetcache.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author qingyang
 * @date 2018/8/12 11:19
 */
@Slf4j
@RestController
@RequestMapping("/member")
public class MemberWeb {

    @Resource
    private MemberService memberService;

    @PostMapping
    public void addMember(@RequestBody Member member){
        memberService.createMember(member);
        log.info("添加会员成功，会员记录："+member.toString());
    }

    @GetMapping("{memberId}")
    public Member queryByMemberId(@PathVariable Integer memberId){
        Member member = memberService.findByMemberId(memberId);
        if (member == null){
            return new Member();
        }
        log.info("查询编号为【 "+memberId+" 】的会员记录成功，查询结果："+member.toString());
        return member;
    }

    @PutMapping("{memberId}")
    public Member updMember(@PathVariable Integer memberId,@RequestBody Member member){
        memberService.updByMemberId(member);
        log.info("修改编号为【 "+memberId+" 】的会员记录成功，更新结果："+member.toString());
        return member;
    }

    @DeleteMapping("{memberId}")
    public void delMember(@PathVariable Integer memberId){
        memberService.delByMemberId(memberId);
        log.info("删除编号为【 "+memberId+" 】的会员记录成功");
    }
}
