package com.fyndna.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:appconfig.properties")
public class AppConfig {
	
	private String threacount;

	public String getThreacount() {
		return threacount;
	}

	public void setThreacount(String threacount) {
		this.threacount = threacount;
	}

private String topicName;

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
