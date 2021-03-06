

package com.app.demo.springbootonkubernetes.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.demo.springbootonkubernetes.vo.AppRequest;
import com.app.demo.springbootonkubernetes.vo.AppResponse;
import com.app.demo.springbootonkubernetes.vo.RequestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GreetingController {

	private static final String template = "YES HELLO, %s!";
	private final AtomicLong counter = new AtomicLong();
	Logger logger	=	LoggerFactory.getLogger(GreetingController.class);
	
	@GetMapping("/greeting")
	public AppResponse greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new AppResponse(counter.incrementAndGet(), String.format(template, name));
	}
	
	@GetMapping("/ports")
	public String ports() {
		return "Reponse Fomr Service";
	}

	
	@RequestMapping(value = "/neworder", method = RequestMethod.POST)
	public ResponseEntity<AppResponse> saveNewOrder(@RequestBody AppRequest data) {
		//System.out.println("New Order Request "+data);
		logger.info("LLLLGot the in neworder as "+data);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonInString = mapper.writeValueAsString(data);
			logger.info("Got the JSON  "+jsonInString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AppResponse res	=	new AppResponse(1,data.getData()+" data ");
		res.setContent("");
		return new ResponseEntity<AppResponse>(res, HttpStatus.OK);

	}
}
