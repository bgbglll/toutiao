package com.bg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class ToutiaoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		//changed
		SpringApplication.run(ToutiaoApplication.class, args);
	}
}

