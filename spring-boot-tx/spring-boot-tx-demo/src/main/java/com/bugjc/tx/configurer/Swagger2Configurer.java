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
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.bugjc.tx.web";
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
                .title("积存-Spring事务")
                .description("本文档是对Spring多数据源事务小工具包的接口输出，仅供Github stars的人员查看😊。")
                .version(VERSION)
                .contact(new Contact("aoki", "", "qing.muyi@foxmail.com"))
                .build();
    }
}
