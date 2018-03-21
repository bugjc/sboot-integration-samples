package com.bugjc.exception;

public class TransactionException {

    public static final BizException TX_GROUP_TIMEOUT = new BizException(201, "事务组超时");

    public static final BizException UN_MATCH_RULE = new BizException(201, "未匹配规则");

    public static final BizException UN_CONFIG_RULE = new BizException(202, "未配置规则");


}
