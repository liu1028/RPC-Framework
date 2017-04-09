package org.eagle.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.eagle.common.exception.RPCException;
import org.eagle.rpc.transport.bio.client.Caller;
import org.eagle.rpc.transport.bio.client.CallerInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientBroker implements InvocationHandler{

	private Logger logger=LoggerFactory.getLogger(ClientBroker.class);
	
	//TODO：通过zk获取host+port.zkClient也必须是单例的（初始化上移到bootstrap中）

	
	private String serviceName;
	
	public ClientBroker(String serviceName) {
		this.serviceName=serviceName;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		 //TODO:这里获取zk单例，根据服务名读取具体的host+port
		
		
		//当前假设已经读到host，port
		String host="192.168.31.3";
		int port=40001;
		
		//一个具体的调用实体类，组合了request，response
		Caller caller=new Caller();
		caller.setHost(host);
		caller.setPort(port);
		caller.setMethod(method);
		caller.setArgs(args);
		caller.setServiceName(serviceName);
		
		try{
			CallerInvoker callerInvoker=CallerInvoker.getInstance();
			
			//真正的调用逻辑
			Object result=callerInvoker.invoke(caller);
			
			return result;
		}catch(RPCException ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}


}
