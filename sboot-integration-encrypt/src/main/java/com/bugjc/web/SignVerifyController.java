package com.bugjc.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.bugjc.core.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignVerifyController {

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZ7/OKn2aAOvjfybqr+pbK1ohJwmKzbjTg2utLY/LK8KZe3omIM1GoHMdAmzyCwubju0EhtSEgcLvw72NiFvBKRubROJjwPLQr8qlIkvf/gs0Z+V9zE2l5dgxf3H3fOJmxGr/Yiq+ujDE3uTNkfIezK4h30php68rAxGuVfS8ofQIDAQAB";

    @GetMapping("/rsa-sign")
    public String rsaSign() {
        return "rsa-sign";
    }

    @PostMapping("rss-verify-sign")
    @ResponseBody
    public String rsaVerifySign(@RequestHeader("Signature") String signStr,@RequestBody String json){

        Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA,null,publicKey);
        boolean flag = false;

        try {
            //pc端
            flag = sign.verify(json.getBytes(), StringUtil.hexStringToByteArray(signStr));
        }catch (Exception ex){
            //app端
            try {
                flag = sign.verify(json.getBytes(),Base64.decode(signStr));
            }catch (Exception e){
                flag = false;
            }

        }

        if (flag){
            return "{\"msg\":\"服务端验签成功\"}";
        }else {
            return "{\"msg\":\"服务端验签失败\"}";
        }

    }

}
