package com.fyndna.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class AppProperty {

	private HashMap<String, String>	propertyKeyVal	=	null;
	
	private static AppProperty	property	=	null;
	private static Properties prop =	null;
	
			
	private AppProperty(){
		propertyKeyVal	=	new HashMap<String,String>();
	}

	public String getProperty(String key,String defaultValue) throws Exception{
		String value	=	null;
		String override	=	null;
		
		if(key!=null){
			override	=	System.getProperty(key);
			if(override==null || override.equals("")){
				value	=	prop.getProperty(key);
				if(value==null){
					value	=	defaultValue;
				}
			}
		} else {
			return defaultValue;
			//BaseExceptionManager.throwFatalException(new Exception(), "Not found","EN", 1, "Not fond", 1);
		}
		
		return value;
	}

	public String getUILabel(String key) throws Exception{
		String value	=	null;
		if(key!=null){
			value	=	prop.getProperty(key);
			if(value==null){
				value	=	key;
			}
		} else {
			return key;
		}
		
		return value;
	}

	
	public static AppProperty getInstance(){
		
		if(property==null){
			property	=	new AppProperty();
			load();
		}
		
		return property;
	}
	
	private static void load(){
		prop = new Properties();
    	InputStream input = null;
    	String filename = null;
    	File confFile	=	null;
    	File fielFromSysProperty	=	null;
		try{
			filename	=	System.getProperty("appconfig.properties");
			System.out.println("Reading from appconfig.");
			confFile	=	new File(filename);
			input	=	new FileInputStream(confFile);
			//System.out.println("\n Loading Property file named "+filename);
			prop.load(input);

		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	//GRPAH_INVERTER_DCVOLATE
	
	public static void main(String str[]){
		AppProperty p	=	AppProperty.getInstance();
		try{
			String v	=	p.getProperty("STRIPE_SK_VALUE", "Pie");
			System.out.println("Testing "+v);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	
	public static final String MAILSMTPAUTH	=	"MAILSMTPAUTH";
	public static final String MAILSMTPSTARTTLSENABLE	=	"MAILSMTPSTARTTLSENABLE";
	public static final String MAILSMTPHOST	=	"MAILSMTPHOST";
	public static final String MAILSMTPPORT	=	"MAILSMTPPORT";
	public static final String MAILSENDEREMAILID	=	"MAILSENDEREMAILID";
	public static final String MAILSENDERPASSWORD	=	"MAILSENDERPASSWORD";
}
