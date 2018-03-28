package com.bugjc.jwt.core.shiro.filter;

import com.bugjc.jwt.core.dto.Result;
import com.bugjc.jwt.core.dto.ResultCode;
import com.bugjc.jwt.core.shiro.jwt.JwtToken;
import com.bugjc.jwt.core.util.SpringContextUtil;
import com.bugjc.jwt.core.util.TokenHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author aoki
 */
public class JwtFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        TokenHelper tokenHelper = (TokenHelper) SpringContextUtil.getBean("tokenHelper");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorization = tokenHelper.getToken(httpRequest);
        if (authorization == null){
            authorization = tokenHelper.getTokenByParam(httpRequest);
            if (authorization == null){
                throw new AuthenticationException("请求中需要携带token。");
            }

        }

        return JwtToken.builder().token(authorization).build();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json;charset=UTF-8");
            servletResponse.setHeader("Access-Control-Allow-Origin","*");
            ObjectMapper objectMapper = new ObjectMapper();

            response.getWriter().write(objectMapper.writeValueAsString(new Result().setCode(ResultCode.UNAUTHORIZED).setMessage(ae.getMessage())));
        } catch (IOException e) {}
        return false;
    }
}
