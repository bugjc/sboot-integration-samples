package com.bugjc.aspect;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class TransactionalMethod {


    public static TransactionalMethod getInstance() {
        return HelperHolder.INSTANCE;
    }

    private static class HelperHolder {
        private static final TransactionalMethod INSTANCE = new TransactionalMethod();
    }

    private TransactionalMethod(){};


    public static void main(String[] args) {
        // 按指定模式在字符串查找
        String line = "getTest";
        String line1 = "savetest";
        boolean flag = new TransactionalMethod().matcherRule(line,"*get*T*");
        System.out.println("匹配成功？"+flag);

        Map<String,Object> exMap = new HashMap<>();
        exMap.put("Exception", new Exception("抛出Exception异常。"));
        exMap.put("RuntimeException", new RuntimeException("抛出RuntimeException异常。"));

        Exception ex = new TransactionalMethod().matcherException(exMap,"RuntimeException");
        System.out.println(ex.getMessage());

        System.out.println(new TransactionalMethod().matcherReadOnly("true"));

    }

    public static Map<String,Object> exMap = new HashMap(){{
        put("Exception", new Exception("抛出Exception异常。"));
        put("RuntimeException", new RuntimeException("抛出RuntimeException异常。"));
    }};

    /**
     * 匹配规则并返回
     * @param input
     * @param rules
     * @return
     */
    public MethodAttribute matcherRules(String input,List<String> rules){
        for (String rule : rules) {
            if (TransactionalMethod.getInstance().matcherRule(input, rule)){
                return JSON.parseObject(rule,MethodAttribute.class);
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

        if (StringUtils.isBlank(input)){
            return false;
        }

        if (StringUtils.isBlank(rule)){
            return false;
        }
        MethodAttribute methodAttribute = JSON.parseObject(rule,MethodAttribute.class);
        String regex = methodAttribute.getName().replaceAll("\\*","(.*)").toString();
        return Pattern.matches(regex,input);

    }

    /**
     * 简单匹配回滚异常
     * @param exMap
     * @param rollbackForEx
     * @return
     */
    public Exception matcherException(Map<String,Object> exMap,String rollbackForEx){
        if (exMap.isEmpty()){
            return new RuntimeException("未配置异常集合，抛出默认异常。");
        }

        if (exMap.get(rollbackForEx) == null){
            return new RuntimeException("未显示配置rollbackFor异常，抛出默认异常。");
        }

        return (Exception) exMap.get(rollbackForEx);

    }

    /**
     * 简单匹配只读属性
     * @param readOnly
     * @return
     */
    public boolean matcherReadOnly(String readOnly){
        if (StringUtils.isBlank(readOnly)){
            return false;
        }

        if(readOnly.equals("true")){
            return true;
        }

        return false;
    }

    static class MethodAttribute {
        private String name;
        private String rollbackFor;
        private String readOnly;
        public String getName() {
            return name;
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

        public String getReadOnly() {
            return readOnly;
        }

        public void setReadOnly(String readOnly) {
            this.readOnly = readOnly;
        }
    }


}
