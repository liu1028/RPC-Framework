package org.eagle.protocol.api;

/**
 * @author lhs
 * @Time 2017-03-31 01:04:50
 * @Description RPC调用的响应
 */
public interface Response {

	/**
	 * @description 获取这个请求-响应的唯一标识符
	 */
	String getTag(); 
	
	/**
	 * @description 获取调用的状态码
	 */
	String getStatus();
	
	/**
	 * @description 获取调用结果
	 */
	Object getResult();
	
}
