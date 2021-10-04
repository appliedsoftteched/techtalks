package com.fyndna.app.servlet;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;

import com.fyndna.app.service.RequestHandler;
import com.fyndna.app.util.Singleton;
import com.fyndna.app.util.Util;
import com.fyndna.app.vo.Customer;
import com.fyndna.app.vo.HttpRequestContextVO;
import com.fyndna.app.vo.TransportVO;

/**
 * Servlet implementation class SimpleServlet
 */
//@WebServlet("/hello")
@WebServlet(urlPatterns={"/async"}, asyncSupported=true)
public class ActorServlet extends HttpServlet {

   private static final long serialVersionUID = 1L;

   Logger logger = LoggerFactory.getLogger(ActorServlet.class);

	

   @Autowired
   private TaskExecutor taskExecutor;
   
   
   private ExecutorService producerWorkerThreadPool = null;

   @Autowired
   Environment env;
   
   @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		//super.init();
	   //logger.debug("Thread count for the pool "+env.getProperty("httprequesthandlerthreadcount"));
       //int noOfProducerWorkerThreads = Integer.parseInt(Utils.getProperty(Constants.NUM_PRODUCER_WORKER_THREADS));

       //producerWorkerThreadPool = Executors.newFixedThreadPool(5);

	   initilizeProducer();
	}

   
   private void initilizeProducer() {

   }

   private void sendTask(TransportVO vo) {
   taskExecutor.execute(new Runnable() {
		
		@Override
		public void run() {
			logger.info("Processing in separate thread");
	        try {
			    //Thread.sleep(200);
	        	
	        	//getDataFromFC(cust);
	        	
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }				
	        logger.info("Sending Output now");
	        
		}

	});
   }
   
//   @Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		//super.doPost(req, resp);
//	   	System.out.println("Got in post method....with "+req.getParameterNames());
//	    final long startTime = System.nanoTime();
//	    final AsyncContext asyncContext = req.startAsync(req, resp);
//
//	    String messageKey	=	Util.generateMessageKey();	       
//	    
//	    String xml = readData(req);
//	    System.out.println(xml);
//	    Customer cust = getCustomer(xml);
//	    System.out.println(cust.getCountry());
//	    HttpRequestContextVO contextHolder	=	new HttpRequestContextVO();
//	    contextHolder.setAsynhContext(asyncContext);
//	    contextHolder.setMessage(cust);
//	    
//	    Singleton.getInstance().getReqMap().put(messageKey, contextHolder);
//	    new Thread() {
//
//	      @Override
//	      public void run() {
//	        try {
//	        	
//	        	sendMessage(messageKey,cust);	    
//
//	        } catch (Exception e) {
//	          throw new RuntimeException(e);
//	        }
//	      }
//	    }.start();
//	   
//	}
//
//  
   
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
	   	System.out.println("Got in post method....with "+req.getParameterNames());
	    final long startTime = System.nanoTime();
	    final AsyncContext asyncContext = req.startAsync(req, resp);

	    String messageKey	=	Util.generateMessageKey();	       
	    
	    String xml = readData(req);
	    System.out.println(xml);
	    Customer cust = getCustomer(xml);
	    System.out.println(cust.getCountry());
	    HttpRequestContextVO contextHolder	=	new HttpRequestContextVO();
	    contextHolder.setAsynhContext(asyncContext);
	    contextHolder.setMessage(cust);
	    
	    Singleton.getInstance().getReqMap().put(messageKey, contextHolder);
	    RequestHandler handler	=	new RequestHandler();
	    handler.setMessageKey(messageKey);
	    handler.setCustomer(cust);
	    
	    taskExecutor.execute(handler);
	    
	   
	}

   
   private void sendMessage(String messageKey,Customer cust){
	   TransportVO response	=	new TransportVO();
	   response.setMessageKey(messageKey);
	   response.setCustomer(cust);
	   sendResponse(response);
   }
   
   private void sendResponse (TransportVO vo) {
   	HttpRequestContextVO httpAsynChData = Singleton.getInstance().getReqMap().get(vo.getMessageKey());
   	
	AsyncContext asyncContext= httpAsynChData.getAsynhContext();

	Customer cust = httpAsynChData.getMessage();
    System.out.println("While Sending Response "+cust);
    
   	Singleton.getInstance().getReqMap().remove(vo.getMessageKey());
       ServletResponse response = asyncContext.getResponse();
       //response.setContentType("text/plain");
       response.setContentType("application/xml");
       PrintWriter out;
		try {
			out = response.getWriter();
			//out.print("Work completed in post. Time elapsed: ");
			out.print(Util.convert(vo.getCustomer()));
	        out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       
       asyncContext.complete();

   }
   private String readData(HttpServletRequest request) {
	   String xml = null;
       try {
               byte[] xmlData = new byte[request.getContentLength()];

               //Start reading XML Request as a Stream of Bytes
               InputStream sis = request.getInputStream();
               BufferedInputStream bis = new BufferedInputStream(sis);

               bis.read(xmlData, 0, xmlData.length);

               if (request.getCharacterEncoding() != null) {
                       xml = new String(xmlData, request.getCharacterEncoding());
               } else {
                       xml = new String(xmlData);
               }
               //xml and xmlData contains incomplete data
       } catch (IOException ioe) {
         ioe.printStackTrace();
       } 
       return xml;
   }
  
   /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	    final long startTime = System.nanoTime();
	    final AsyncContext asyncContext = request.startAsync(request, response);

	    new Thread() {

	      @Override
	      public void run() {
	        try {
	          ServletResponse response = asyncContext.getResponse();
	          response.setContentType("text/plain");
	          PrintWriter out = response.getWriter();
	          Thread.sleep(1000);
	          out.print("Work completed. Time elapsed: " + (System.nanoTime() - startTime));
	          out.flush();
	          asyncContext.complete();
	        } catch (Exception e) {
	          throw new RuntimeException(e);
	        }
	      }
	    }.start();
	}
	
	  private Customer getCustomer(String xml) {
		   Customer cust = new Customer();
		   JAXBContext jaxbContext;
		   try
		   {
		       jaxbContext = JAXBContext.newInstance(Customer.class);              
		    
		       Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    
		       cust = (Customer) jaxbUnmarshaller.unmarshal(new StringReader(xml));
		        
		       //System.out.println(cust);
		   }
		   catch (JAXBException e) 
		   {
		       e.printStackTrace();
		   }
		   return cust;
		   
	   }	
}