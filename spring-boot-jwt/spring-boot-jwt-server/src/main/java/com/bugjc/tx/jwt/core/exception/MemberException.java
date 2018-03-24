package com.bugjc.tx.jwt.core.exception;

public class MemberException {


    /**
     * 余额不足
     */
    public static final BizException NOT_BALANCE = new BizException(201, "余额不足");

    /**
     * 无效的账户
     */
    public static final BizException INVALID_ACCOUNT= new BizException(202, "无效的账户");

    /**
     * 无效的交易金额
     */
    public static final BizException INVALID_TXN_AMT= new BizException(203, "无效的交易金额");


}
