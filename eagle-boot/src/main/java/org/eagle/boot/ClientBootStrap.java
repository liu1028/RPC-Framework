package org.eagle.boot;

import java.util.List;

import org.eagle.rpc.transport.bio.client.CallerInvoker;

public class ClientBootStrap {

	private List<String> serviceNames;
	
	private String zkAddr;
	
	public ClientBootStrap(){
		
	}
	
	public void init(){
		
		initZkClient();
		
		initInvoker();
	}
	
	private void initZkClient() {
		
		serviceNames.forEach(System.out::println);
		
		System.out.println(zkAddr);
	}

	public void initInvoker(){
		CallerInvoker callerInvoker=CallerInvoker.getInstance();
		
		callerInvoker.init();
		
		callerInvoker.start();
	}

	public List<String> getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}

	public String getZkAddr() {
		return zkAddr;
	}

	public void setZkAddr(String zkAddr) {
		this.zkAddr = zkAddr;
	}
	
}
