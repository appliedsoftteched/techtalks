package com.fyndna.app.vo;

import javax.servlet.AsyncContext;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

public class HttpRequestContextVO {

	private DeferredResult<ResponseEntity<Customer>> deferredResponse;
	public DeferredResult<ResponseEntity<Customer>> getDeferredResponse() {
		return deferredResponse;
	}
	public void setDeferredResponse(DeferredResult<ResponseEntity<Customer>> deferredResponse) {
		this.deferredResponse = deferredResponse;
	}
	private AsyncContext asynhContext;
	private Customer message;
	public AsyncContext getAsynhContext() {
		return asynhContext;
	}
	public void setAsynhContext(AsyncContext asynhContext) {
		this.asynhContext = asynhContext;
	}
	public Customer getMessage() {
		return message;
	}
	public void setMessage(Customer message) {
		this.message = message;
	}

}
