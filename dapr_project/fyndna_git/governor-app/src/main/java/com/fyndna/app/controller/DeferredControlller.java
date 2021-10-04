package com.fyndna.app.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.fyndna.app.publisher.Publisher;
import com.fyndna.app.service.RequestHandler;
import com.fyndna.app.util.Singleton;
import com.fyndna.app.util.Util;
import com.fyndna.app.vo.Customer;
import com.fyndna.app.vo.HttpRequestContextVO;

@RestController
public class DeferredControlller {

	private final static Logger logger = LoggerFactory.getLogger(DeferredControlller.class);

		

    @Autowired
    @Qualifier(value="governor-task")
    private Executor taskExecutor;
    
    @Autowired
    private Environment env;
    
    @Autowired
    Publisher publisher;

	 @PostMapping(path =
	 "${httprequestep}",consumes=MediaType.APPLICATION_XML_VALUE,
	 produces=MediaType.APPLICATION_XML_VALUE) 
	 public DeferredResult<ResponseEntity<Customer>> getCustomerTaskExec(@RequestBody Customer cust) {
		   logger.info("Received async-deferredresult request "+cust);
		   logger.debug(env.getProperty("httprequesthandlerthreadcount"));
		   DeferredResult<ResponseEntity<Customer>> output =  new DeferredResult(10000l);
		    output.onTimeout(() ->{
		    	logger.info("Timed Out");
		    output.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout."));
		    
	 		});
		    

		    String messageKey	=	Util.generateMessageKey();	       
		    
		    
		    System.out.println(cust.getCountry());
		    HttpRequestContextVO contextHolder	=	new HttpRequestContextVO();
		    contextHolder.setDeferredResponse(output);
		    contextHolder.setMessage(cust);
		    
		    Singleton.getInstance().getReqMap().put(messageKey, contextHolder);
		    RequestHandler handler	=	new RequestHandler();
		    handler.setMessageKey(messageKey);
		    handler.setCustomer(cust);
		    handler.setDeferredResponse(output);
		    handler.setPublisher(publisher);
		    taskExecutor.execute(handler);		    
		    
		    
		    
		    logger.info("servlet thread freed");
		    return output;
		 
	 }

	 @PostMapping(path =
	 "/paymenttxn_b",consumes=MediaType.APPLICATION_XML_VALUE) 
	 public DeferredResult<ResponseEntity<Customer>> getCarInfoKafka_Ex_b(@RequestBody Customer cust) {
		   logger.info("Received async-deferredresult request "+cust.getName());
		    DeferredResult<ResponseEntity<Customer>> output = new DeferredResult<>();
		    ForkJoinPool.commonPool().submit(() -> {
		        logger.info("Processing in separate thread");
		        try {
				    Thread.sleep(500);
		        } catch (InterruptedException e) {
		        }
		        output.setResult(ResponseEntity.ok(cust));
		    });
		    logger.info("servlet thread freed");
		    return output;
		 
	 }

//
//	 @PostMapping(path =
//	 "/test") 
//	 public DeferredResult<ResponseEntity<MyModel>> getCarInfoKafka_Ex(@RequestBody MyModel cust) {
//		   logger.info("Received async-deferredresult request");
//		    DeferredResult<ResponseEntity<MyModel>> output = new DeferredResult<>();
//		    ForkJoinPool.commonPool().submit(() -> {
//		        logger.info("Processing in separate thread");
//		        try {
//				    Thread.sleep(500);
//		        } catch (InterruptedException e) {
//		        }
//		        output.setResult(ResponseEntity.ok(cust));
//		    });
//		    logger.info("servlet thread freed");
//		    return output;
//		 
//	 }

	 @PostMapping(path =
			 "/testcall",consumes=MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	 public Callable<Customer> processUpload(@RequestBody Customer file) {
		 logger.info("processUpload/..."+file.getName());
	 	return new Callable<Customer>() {
	 		public Customer call() throws Exception {
	 			// ...
	 			return file;
	 		}
	 	};
	 }

}
