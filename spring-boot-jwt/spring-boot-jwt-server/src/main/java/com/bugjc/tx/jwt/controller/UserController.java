package com.bugjc.tx.jwt.controller;

import com.bugjc.jwt.core.dto.Result;
import com.bugjc.jwt.core.dto.ResultCode;
import com.bugjc.jwt.core.dto.ResultGenerator;
import com.bugjc.jwt.core.dto.UserTokenState;
import com.bugjc.jwt.core.shiro.jwt.JwtToken;
import com.bugjc.jwt.core.util.TokenHelper;
import com.bugjc.jwt.database.Service;
import com.xiaoleilu.hutool.util.StrUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private Service service;
    @Resource
    private TokenHelper tokenHelper;

    @Autowired
    public void setService(Service service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Device device) throws Exception {

        if (StrUtil.isBlank(username)){
            ResultGenerator.genFailResult("用户名不能为空");
        }

        if (StrUtil.isBlank(username)){
            ResultGenerator.genFailResult("密码不能为空");
        }

        // 构建JwtToken
        String token = tokenHelper.generateToken(username,password,device);
        JwtToken jwtToken = JwtToken
                .builder()
                .principal(username)
                .token(token)
                .password(password)
                .build();
        Subject subject = SecurityUtils.getSubject();
        int expiresIn = tokenHelper.getExpiredIn(device);
        try {
            // 该方法会调用JwtRealm中的doGetAuthenticationInfo方法
            subject.login(jwtToken);
            if (subject.isAuthenticated()){
                return ResultGenerator.genSuccessResult(new UserTokenState(token,expiresIn));
            }
            return new Result().setCode(ResultCode.RESOURCE_NOT_FOUND).setMessage("登录失败，请稍后重试");
        } catch (UnknownAccountException exception) {
            return ResultGenerator.genFailResult("账号不存在");
        } catch (IncorrectCredentialsException exception) {
            return ResultGenerator.genFailResult("错误的凭证，用户名或密码不正确");
        } catch (LockedAccountException exception) {
            return ResultGenerator.genFailResult("账户已锁定");
        } catch (ExcessiveAttemptsException exception) {
            return ResultGenerator.genFailResult("错误次数过多");
        } catch (AuthenticationException exception) {
            return ResultGenerator.genFailResult("认证失败");
        }
    }

    @GetMapping("/edit")
    public Result edit() {
        return ResultGenerator.genSuccessResult("You are editing now");
    }

    @GetMapping("/admin/hello")
    public Result adminView() {
        return ResultGenerator.genSuccessResult("You are visiting admin content");
    }

    @GetMapping("/annotation/require_auth")
    @RequiresAuthentication
    public Result annotationView1() {
        return ResultGenerator.genSuccessResult("You are visiting require_auth");
    }

    @GetMapping("/annotation/require_role")
    @RequiresRoles("admin")
    public Result annotationView2() {
        return ResultGenerator.genSuccessResult("You are visiting require_role");
    }

    @GetMapping("/annotation/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public Result annotationView3() {
        return ResultGenerator.genSuccessResult("You are visiting permission require edit,view");
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return ResultGenerator.genSuccessResult("Unauthorized");
    }
}
