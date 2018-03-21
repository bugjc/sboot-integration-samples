package com.bugjc.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 事物配置
 * @author : aoki
 */
@Component
@PropertySource("classpath:tx.properties")
@ConfigurationProperties(prefix="tx")
public class TxConfigurer {

    @Value("${tx.timeout}")
    public int timeout = 20;//事务组超时时间
    public static final int offsetTime = 1;//偏移时间
    private List<String> rule = new ArrayList<>();//事务规则集

    public List<String> getRule() {
        return rule;
    }

    public void setMethod(List<String> rule) {
        this.rule = rule;
    }
}
