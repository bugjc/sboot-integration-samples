package com.bugjc.exception;

/**
 * 业务异常基类
 * @author aoki
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    public BizException(int code, String msgFormat) {
        super(String.format(code+"|"+msgFormat));
        this.code = code;
        this.msg = msgFormat;
    }
    public BizException(Throwable cause) {
        super(cause);
    }


    public BizException() {
        super();
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }


    /**
     * 实例化异常
     *
     * @param msgFormat
     * @return
     */
    public BizException newInstance(String msgFormat) {
        return new BizException(this.code, msgFormat);
    }

}
