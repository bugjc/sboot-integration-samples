package com.bugjc.demo;

import com.bugjc.autocinfigure.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目入口
 * @author aoki
 */
@SpringBootApplication
@RestController
public class DemoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoClientApplication.class, args);
	}

	@Autowired
	private ExampleService exampleService;

	@GetMapping("/input")
	public String input(String word){
		return exampleService.wrap(word);
	}


}
