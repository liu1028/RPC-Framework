package org.eagle.rpc.api;

import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.protocol.api.Response;
import org.eagle.rpc.transport.bio.client.Caller;
import org.eagle.rpc.transport.bio.client.ClientEndPoint;

/**
 * @author lhs
 * @Time 2017-04-04 09:06:41
 * @Description 该类作用：获取host和port；拼装request请求
 */
public abstract class AbstractCallerInvoker implements Lifecycle,Invoker{
	
	private ClientEndPoint endPoint;
	
	@Override
	public void init() {
		endPoint=ClientEndPoint.getInstance();
		initInternal();
	}

	protected abstract void initInternal();

	@Override
	public void start() {
		endPoint.init();
	}
	
	/**
	 * @throws RPCException 
	 * @description caller的通用逻辑：
	 * 		1.拼装request（具体实现交由子类来做，因为不同协议有不同的请求格式）
	 *     2.发送请求消息
	 *     3.等待响应。（利用锁机制保证在并发情况下，响应能正确送达发送该请求的线程）
	 *     4.根据响应状态：1)调用成功，直接返回result; 2)调用失败，向上抛出异常
	 */
	public Object invoke(Caller caller) throws RPCException{
		
		buildRequest(caller);

		addCaller2Pool(caller);
	
		byte[] req=encodeRequest();
		
		endPoint.sendMessage(caller.getHost(),caller.getPort(),req);
		
		synchronized (caller) {
			//等待服务器端发送消息过来，才唤醒
			while(!caller.isDone()){
				try {
					caller.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Object result=dealResponse(caller); 
		
		return result;
	}
	
	protected abstract byte[] encodeRequest();

	protected abstract void buildRequest(Caller caller) throws RPCException;
	
	protected abstract Object dealResponse(Caller caller) throws RPCException;

	protected abstract void addCaller2Pool(Caller caller);
}
