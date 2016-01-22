package com.syscom.test;

public class ConvertUtil {
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static byte[] convertIntTo2Byte(int msgLen) {
		byte[] bRet = new byte[2];
        for (int i = 0; i < 2; i++) {
        	bRet[1 - i] = (byte)(msgLen >> (i * 8));
        }
        return bRet;
    }
	
	public static int convert2ByteToInt(byte[] bMsgLen) {
		return ((bMsgLen[0] & 0xff) << 8) | (bMsgLen[1] & 0xff);
	}
	
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

	public static void main(String[] args) {
		int msgLen = 91;
		
		System.out.println("Message length: " + 91);
		
		byte[] bLens = convertIntTo2Byte(msgLen);
		
		System.out.println("Convert int to 2 byte hex string: " + bytesToHex(bLens));
		
		System.out.println("Convert 2 byte to int: " + convert2ByteToInt(bLens));
	}
}
