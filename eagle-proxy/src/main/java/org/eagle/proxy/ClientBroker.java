package org.eagle.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.eagle.common.exception.RPCException;
import org.eagle.registration.ZKDiscovery;
import org.eagle.registration.router.Valve;
import org.eagle.rpc.transport.bio.client.Caller;
import org.eagle.rpc.transport.bio.client.CallerInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

public class ClientBroker implements InvocationHandler{

	private Logger logger=LoggerFactory.getLogger(ClientBroker.class);
	
	//获取ZK单例
	private ZKDiscovery zkDiscovery=ZKDiscovery.getInstance();
	
	private String serviceName;
	
	public ClientBroker(String serviceName) {
		this.serviceName=serviceName;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		 //获取路由匹配责任链，返回对应的url
		Valve valve=zkDiscovery.getValve(serviceName);
		if(valve==null){
			throw new RPCException("没有找到当前可用实例！");
		}
		
		String url=valve.handle(method.getName());
		
		Splitter splitter=Splitter.on(":").trimResults();
		Iterable<String> iter=splitter.split(url);
		Iterator<String> iterator=iter.iterator();
		
		String host=iterator.next();
		int port=Integer.valueOf(iterator.next());
		
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
