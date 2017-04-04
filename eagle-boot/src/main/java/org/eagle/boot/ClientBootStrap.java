package org.eagle.boot;

import org.eagle.rpc.transport.bio.client.CallerInvoker;

public class ClientBootStrap {

	public ClientBootStrap(){
		
	}
	
	public void init(){
		CallerInvoker callerInvoker=CallerInvoker.getInstance();
		
		callerInvoker.init();
		
		callerInvoker.start();
	}
}
