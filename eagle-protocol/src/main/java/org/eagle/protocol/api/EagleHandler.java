package org.eagle.protocol.api;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lhs
 * @Time 2017-03-27 20:48:41
 * @Description 自定义协议
 */
public interface EagleHandler extends ProtocolHandler{
	
	/**
	 * @description 解析请求协议头部 
	 */
	void parseHeader4Req(JSONObject mesg);
	
	/**
	 * @description 解析请求协议体
	 */
	void parseBody4Req(JSONObject mesg);
	
	/**
	 * @description 解析响应内容
	 */
	void parseResponse(JSONObject mesg);
}
