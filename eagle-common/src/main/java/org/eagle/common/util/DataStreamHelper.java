package org.eagle.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class DataStreamHelper {

	private static final int ARRAY_CAPACITY=1024;
	
	private static final Charset CHARSET=Charset.forName("UTF-8");
	
	public static byte[] output2Bytes(InputStream in,ByteArrayOutputStream out) throws IOException{
		
		byte[] bytes=new byte[ARRAY_CAPACITY];
		int len;
		
		while((len=in.read(bytes))!=-1){
			out.write(bytes, 0, len);
		}
		
		return out.toByteArray();
	}
	
	public static String output2LittleBytesAndGC(InputStream in,int byteArrLength){
		
		byte[] bytes=new byte[byteArrLength];
		int len=0;
		try {
			len=in.read(bytes);
			String content=new String(bytes,0,len,CHARSET);
			return content;
		} catch (IOException e) {
			e.printStackTrace();		
			return null;
		}finally{
			try {
				bytes=null;
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
