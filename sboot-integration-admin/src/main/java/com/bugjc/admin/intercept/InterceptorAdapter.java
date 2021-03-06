package com.bugjc.admin.intercept;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qingyang
 */
@Slf4j
@Configuration
public class InterceptorAdapter implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("拦截");
		registry.addInterceptor(new UrlInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/","/login","/logout","/toLogin","/error/**","/upload/show/**");
	}
}
