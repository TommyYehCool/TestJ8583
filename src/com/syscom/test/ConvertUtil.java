package com.syscom.test;

public class ConvertUtil {
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static byte[] convertIntTo2Bytes(int msgLen) {
		byte[] bRet = new byte[2];
        for (int i = 0; i < 2; i++) {
        	bRet[1 - i] = (byte)(msgLen >> (i * 8));
        }
        return bRet;
    }
	
	public static byte[] convertIntTo4Bytes(int msgLen) {
        byte[] bRet = new byte[4];
        for (int i = 0; i < 4; i++) {
            bRet[3 - i] = (byte)(msgLen >> (i * 8));
        }
        return bRet;
    }
	
	public static int convert2BytesToInt(byte[] bMsgLen) {
		return ((bMsgLen[0] & 0xff) << 8) | (bMsgLen[1] & 0xff);
	}
	
	public static int convert4BytesToInt(byte[] bMsgLen) {
		return ((bMsgLen[0] & 0xff) << 24) | ((bMsgLen[1] & 0xff) << 16) | 
				((bMsgLen[2] & 0xff) << 8) | (bMsgLen[3] & 0xff);
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
		
		System.out.println("Message length: " + msgLen);
		
		System.out.println("----------------------------------------------");
		
		byte[] b2BytesLens = convertIntTo2Bytes(msgLen);
		
		showBytesContents(b2BytesLens);
		
		System.out.println("Convert 2 bytes array to hex string: " + bytesToHex(b2BytesLens));
		
		System.out.println("Convert 2 bytes to int: " + convert2BytesToInt(b2BytesLens));
		
		byte[] b4BytesLens = convertIntTo4Bytes(msgLen);
		
		System.out.println("----------------------------------------------");
		
		showBytesContents(b4BytesLens);
		
		System.out.println("Convert 4 bytes array to hex string: " + bytesToHex(b4BytesLens));
		
		System.out.println("Convert 4 bytes to int: " + convert4BytesToInt(b4BytesLens));
	}

	private static void showBytesContents(byte[] bLens) {
		System.out.print("Bytes content: ");
		for (int i = 0; i < bLens.length; i++) {
			System.out.print(bLens[i] + " ");
		}
		System.out.println();
	}
}
