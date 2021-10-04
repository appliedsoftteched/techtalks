package com.fyndna.app;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.fyndna.app.config.AppConstants;
import com.fyndna.app.config.BootstrapAppInitializer;
import com.fyndna.app.servlet.ActorServlet;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

/**
 * Run In Local Mode with
 * 
 * dapr run --components-path ./component/pubsub --app-id governor-app --app-port 8082 -- java -jar target/governor-app-exec.jar com.fyndna.app.GovernorAppApplication -p 8082
 * 
 * @author rahulpoddar
 *
 */
@SpringBootApplication
public class GovernorAppApplication {

	@Autowired
	Environment env;
	
	public static void main(String[] args) {
		//SpringApplication.run(GovernorAppApplication.class, args);
		
		new SpringApplicationBuilder(GovernorAppApplication.class)
	    .initializers(new BootstrapAppInitializer())
	    .run(args);
	}

	@Bean
	public ServletRegistrationBean exampleServletBean() {
	    ServletRegistrationBean bean = new ServletRegistrationBean(
	      new ActorServlet(), "/asyncservlet/*");
	    bean.setLoadOnStartup(1);
	    return bean;
	}
	

	
	@Bean(destroyMethod = "shutdown",name="governor-task")
    public Executor taskExecutor() {
		String threadCount = env.getProperty("httprequesthandlerthreadcount");
		Integer count = Integer.getInteger(threadCount);
		System.out.println("Thread Count for governor http is  "+count);
		return Executors.newScheduledThreadPool(AppConstants.HTTP_THREADS);
    }


	@Bean(destroyMethod = "shutdown",name="fc-task")
	
    public Executor fctaskExecutor() {
		String threadCount = env.getProperty("fcrequesthandlerthreadcount");
		//ZOO
		//docker-compose up
		//kafka
//docker build -t rahulopengt/kafka:2.0 .
		//docker-compose up
		Integer count = Integer.getInteger(threadCount);
		System.out.println("Thread Count for governor http is  "+count);
        return Executors.newScheduledThreadPool(AppConstants.FC_THREADS);
    }


	
	@Bean
	public DaprClient initDaprClt() {
		DaprClient client = new DaprClientBuilder().build();
		return client;
	}

	@Bean(name="pooledClient")
	public CloseableHttpClient httpClient() {
	    return HttpClientBuilder.create().build();
	}
}
