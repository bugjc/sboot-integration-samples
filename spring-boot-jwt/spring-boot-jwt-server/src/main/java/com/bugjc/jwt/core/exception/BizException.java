package com.bugjc.jwt.core.exception;

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

    public BizException(int code, String msg) {
        super(String.format(code+"|"+msg));
        this.code = code;
        this.msg = msg;
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
     * @param msg
     * @return
     */
    public BizException newInstance(String msg) {
        return new BizException(this.code, msg);
    }

}
