package com.bugjc.jwt;

import com.bugjc.jwt.controller.UserController;
import com.bugjc.jwt.core.dto.Result;
import org.junit.Test;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;

import javax.annotation.Resource;

public class JwtServerTest extends Tester{

    @Resource
    UserController userController;

    @Test
    public void login() throws Exception {
        Result result = userController.login("aoki", "123456", new Device() {
            @Override
            public boolean isNormal() {
                return false;
            }

            @Override
            public boolean isMobile() {
                return true;
            }

            @Override
            public boolean isTablet() {
                return false;
            }

            @Override
            public DevicePlatform getDevicePlatform() {
                return null;
            }
        });
        System.out.println(result.getData());
    }
}
