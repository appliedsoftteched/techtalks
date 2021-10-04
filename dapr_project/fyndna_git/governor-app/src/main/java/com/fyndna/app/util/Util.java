package com.fyndna.app.util;

import java.io.StringWriter;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;


import javax.xml.bind.Marshaller;

import com.fyndna.app.vo.Customer;

public class Util {

	
	public static String generateMessageKey() {
	    long longKey = System.currentTimeMillis();
		Random random = new Random();
		char randomizedCharacter = (char) (random.nextInt(26) + 'a');
	    System.out.println("Generated Random Character: " + randomizedCharacter);
	    String keyId = longKey+Character.toString(randomizedCharacter);
	    return keyId;
	}

	/**
	 * https://idineshkrishnan.com/return-xml-response-in-servlet/
	 * @param product
	 * @return
	 */
	public static String convert(Customer product) {

		StringWriter writer = new StringWriter();

		if (product != null) {

			try {
				JAXBContext context = JAXBContext.newInstance(Customer.class);
				
				Marshaller marshaller = context.createMarshaller();
				
				marshaller.marshal(product, writer);
				
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}

		return writer.toString();
	}
}
