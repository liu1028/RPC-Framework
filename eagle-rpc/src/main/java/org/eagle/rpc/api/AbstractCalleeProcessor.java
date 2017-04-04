package org.eagle.rpc.api;

import org.eagle.common.api.Lifecycle;
import org.eagle.protocol.api.ProtocolHandler;
import org.eagle.protocol.api.Request;
import org.eagle.protocol.api.Response;

public abstract class AbstractCalleeProcessor  implements Processor,Lifecycle{

	
	private ProtocolHandler protocolHandler;
	
	private byte[] binaryReq;
	
	private byte[] binaryResp;
	
	public AbstractCalleeProcessor(){
	}
	
	@Override
	public void init() {
		initInternal();
	}
	
	/**
	 * @description 提供给子类进行进行一些初始化操作
	 */
	protected abstract void initInternal();

	@Override
	public void process() {
		protocolHandler.decode4Request(binaryReq);
		Request request=protocolHandler.fetchRequest();
		Response response=protocolHandler.fetchResponse();
		processInternal(request,response);
		binaryResp=protocolHandler.encodeResponse(response);
	}
	
	/**
	 * @description 交给子类完成具体的请求响应操作
	 */
	protected abstract Response processInternal(Request request,Response response);
	
	
	public void forwardBinaryRequest(byte[] mesg){
		this.binaryReq=mesg;
	}
	
	public byte[] retrieveBinaryResponse(){
		return this.binaryResp;
	}
	
	public void setProtocolHandler(ProtocolHandler protocolHandler){
		this.protocolHandler=protocolHandler;
	}
	
	@Override
	public void start() {
	}
	
	@Override
	public void destroy() {

	}
}
