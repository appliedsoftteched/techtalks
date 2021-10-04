package com.fyndna.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import com.fyndna.app.util.AppProperty;

public class BootstrapAppInitializer  implements ApplicationContextInitializer<ConfigurableApplicationContext>{

	@Autowired
	Environment env;
	
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		// TODO Auto-generated method stub
		initializeSystemProperties();	
	}
	
	private void initializeSystemProperties(){
		if(env==null) {
			System.out.println("Envrionment prop is null");
		} else {
			System.out.println("Envrionment prop is NOT NULL");
			//		System.setProperty("log4j.configuration", "log4j.xml");
		}
//		System.setProperty("log4j.debug", "true");
		//System.out.println("Environment prop value "+env.getProperty("httprequesthandlerthreadcount"));
		//AppProperty.getInstance();
		
	}

}
