package com.app.demo.springbootonkubernetes.vo;

import java.io.Serializable;

public class OrderDetailVO implements Serializable {

	  private String document_title;
	  @Override
	public String toString() {
		return "OrderDetailVO [document_title=" + document_title + ", desc=" + desc + ", publisher=" + publisher + "]";
	}
	public String getDocument_title() {
		return document_title;
	}
	public void setDocument_title(String document_title) {
		this.document_title = document_title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	private String desc;
	  private String publisher;
}
