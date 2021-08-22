package com.app.demo.springbootonkubernetes.vo;

import java.io.Serializable;

public class AppRequest  implements Serializable{

	@Override
	public String toString() {
		return "AppRequest [data=" + data + "]";
	}

	private RequestData data	=	null;

	public RequestData getData() {
		return data;
	}

	public void setData(RequestData data) {
		this.data = data;
	}
	
}
