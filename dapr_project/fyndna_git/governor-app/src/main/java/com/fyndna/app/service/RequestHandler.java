package com.fyndna.app.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fyndna.app.publisher.Publisher;
import com.fyndna.app.util.Singleton;
import com.fyndna.app.util.Util;
import com.fyndna.app.vo.Customer;
import com.fyndna.app.vo.HttpRequestContextVO;
import com.fyndna.app.vo.TransportVO;

public class RequestHandler implements Runnable {

	Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	
	private String messageKey;
	

	Publisher publisher;
	
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public String getMessageKey() {
		return messageKey;
	}


	//REMOVE AFTER TEST
	private DeferredResult<ResponseEntity<Customer>> deferredResponse;
	public DeferredResult<ResponseEntity<Customer>> getDeferredResponse() {
		return deferredResponse;
	}
	public void setDeferredResponse(DeferredResult<ResponseEntity<Customer>> deferredResponse) {
		this.deferredResponse = deferredResponse;
	}
	
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}


	private Customer customer;
	
	
	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		TransportVO vo = new TransportVO();
		vo.setMessageKey(messageKey);
		vo.setCustomer(customer);
		//sendResponse(vo);
		sendToKafkaDaprBindng(vo);
		//sendDeferredResponse(vo);
	}

	
	private void sendToKafkaDaprBindng(TransportVO vo) {

		try {
			publisher.publishMessage(vo.getMessageKey());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	   private void sendDeferredResponse (TransportVO vo) {
	   	HttpRequestContextVO httpAsynChData = Singleton.getInstance().getReqMap().get(vo.getMessageKey());
		DeferredResult<ResponseEntity<Customer>> output = httpAsynChData.getDeferredResponse();
		Customer cust = httpAsynChData.getMessage();
		logger.debug("Sending the respone now");		    
	   	Singleton.getInstance().getReqMap().remove(vo.getMessageKey());
	
		try {
	        output.setResult(ResponseEntity.ok(cust));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   }

	   private void sendResponse (TransportVO vo) {
		   	HttpRequestContextVO httpAsynChData = Singleton.getInstance().getReqMap().get(vo.getMessageKey());
		   	
			
		   	AsyncContext asyncContext= httpAsynChData.getAsynhContext();

			Customer cust = httpAsynChData.getMessage();
		    System.out.println("While Sending Response "+cust);
		    
		   	Singleton.getInstance().getReqMap().remove(vo.getMessageKey());
		       ServletResponse response = asyncContext.getResponse();
		       //response.setContentType("text/plain");
		       response.setContentType("application/xml");
		       PrintWriter out;
				try {
					out = response.getWriter();
					//out.print("Work completed in post. Time elapsed: ");
					out.print(Util.convert(vo.getCustomer()));
			        out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
		       
		       asyncContext.complete();

		   }

	
}
