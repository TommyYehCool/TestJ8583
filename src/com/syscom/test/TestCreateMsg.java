package com.syscom.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;
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
		IsoMessage isoMsg = mf.newMessage(0x0200);
		isoMsg.setCharacterEncoding("UTF-8");
		
		boolean hasField3 = isoMsg.hasField(3);
		log.info("0200 has field 3: {}", hasField3);
		
		IsoType field3Type = isoMsg.getField(3).getType();
		log.info("0200 field 3 IsoType: {}", field3Type);
		
		boolean hasField32 = isoMsg.hasField(32);
		log.info("0200 has field 32: {}", hasField32);
		
		IsoValue<Object> field32 = isoMsg.getField(32);
		
		IsoType field32Type = field32.getType();

		log.info("0200 field 32 IsoType: {}", field32Type);
		
		log.info("0200 field 32 original value: {}", field32.getValue());
		
		String test32Val = "Jeff handsome";
		IsoValue<Object> val32 = new IsoValue<Object>(field32Type, test32Val);
		isoMsg.putAt(32, val32);
		
		field32 = isoMsg.getField(32);
		
		log.info("0200 field 32 new value: {}", field32.getValue());
		
		boolean hasField4 = isoMsg.hasField(4);
		log.info("0200 has field 4: {}", hasField4);
		
		IsoValue<Object> field4 = isoMsg.getField(4);
		log.info("0200 field 4: {}", field4);
		
		byte[] data0200 = isoMsg.writeData();
		log.info("0200 data: {}", new String(data0200));
	}

	public static void main(String[] args) throws IOException {
		TestCreateMsg test = new TestCreateMsg();
		test.start();
	}

}
