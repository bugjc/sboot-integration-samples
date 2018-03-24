package com.bugjc.tx.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalProperty {

    @Value("${service.base.url}")
    public String serviceBaseUrl;

}
