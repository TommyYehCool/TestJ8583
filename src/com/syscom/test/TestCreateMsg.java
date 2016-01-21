package com.syscom.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.parse.ConfigParser;

public class TestCreateMsg {
	
	private final static Logger log = LoggerFactory.getLogger("com.syscom.test");
	
	private MessageFactory<IsoMessage> mf = new MessageFactory<IsoMessage>();
	
	private void start() throws IOException {
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
		String j8583Config = "./config/j8583-config.xml";
		
		ConfigParser.configureFromUrl(mf, new File(j8583Config).toURI().toURL());
		
		log.info("Load config succeed, path: [{}]", j8583Config);
	}

	private void testing() {
		IsoMessage isoMsg = mf.newMessage(0x200);
		isoMsg.setCharacterEncoding("UTF-8");
		
		boolean hasField3 = isoMsg.hasField(3);
		log.info("0200 has field 3: " + hasField3);
		
		IsoType field3Type = isoMsg.getField(3).getType();
		log.info("0200 field 3 IsoType: " + field3Type);
		
		IsoType field32Type = isoMsg.getField(32).getType();
		log.info("0200 field 32 IsoType: " + field32Type);
		
		boolean hasField4 = isoMsg.hasField(4);
		log.info("0200 has field 4: " + hasField4);
		
		byte[] data0200 = isoMsg.writeData();
		log.info("0200 data: {}", new String(data0200));
	}

	public static void main(String[] args) throws IOException {
		TestCreateMsg test = new TestCreateMsg();
		test.start();
	}

}
