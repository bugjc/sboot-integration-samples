package com.bugjc.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DES3VerifyController {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZ7/OKn2aAOvjfybqr+pbK1ohJwmKzbjTg2utLY/LK8KZe3omIM1GoHMdAmzyCwubju0EhtSEgcLvw72NiFvBKRubROJjwPLQr8qlIkvf/gs0Z+V9zE2l5dgxf3H3fOJmxGr/Yiq+ujDE3uTNkfIezK4h30php68rAxGuVfS8ofQIDAQAB";

    @GetMapping("/3des")
    public String rsaSign() {
        return "3des";
    }

    @PostMapping("3des-verify")
    @ResponseBody
    public String rsaVerifySign(@RequestHeader("Key") String key,@RequestBody String json){

        try {
            DESede des = SecureUtil.desede(Base64.decode(key));
            JSONObject jsonObject = JSON.parseObject(json);
            String decryptStr = des.decryptStr(jsonObject.getString("pwd"));
            return "{\"msg\":\"解密字符串："+decryptStr+"\"}";
        }catch (Exception ex){
            return "{\"msg\":\"解密失败\"}";
        }


    }

}
