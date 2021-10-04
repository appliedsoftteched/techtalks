package com.fyndna.app.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fyndna.app.util.Singleton;
import com.fyndna.app.vo.Customer;
import com.fyndna.app.vo.HttpRequestContextVO;
import com.fyndna.app.vo.TransportVO;

public class FCRequestSender implements Runnable {

	Logger logger = LoggerFactory.getLogger(FCRequestSender.class);
	
	@Autowired
	CloseableHttpClient pooledClient;
	
	private String messageKey;

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	String fcURL	=	"http://localhost:8090/sleep/200";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Got the message in FC Request Sendier pooledClient "+Singleton.getInstance().getHttpClient());
		logger.debug("sending message to FC in new thread "+Thread.currentThread().getId() +" for message "+messageKey);
		HttpRequestContextVO vo = Singleton.getInstance().getReqMap().get(messageKey);
		callFC(vo.getMessage());
		sendDeferredResponse(messageKey);
		
	}
	
	private void callFC(Customer vo) {
		  try {
		   CloseableHttpClient httpClient = Singleton.getInstance().getHttpClient();
           logger.debug("Now sending request to FC");

           String servletURL = fcURL;
           
           HttpGet request = new HttpGet(servletURL);


         
			CloseableHttpResponse response = httpClient.execute(request);
			logger.debug("Got the response from FC");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void sendDeferredResponse (String messageKey) {
	   	HttpRequestContextVO httpAsynChData = Singleton.getInstance().getReqMap().get(messageKey);
		DeferredResult<ResponseEntity<Customer>> output = httpAsynChData.getDeferredResponse();
		Customer cust = httpAsynChData.getMessage();
		
		logger.debug("Sending the respone now");		    
	   	Singleton.getInstance().getReqMap().remove(messageKey);
	
		try {
	        cust.setCountry(messageKey);
			output.setResult(ResponseEntity.ok(cust));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   }
}
