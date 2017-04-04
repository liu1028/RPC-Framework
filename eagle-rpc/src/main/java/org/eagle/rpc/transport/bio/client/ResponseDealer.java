package org.eagle.rpc.transport.bio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

public class ResponseDealer extends Thread{

	private static final Charset CHARSET=Charset.forName("UTF-8"); 
	
	private RPCConnection conn;
	
	private String connKey;
	
	public ResponseDealer(RPCConnection conn,String connKey) {
		this.conn=conn;
		this.connKey=connKey;
	}
	
	@Override
	public void run() {
		
		BufferedReader reader=conn.getReader();
		CallerPool pool=CallerPool.getInstance();
		
		try{
			String response;
			while((response=reader.readLine())!=null){
				
				String tag=getRemoteId(response);
				
				Caller caller=pool.get(tag);
				
				caller.setResponse(response.getBytes(CHARSET));
				
				if(caller!=null){
					caller.setDone(true);
					synchronized (caller) {
						caller.notify();
					}
					pool.remove(tag);
				}
				
			}
		}catch(IOException  | IllegalMonitorStateException e){
			ConnectionPool connectionPool=ConnectionPool.getInstance();
			connectionPool.remove(connKey);
			conn.destroy();
			e.printStackTrace();
		}
	}
	
	private String getRemoteId(String json){
		return StringUtils.substringBetween(json, "tag\":\"", "\"");
	}
	

}
