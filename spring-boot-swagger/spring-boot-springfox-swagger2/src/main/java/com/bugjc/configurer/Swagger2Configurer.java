package com.bugjc.configurer;

import com.bugjc.core.dto.Result;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

        //自定义参数
        List<Parameter> pars = new ArrayList<Parameter>(){{
            add(new ParameterBuilder().name("Token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(true).build());
        }};

        //自定义异常信息
        ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder().code(200).message("成功").build());
            add(new ResponseMessageBuilder().code(400).message("请求参数错误").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(401).message("权限认证失败").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(403).message("请求资源不可用").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(404).message("请求资源不存在").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(409).message("请求资源冲突").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(415).message("请求格式错误").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(423).message("请求资源被锁定").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(501).message("请求方法不存在").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(503).message("服务暂时不可用").responseModel(new ModelRef("Error")).build());
            add(new ResponseMessageBuilder().code(-1).message("未知异常").responseModel(new ModelRef("Error")).build());
        }};

        return new Docket(DocumentationType.SWAGGER_2)
                .host("127.0.0.1:8001")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                //添加全局参数
                .globalOperationParameters(pars)
                //自定义接口排序
                .apiDescriptionOrdering(new Ordering<ApiDescription>() {

                    @Override
                    public int compare(ApiDescription left, ApiDescription right) {
                        int leftPos = left.getOperations().size() == 1 ? left.getOperations().get(0).getPosition() : 0;
                        int rightPos = right.getOperations().size() == 1 ? right.getOperations().get(0).getPosition() : 0;

                        int position = Integer.compare(leftPos, rightPos);

                        if(position == 0) {
                            position = left.getPath().compareTo(right.getPath());
                        }

                        return position;
                    }
                })
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .alternateTypeRules();
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
