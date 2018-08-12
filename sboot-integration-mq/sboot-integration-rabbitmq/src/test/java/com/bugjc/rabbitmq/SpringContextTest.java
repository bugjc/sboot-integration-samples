package com.bugjc.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.bugjc.rabbitmq.rpc.core.util.SpringContextUtil;
import com.bugjc.rabbitmq.rpc.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * rsa
 * @author aoki
 */
@Slf4j
public class SpringContextTest extends Tester{

    @Test
    public void setContextTest() {
        MemberService memberService = SpringContextUtil.getBean("memberService",MemberService.class);
        memberService.findByMemberId(new JSONObject(){{
            put("key","value");
        }});
    }
}
