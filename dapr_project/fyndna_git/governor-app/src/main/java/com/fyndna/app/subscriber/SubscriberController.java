/*
 * Copyright (c) Microsoft Corporation and Dapr Contributors.
 * Licensed under the MIT License.
 */

package com.fyndna.app.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyndna.app.service.FCRequestSender;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * SpringBoot Controller to handle input binding.
 */
@RestController
public class SubscriberController {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


  @Autowired
  @Qualifier(value="fc-task")
  private Executor fctaskExecutor;
  
  
  /**
   * Handles a registered publish endpoint on this app.
   * @param cloudEvent The cloud event received.
   * @return A message containing the time.
   */
  @Topic(name = "testingtopic", pubsubName = "${myAppProperty:messagebus}")
  @PostMapping(path = "/testingtopic")
  public Mono<Void> handleMessage(@RequestBody(required = false) CloudEvent<String> cloudEvent) {
    return Mono.fromRunnable(() -> {
      try {
        System.out.println("Subscriber got: " + cloudEvent.getData());
        System.out.println("Subscriber Message in thread : " + Thread.currentThread().getId());
        sendFCRequest(cloudEvent.getData());
        
        System.out.println("Subscriber got: " + OBJECT_MAPPER.writeValueAsString(cloudEvent));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  
  private void sendFCRequest(String messageId) {
	   
	  System.out.println("Sending messaeg to FC in AS "+messageId);
	  System.out.println("Sending messaeg in thread "+Thread.currentThread().getId());
	  FCRequestSender send	=	new FCRequestSender();
	  send.setMessageKey(messageId);
	  fctaskExecutor.execute(send);
	  
  }
}
