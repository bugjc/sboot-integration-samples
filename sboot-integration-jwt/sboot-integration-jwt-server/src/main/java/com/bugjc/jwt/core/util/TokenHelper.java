package com.bugjc.jwt.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bugjc.jwt.core.common.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;




/**
 * jwt工具类
 * @author aoki
 */
@PropertySource("classpath:application.properties")
@ConfigurationProperties()
@Component
public class TokenHelper {

    @Value("${app.name}")
    private String appName;

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.expires-in}")
    private int expiresIn;

    @Value("${jwt.mobile-expires-in}")
    private int mobileExpiresIn;

    @Value("${jwt.header}")
    private String authHeader;

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    @Autowired
    TimeProvider timeProvider;


    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    public String generateToken(String username, String secret, Device device) throws UnsupportedEncodingException {
        String audience = generateAudience(device);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(appName)
                .withAudience(audience)
                .withIssuedAt(timeProvider.now())
                // 附带username信息
                .withClaim("username", username)
                .withExpiresAt(generateExpirationDate(device))
                .sign(algorithm);
    }

    /**
     * 通过spring-mobile-device的device检测访问主体
     * @param device
     * @return
     */
    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            //PC端
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            //平板
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            //手机
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    /**
     * 获取所有claims
     * @param token
     * @return
     */
    private Map<String,Claim> getAllClaimsFromToken(String token) {
        Map<String,Claim> claims;
        try {
            DecodedJWT jwt = JWT.decode(token);
            claims = jwt.getClaims();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(Device device) {
        long expires = device.isTablet() || device.isMobile() ? mobileExpiresIn : expiresIn;
        return new Date(timeProvider.now().getTime() + expires * 1000);
    }

    public int getExpiredIn(Device device) {
        return device.isMobile() || device.isTablet() ? mobileExpiresIn : expiresIn;
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withClaim("username", username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    public String getTokenByParam(HttpServletRequest request) {

        String authHeader = request.getParameter(getAuthHeader());
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(authHeader);
    }

    public String getAuthHeader() {
        return authHeader.toLowerCase();
    }
}