package com.bugjc.exception;

public class TransactionRuleException {

    public static final BizException UN_MATCH_RULE = new BizException(201, "未匹配规则");

    public static final BizException UN_CONFIG_RULE = new BizException(202, "未配置规则");


}
