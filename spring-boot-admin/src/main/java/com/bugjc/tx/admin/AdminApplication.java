package com.bugjc.tx.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@MapperScan("com.bugjc.admin.dao")
public class AdminApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer,CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));            
		container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));            
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));     
	}


	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Admin启动成功！");
	}
}
