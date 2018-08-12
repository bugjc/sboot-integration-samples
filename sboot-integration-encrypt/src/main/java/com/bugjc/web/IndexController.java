package com.bugjc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZ7/OKn2aAOvjfybqr+pbK1ohJwmKzbjTg2utLY/LK8KZe3omIM1GoHMdAmzyCwubju0EhtSEgcLvw72NiFvBKRubROJjwPLQr8qlIkvf/gs0Z+V9zE2l5dgxf3H3fOJmxGr/Yiq+ujDE3uTNkfIezK4h30php68rAxGuVfS8ofQIDAQAB";

    @GetMapping("/")
    public String rsaSign() {
        return "index";
    }

}
