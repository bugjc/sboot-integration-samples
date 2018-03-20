package com.bugjc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.exception.BizException;
import com.bugjc.exception.TransactionRuleException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TransactionRuleUtil {


    public static TransactionRuleUtil getInstance() {
        return HelperHolder.INSTANCE;
    }

    private static class HelperHolder {
        private static final TransactionRuleUtil INSTANCE = new TransactionRuleUtil();
    }

    private TransactionRuleUtil(){};

    /**
     * 自定义异常列表
     */
    public static Map<String,Object> exMap = new HashMap(){{
        put("Exception", new Exception());
        put("RuntimeException", new BizException());
    }};


    public static void main(String[] args) {
        // 按指定模式在字符串查找
        String line = "getTest";
        String line1 = "savetest";
        boolean flag = new TransactionRuleUtil().matcherRule(line,"*get*T*");
        System.out.println("匹配成功？"+flag);

        Map<String,Object> exMap = new HashMap<>();
        exMap.put("Exception", new Exception("抛出Exception异常。"));
        exMap.put("RuntimeException", new RuntimeException("抛出RuntimeException异常。"));

        Exception ex = new TransactionRuleUtil().matcherException(exMap,"RuntimeException","1");
        System.out.println(ex.getMessage());

    }

    /**
     * 匹配规则并返回
     * @param input
     * @param rules
     * @return
     */
    public RuleAttribute matcherRules(String input,List<String> rules){
        for (String rule : rules) {
            if (TransactionRuleUtil.getInstance().matcherRule(input, rule)){
                return JSON.parseObject(rule,RuleAttribute.class);
            }
        }
        return null;
    }

    /**
     * 简单匹配规则
     * @param input
     * @param rule
     * @return
     */
    public boolean matcherRule(String input,String rule){

        if (StringUtils.isEmpty(input)){
            return false;
        }

        if (StringUtils.isEmpty(rule)){
            return false;
        }

        RuleAttribute RuleAttribute = JSON.parseObject(rule,RuleAttribute.class);
        String regex = RuleAttribute.getName().replaceAll("\\*","(.*)").toString();
        return Pattern.matches(regex,input);

    }

    /**
     * 简单匹配回滚异常
     * @param exMap
     * @param rollbackForEx
     * @return
     */
    public Exception matcherException(Map<String,Object> exMap,String rollbackForEx,String msg){
        if (exMap.isEmpty()){
            return new RuntimeException("未配置异常集合，抛出默认异常。");
        }

        if (exMap.get(rollbackForEx) == null){
            return new RuntimeException("未显示配置rollbackFor异常，抛出默认异常。");
        }

        return (Exception) getExmapByKey(rollbackForEx,msg);

    }

    public Exception getExmapByKey(String rollbackFor,String msg){
        Exception ex = (Exception) exMap.get(rollbackFor);
        if (ex instanceof BizException){
            return ((BizException)ex).newInstance(msg);
        }
        return ex;
    }


    /**
     * 简单匹配只读属性
     * @param methodName
     * @return
     */
    public JSONObject matcherReadOnly(String methodName, List<String> rules){

        if (rules.isEmpty()){
            throw TransactionRuleException.UN_MATCH_RULE;
        }

        //匹配方法事务规则
        RuleAttribute ruleAttribute = TransactionRuleUtil.getInstance().matcherRules(methodName, rules);
        if (ruleAttribute == null){
            throw TransactionRuleException.UN_CONFIG_RULE;
        }
        //获取匹配到的规则属性
        boolean readOnly = ruleAttribute.getReadOnly();

        if(!readOnly){
            return JSON.parseObject(JSON.toJSONString(ruleAttribute));
        }

        return null;
    }

    static class RuleAttribute {
        private String name;
        private String rollbackFor = "Exception";
        private boolean readOnly = false;
        public String getName() {
            return name;
        }

        public static RuleAttribute getInstance() {
            return new RuleAttribute();
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRollbackFor() {
            return rollbackFor;
        }

        public void setRollbackFor(String rollbackFor) {
            this.rollbackFor = rollbackFor;
        }

        public boolean getReadOnly() {
            return readOnly;
        }

        public void setReadOnly(boolean readOnly) {
            this.readOnly = readOnly;
        }
    }

}
