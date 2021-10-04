package com.fyndna.app.vo;

import java.io.Serializable;

public class TransportVO implements Serializable{

private String messageKey;

public String getMessageKey() {
	return messageKey;
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

}
