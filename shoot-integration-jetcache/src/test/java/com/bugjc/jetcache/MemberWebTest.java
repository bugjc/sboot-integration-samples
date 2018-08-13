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
        for (int i = 0; i < 10; i++) {
            Member member = new Member();
            member.setCreateDate(new Date());
            member.setAge(10+i);
            member.setNickname("jack"+i);
            HttpRequest.post(serverAddress+"/member")
                    .contentType("application/json")
                    .body(JSON.toJSONString(member))
                    .execute()
                    .body();
            log.info("添加成功");
        }

    }

    @Test
    public void testGetMember(){
        for (int i = 0; i < 1; i++) {
            String result = HttpRequest.get(serverAddress+"/member/"+(i+1)).execute().body();
            log.info("查询结果"+result);
        }

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

    @Test
    public void testDelMember(){
        HttpRequest.delete(serverAddress+"/member/1")
                .contentType("application/json")
                .execute()
                .body();
        log.info("删除成功");
    }

}
