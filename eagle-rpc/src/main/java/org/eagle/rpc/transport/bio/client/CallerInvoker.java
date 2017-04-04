package org.eagle.rpc.transport.bio.client;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.common.util.TypeConverter;
import org.eagle.protocol.EagleProtoHandler;
import org.eagle.protocol.EagleRequest;
import org.eagle.protocol.api.ProtocolHandler;
import org.eagle.protocol.api.Response;
import org.eagle.protocol.api.ResponseStatus;
import org.eagle.rpc.api.AbstractCallerInvoker;
import org.eagle.rpc.api.Invoker;

/**
 * 
 * @author lhs
 * @Time 2017-04-04 10:02:23
 * @Description
 */
public class CallerInvoker extends AbstractCallerInvoker{
	
	private static CallerInvoker callerInvoker=new CallerInvoker();
	
	private CallerPool callerPool;

	//保证ProtocolHandler的线程安全
	private ThreadLocal<ProtocolHandler> protocolHandler;
	
	private CallerInvoker() {
	}
	
	@Override
	public void initInternal(){
		//单例模式,获取依赖
		callerPool=CallerPool.getInstance();
		protocolHandler=new ThreadLocal<>();
	}

	@Override
	protected void buildRequest(Caller caller) throws RPCException {
		EagleProtoHandler protoHandler=new EagleProtoHandler();
		protocolHandler.set(protoHandler);
		
		String serviceName=caller.getServiceName();
		Method method=caller.getMethod();
		Object[] args=caller.getArgs();
		
		//交给协议处理类来构建请求
		protoHandler.buildRequest(serviceName, method, args);
	}

	@Override
	protected void addCaller2Pool(Caller caller) {
		EagleProtoHandler protoHandler=(EagleProtoHandler)protocolHandler.get();
		EagleRequest request=(EagleRequest)protoHandler.fetchRequest();
		String remoteId=request.getHeader().getTag();
		
		callerPool.put(remoteId, caller);
	}
	
	@Override
	protected byte[] encodeRequest() {
		EagleProtoHandler protoHandler=(EagleProtoHandler)protocolHandler.get();
		return protoHandler.encodeRequest();
	}
	
	
	@Override
	protected Object dealResponse(Caller caller) throws RPCException {
		byte[] resp=caller.getResponse();
		EagleProtoHandler protoHandler=(EagleProtoHandler)protocolHandler.get();
		//构建Response类
		protoHandler.decode4Response(resp);
		//获取构建的Response
		Response response=protoHandler.fetchResponse();
		
		String success=ResponseStatus.SUCCESS.getStatusCode();
		String fail=ResponseStatus.FAILURE.getStatusCode();
		
		if(success.equalsIgnoreCase(response.getStatus())){
			
			Method method=caller.getMethod();
			Type type=method.getGenericReturnType();
			
			return TypeConverter.compatible2JavaObject(response.getResult(), type);
			
		}else if(fail.equalsIgnoreCase(response.getStatus())){
			Object r=response.getResult();
			if(r instanceof String)
				throw new RPCException((String)r);
			else
				throw new RPCException("不可预期的错误：远程调用失败，期待字符串型的错误描述！！");
		}else{
			throw new RPCException("不可预期的错误：远程调用失败，结果返回状态码不正确！！");
		}
	}
	
	
	public static CallerInvoker getInstance(){
		return callerInvoker;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
