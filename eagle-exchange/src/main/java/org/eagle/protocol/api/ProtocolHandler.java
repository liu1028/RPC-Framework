package org.eagle.protocol.api;

import org.eagle.common.exception.RPCException;

/**
 * @author lhs
 * @Time 2017-03-27 20:41:35
 * @Description 协议处理类接口
 */
public interface ProtocolHandler {
	
	/**
	 * @throws RPCException 
	 * @description 解码请求
	 */
	void decode4Request(byte[] mesg);
	
	/**
	 * @description 编码请求
	 */
	byte[] encodeRequest(Object req);
	
	/**
	 *  @description 编码响应 
	 */
	byte[] encodeResponse(Object resp);
	
	/**
	 * @description 解码响应
	 */
	void decode4Response(byte[] mesg);
	
	/**
	 * @description 获取解析组装成的request对象
	 */
	Request fetchRequest();
	
	/**
	 * @description 获取解析组装成的request对象
	 */
	Response fetchResponse();
}
