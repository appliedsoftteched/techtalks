package com.app.demo.springbootonkubernetes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.demo.springbootonkubernetes.controller.GreetingController;

@SpringBootApplication
public class SpringbootonkubernetesApplication {

	static Logger logger	=	LoggerFactory.getLogger(SpringbootonkubernetesApplication.class);
	public static void main(String[] args) {
		logger.info("I AM  Starting the application");
		SpringApplication.run(SpringbootonkubernetesApplication.class, args);
		logger.info("I AM Started the application");
	}

}
