package com.bugjc.configurer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态数据源事物配置
 * @author : aoki
 */
@Component
@PropertySource("classpath:tx.properties")
@ConfigurationProperties(prefix="tx")
public class TxConfigurer {
    
    private List<String> rule = new ArrayList<>();

    public List<String> getRule() {
        return rule;
    }

    public void setMethod(List<String> rule) {
        this.rule = rule;
    }
}
