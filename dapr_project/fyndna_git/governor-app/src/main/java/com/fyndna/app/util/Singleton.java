package com.fyndna.app.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.fyndna.app.vo.HttpRequestContextVO;



//Java program implementing Singleton class
//with using getInstance() method

//Class 1
//Helper class
public class Singleton {
	// Static variable refereence of single_instance
	// of type Singleton
	private static Singleton single_instance = null;

	// Decl;aring a variable of type String
	public String s;

	public Map<String, HttpRequestContextVO> reqMap;
	
	public Map<String, HttpRequestContextVO> getReqMap() {
		return reqMap;
	}
	
//	public Map<String, AsyncContext> reqMap;
//	
//	public Map<String, AsyncContext> getReqMap() {
//		return reqMap;
//	}

	public void setReqMap(Map<String, HttpRequestContextVO> reqMap) {
		this.reqMap = reqMap;
	}

	// Constructor
	// Here we will be creating private constructor
	// restricted to this class itself
//	private Singleton()
//	{
//		s = "Hello I am a string part of Singleton class";
//	}

	 private final PoolingHttpClientConnectionManager cm;
	    private final CloseableHttpClient threadSafeClient;

	    private Singleton() {
	        int httpPoolSize = 20;
	        cm = new PoolingHttpClientConnectionManager();
	        // Increase max total connection to 200
	        cm.setMaxTotal(httpPoolSize);
	        // Increase default max connection per route to 20
	        cm.setDefaultMaxPerRoute(httpPoolSize);
	        threadSafeClient = HttpClients.custom()
	                .setConnectionManager(cm)
	                .build();
	    }
	    
	    public CloseableHttpClient getHttpClient() {
	        return threadSafeClient;
	    }
	    
	// Static method
	// Static method to create instance of Singleton class
	public static Singleton getInstance()
	{
		if (single_instance == null) {
			single_instance = new Singleton();
			single_instance.reqMap=new HashMap<String, HttpRequestContextVO>();
			
		}
		return single_instance;
	}
}

//Class 2
//Main class
class GFG {
	// Main driver method
	public static void main(String args[])
	{
		// Instantiating Singleton class with variable x
		Singleton x = Singleton.getInstance();

		// Instantiating Singleton class with variable y
		Singleton y = Singleton.getInstance();

		// Instantiating Singleton class with variable z
		Singleton z = Singleton.getInstance();

		// Printing the hash code for above variable as
		// declared
		System.out.println("Hashcode of x is "
						+ x.hashCode());
		System.out.println("Hashcode of y is "
						+ y.hashCode());
		System.out.println("Hashcode of z is "
						+ z.hashCode());

		// Condition check
		if (x == y && y == z) {

			// Print statement
			System.out.println(
				"Three objects point to the same memory location on the heap i.e, to the same object");
		}

		else {
			// Print statement
			System.out.println(
				"Three objects DO NOT point to the same memory location on the heap");
		}
	}
}
