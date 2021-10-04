package com.fyndna.app.component;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fyndna.app.config.AppConfig;

@Component
public class AppInitComponent {

	 private static final Logger logger
     = LoggerFactory.getLogger(AppInitComponent.class);

   @Autowired
   Environment environment;
   
   @Autowired
   AppConfig appConfig;
   
   @PostConstruct
   public void init() {
	   logger.info(":VALUES FROM ENV "+Arrays.asList(environment.getDefaultProfiles()));
	   String threadCount = environment.getProperty("app.fcrequesthandlerthreadcount");
		Integer count = Integer.getInteger(threadCount);
       logger.info("Envrionment variable values "+count);
       logger.info("Envrionment variable values "+appConfig.getThreacount());
         
   }
}
