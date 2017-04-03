package org.eagle.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataStreamHelper {

	private static final int ARRAY_CAPACITY=1024;
	
	public static byte[] output2Bytes(InputStream in,ByteArrayOutputStream out) throws IOException{
		
		byte[] bytes=new byte[ARRAY_CAPACITY];
		int len;
		
		while((len=in.read(bytes))!=-1){
			out.write(bytes, 0, len);
		}
		
		return out.toByteArray();
	}
}
