/*
 * Copyright (c) Microsoft Corporation and Dapr Contributors.
 * Licensed under the MIT License.
 */

package com.fyndna.app.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringBoot Controller to handle input binding.
 */
@RestController
public class PublishserController {

	Logger logger = LoggerFactory.getLogger(PublishserController.class);
  
	@Autowired
	Publisher publisher;
	
	@GetMapping("/send")
    public ResponseEntity<String> sendMessage() {
    	logger.info("Publishing message with initialization at startup");
    	

    	Publisher pub = new Publisher();
    	try {
			//pub.publishe("Rahul");
			publisher.publishMessage("Rahul Is sending");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok("FAILED");	
		}
        return ResponseEntity.ok("Done");

    }

	@GetMapping("/sendmessage")
    public ResponseEntity<String> getUsers() {
    	logger.info("Got calls from the call to get USERS");
    	

    	Publisher pub = new Publisher();
    	try {
			pub.publishe("Rahul");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok("FAILED");	
		}
        return ResponseEntity.ok("Done");

    }

}
