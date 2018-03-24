package com.bugjc.tx.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger2配置类
 * @author : aoki
 */
@Configuration
@EnableSwagger2
public class Swagger2Configurer {
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.bugjc.web";
    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xxxxxxxxxxxxxxxx")
                .description("本文档是对xxx模块的接口输出，仅供xxx相关开发人员查看。")
                .version(VERSION)
                .contact(new Contact("aoki", "", "qing.muyi@foxmail.com"))
                .build();
    }
}
