package com.bugjc.configurer;

import org.springframework.beans.factory.annotation.Value;
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
@ConfigurationProperties(prefix="tx.manager.attributes")
public class TxConfigurer {

    @Value("${tx.scan.base-package}")
    private String basePackage;
    @Value("${tx.manager.pointcut.service}")
    private String servicePointcut;
    @Value("${tx.manager.pointcut.dao}")
    private String daoPointcut;
    private List<String> method = new ArrayList<>();

    public String getServicePointcut() {
        return servicePointcut;
    }

    public void setServicePointcut(String servicePointcut) {
        this.servicePointcut = servicePointcut;
    }

    public String getDaoPointcut() {
        return daoPointcut;
    }

    public void setDaoPointcut(String daoPointcut) {
        this.daoPointcut = daoPointcut;
    }

    public List<String> getMethod() {
        return method;
    }

    public void setMethod(List<String> method) {
        this.method = method;
    }
}
