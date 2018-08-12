package com.bugjc.jetcache;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.bugjc.jetcache.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

/**
 * @author qingyang
 * @date 2018/8/12 11:12
 */
@Slf4j
public class MemberWebTest{

    private static String serverAddress = "http://127.0.0.1:8090";

    @Test
    public void testAddMember(){
        Member member = new Member();
        member.setCreateDate(new Date());
        member.setAge(10);
        member.setNickname("jack");
        String result = HttpRequest.post(serverAddress+"/member")
                .contentType("application/json")
                .body(JSON.toJSONString(member))
                .execute()
                .body();
        log.info(result);
    }

    @Test
    public void testGetMember(){
        String result = HttpRequest.get(serverAddress+"/member/1").execute().body();
        log.info("查询结果"+result);
    }

    @Test
    public void testUpdMember(){
        Member member = new Member();
        member.setMemberId(1);
        member.setCreateDate(new Date());
        member.setAge(10);
        member.setNickname("niki");
        String result = HttpRequest.put(serverAddress+"/member/"+member.getMemberId())
                .contentType("application/json")
                .body(JSON.toJSONString(member))
                .execute()
                .body();
        log.info("更新结果"+result);
    }

}
