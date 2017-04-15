package org.eagle.boot;

import java.util.List;

import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.registration.ZKDiscovery;
import org.eagle.rpc.transport.bio.client.CallerInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientBootStrap {

	private static Logger logger=LoggerFactory.getLogger(ClientBootStrap.class);
	
	private List<String> serviceNames;
	
	private String zkAddr;
	
	public ClientBootStrap(){
		
	}
	
	public void init() throws RPCException{
		logger.info("初始化zkClient。。。");
		initZkClient();
		
		logger.info("初始化callerInvoker");
		initInvoker();
	}
	
	private void initZkClient() throws RPCException {
		
		ZKDiscovery zkDiscovery=ZKDiscovery.getInstance();
		
		try{
			zkDiscovery.initZK(zkAddr);
		}catch(Exception ex){
			throw new RPCException(RPCExcptionStatus.CLIENT_CONNECT_SERVER_FAILURE);
		}
		
		zkDiscovery.discover(serviceNames);
		
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
