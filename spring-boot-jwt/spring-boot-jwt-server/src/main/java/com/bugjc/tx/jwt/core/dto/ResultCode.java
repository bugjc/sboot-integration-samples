package com.bugjc.tx.jwt.core.dto;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * @author : aoki
 */
public enum ResultCode {
    //成功
    SUCCESS(200),
    //失败
    FAIL(400),
    //未认证（签名错误）
    UNAUTHORIZED(401),
    //资源不可用
    RESOURCE_NOT_FOUND(403),
    //接口不存在
    NOT_FOUND(404),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
