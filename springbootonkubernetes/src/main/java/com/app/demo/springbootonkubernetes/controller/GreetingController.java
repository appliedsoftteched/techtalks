

package com.app.demo.springbootonkubernetes.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.springbootonkubernetes.vo.AppResponse;

@RestController
public class GreetingController {

	private static final String template = "YES HELLO, %s!";
	private final AtomicLong counter = new AtomicLong();
	Logger logger	=	LoggerFactory.getLogger(GreetingController.class);
	
	@GetMapping("/greeting")
	public AppResponse greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new AppResponse(counter.incrementAndGet(), String.format(template, name));
	}
}
