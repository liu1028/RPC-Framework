package org.eagle.proxy;

import java.lang.reflect.Proxy;

import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;

/**
 * @author lhs
 * @Time 2017-04-04 10:11:21
 * @Description 客户端的代理工厂，产生代理类
 */
public class ProxyFactory {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(Class<T> interfaceClz,String serviceName) throws RPCException{
		if(!interfaceClz.isInterface()){
			throw new  RPCException(RPCExcptionStatus.CLIENT_CALL_NOT_INTERFACE);
		}
		
		ClientBroker broker=new ClientBroker(serviceName);
		
		return (T)Proxy.newProxyInstance(interfaceClz.getClassLoader(),
				new Class<?>[]{interfaceClz}, broker);
		
	}
}
