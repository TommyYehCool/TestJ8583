package com.syscom.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoValue;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;

public class TestParser {
	
	private final static Logger log = LoggerFactory.getLogger("com.syscom.test");
	
	private MessageFactory<IsoMessage> mf = new MessageFactory<IsoMessage>(); 
	
	private BufferedReader reader;

	private void start() throws IOException, ParseException {
		loadLog4jConfig();
		
		loadJ8583Config();
		
		testing();
	}

	private void loadLog4jConfig() {
		String log4jConfig = "./config/log4j.properties";

		PropertyConfigurator.configure(log4jConfig);
		
		log.info("Load log4j config succeed, path: [{}]", log4jConfig);
	}

	private void loadJ8583Config() throws IOException, MalformedURLException {
		String config = "./config/j8583-config.xml";
		
		ConfigParser.configureFromUrl(mf, new File(config).toURI().toURL());
		
		log.info("Load config succeed, path: [{}]", config);
	}
	
	private String getMessage() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        System.out.println("Paste your ISO8583 message here (no ISO headers): ");
        return reader.readLine();
    }
	
	private void testing() throws IOException, ParseException, UnsupportedEncodingException {
		String header0200 = mf.getIsoHeader(0x0200);
		System.out.println("Test create 0200 ISO Header: " + header0200);
	
		String header0210 = mf.getIsoHeader(0x0210);
		System.out.println("Test create 0210 ISO Header: " + header0210);
		
		String header0400 = mf.getIsoHeader(0x0400);
		System.out.println("Test create 0400 ISO Header: " + header0400);
		
		String line = getMessage();
		
		IsoMessage msg = null;
		if (line != null && line.length() > 0) {
	        msg = mf.parseMessage(line.getBytes(), 0);
	        if (msg != null) {
	            System.out.printf("\nMessage type: %04x%n", msg.getType());
	            System.out.println("FIELD TYPE    VALUE");
	            for (int i = 2; i <= 128; i++) {
	                IsoValue<?> f = msg.getField(i);
	                if (f != null) {
	                    System.out.printf("%5d %-6s [", i, f.getType());
	                    System.out.print(f.toString());
	                    System.out.println(']');
	                }
	            }
	            
	            System.out.println("\nTry to get field 3:");
	    		System.out.println(msg.getObjectValue(3));
	    		
	    		System.out.println("\nTry to get field 48:");
	    		System.out.println(msg.getObjectValue(48));
	        }
	    }
	}

	public static void main(String[] args) throws IOException, ParseException {
		new TestParser().start();
	}

}
