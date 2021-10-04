/*
 * Copyright (c) Microsoft Corporation and Dapr Contributors.
 * Licensed under the MIT License.
 */

package com.fyndna.app.publisher;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.Metadata;

import static java.util.Collections.singletonMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Message publisher.
 * 1. Build and install jars:
 * mvn clean install
 * 2. cd [repo root]/examples
 * 3. Run the program:
 * dapr run --components-path ./components/pubsub --app-id publisher -- \
 *   java -jar target/dapr-java-sdk-examples-exec.jar io.dapr.examples.pubsub.http.Publisher
 */
@Component
public class Publisher {

	@Autowired
	DaprClient daprClt;
	
	 public void publishMessage(String args) throws Exception {
	        try {
	          //for (int i = 0; i < NUM_MESSAGES; i++) {
	            String message = String.format("This is message "+args);
	            //Publishing messages
	            daprClt.publishEvent(
	                PUBSUB_NAME,
	                TOPIC_NAME,
	                args,
	                singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();
	            System.out.println("Published message: " + args);


	          System.out.println("Done.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	  }
	
  //Number of messages to be sent.
  private static final int NUM_MESSAGES = 10;

  //Time-to-live for messages published.
  private static final String MESSAGE_TTL_IN_SECONDS = "1000";

  //The title of the topic to be used for publishing
  private static final String TOPIC_NAME = "testingtopic";
  //private static final String TOPIC_NAME = "sample";
  
  //The name of the pubsub
  private static final String PUBSUB_NAME = "messagebus";

  /**
   * This is the entry point of the publisher app example.
   * @param args Args, unused.
   * @throws Exception A startup Exception.
   */
  public static void main(String[] args) throws Exception {
    try (DaprClient client = new DaprClientBuilder().build()) {
      for (int i = 0; i < NUM_MESSAGES; i++) {
        String message = String.format("This is message #%d", i);
        //Publishing messages
        client.publishEvent(
            PUBSUB_NAME,
            TOPIC_NAME,
            message,
            singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();
        System.out.println("Published message: " + message);

        try {
          Thread.sleep((long) (1000 * Math.random()));
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
          return;
        }
      }

      // This is an example, so for simplicity we are just exiting here.
      // Normally a dapr app would be a web service and not exit main.
      System.out.println("Done.");
    }
  }
  
  
    public void publishe(String args) throws Exception {
        try (DaprClient client = new DaprClientBuilder().build()) {
          //for (int i = 0; i < NUM_MESSAGES; i++) {
            String message = String.format("This is message ");
            //Publishing messages
            client.publishEvent(
                PUBSUB_NAME,
                TOPIC_NAME,
                message,
                singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();
            System.out.println("Published message: " + message);

//            try {
//              Thread.sleep((long) (1000 * Math.random()));
//            } catch (InterruptedException e) {
//              e.printStackTrace();
//              Thread.currentThread().interrupt();
//              return;
//            }
          //}

          // This is an example, so for simplicity we are just exiting here.
          // Normally a dapr app would be a web service and not exit main.
          System.out.println("Done.");
        }
  }
}
