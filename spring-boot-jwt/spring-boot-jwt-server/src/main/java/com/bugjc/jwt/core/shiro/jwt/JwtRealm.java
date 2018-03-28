package com.bugjc.jwt.core.shiro.jwt;

import com.bugjc.jwt.core.util.SpringContextUtil;
import com.bugjc.jwt.core.util.TokenHelper;
import com.bugjc.jwt.database.Service;
import com.bugjc.jwt.database.UserBean;
import com.bugjc.jwt.database.Service;
import com.bugjc.jwt.database.UserBean;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author aoki
 */
public class JwtRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        //表示此Realm只支持JwtToken类型
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Service service = (Service) SpringContextUtil.getBean("service");
        String username = principals.toString();
        UserBean user = service.getUser(username);
        //设置用户角色权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        TokenHelper tokenHelper = (TokenHelper) SpringContextUtil.getBean("tokenHelper");
        Service service = (Service) SpringContextUtil.getBean("service");

        JwtToken jwtToken = (JwtToken) authenticationToken;

        // 获取token
        String token = jwtToken.getToken();
        String password = jwtToken.getPassword();

        // 从token中获取用户名
        String username = tokenHelper.getUsername(token);


        // 模拟数据查找
        UserBean user = service.getUser(username);

        // 用户不存在
        if (user == null) {
            throw new UnknownAccountException();
        }

        //密码不匹配
        if (password != null && !user.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }

        boolean flag = TokenHelper.verify(token,username,user.getPassword());

        if (!flag){
            throw new AuthenticationException("无效的token。");
        }

        try {
            return new SimpleAuthenticationInfo(
                    username,
                    token,
                    getName()
            );
        } catch (Exception e) {
            throw new AuthenticationException("添加凭据出错。");
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
