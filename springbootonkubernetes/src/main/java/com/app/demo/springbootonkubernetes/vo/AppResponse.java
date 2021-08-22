package com.app.demo.springbootonkubernetes.vo;

import java.io.Serializable;

public class AppResponse implements Serializable{


	private final long id;
	private String content;

	public AppResponse(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String p_content) {
		content	=	p_content;
	}
}
