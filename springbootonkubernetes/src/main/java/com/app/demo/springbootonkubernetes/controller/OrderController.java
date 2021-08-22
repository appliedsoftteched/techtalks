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
import com.app.demo.springbootonkubernetes.vo.OrderDetailVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class OrderController {

	private static final String template = "YES HELLO, %s!";
	
	Logger logger	=	LoggerFactory.getLogger(OrderController.class);
	
	

	
	@RequestMapping(value = "/orderbook", method = RequestMethod.POST)
	public ResponseEntity<AppResponse> saveNewOrder(@RequestBody OrderDetailVO order) {
		//System.out.println("New Order Request "+data);
		String response	=	"";
		logger.info("Got the details for new order "+order);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonInString = mapper.writeValueAsString(order);
			logger.info("Got the JSON  "+jsonInString);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response	=	"Thank your for your order for "+order.getPublisher();
		
		AppResponse res	=	new AppResponse(1,response);
		
		return new ResponseEntity<AppResponse>(res, HttpStatus.OK);

	}

}
