package com.app.demo.springbootonkubernetes.vo;

public class RequestData {

	@Override
	public String toString() {
		return "RequestData [orderId=" + orderId + "]";
	}

	private int orderId=0;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
