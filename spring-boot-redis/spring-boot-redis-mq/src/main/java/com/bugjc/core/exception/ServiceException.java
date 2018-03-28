package com.bugjc.core.exception;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 * @author : aoki
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String msgFormat, Object... args) {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 实例化异常
     *
     * @param msgFormat
     * @param args
     * @return
     */
    public ServiceException newInstance(String msgFormat, Object... args) {
        return new ServiceException(msgFormat, args);
    }
}
