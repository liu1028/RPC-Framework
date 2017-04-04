package org.eagle.rpc.transport.bio.client;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class ResponseDealer extends Thread{

	private RPCConnection conn;
	
	public ResponseDealer(RPCConnection conn) {
		this.conn=conn;
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
				
				if(caller!=null){
					caller.setDone(true);
					caller.notify();
					pool.remove(tag);
				}
				
			}
		}catch(IOException ex){
			
		}
	}
	
	private String getRemoteId(String json){
		return StringUtils.substringBetween(json, "tag\":\"", "\"");
	}
	

}
