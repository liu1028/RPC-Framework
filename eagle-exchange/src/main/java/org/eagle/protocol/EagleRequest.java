package org.eagle.protocol;

import java.lang.reflect.Type;

import org.eagle.common.exception.RPCException;
import org.eagle.common.util.TypeConverter;
import org.eagle.protocol.api.Request;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lhs
 * @Time 2017-03-27 21:49:10
 * @Description 请求协议格式
 *
 */
public class EagleRequest implements Request{

	private Header header;
	
	private JSONObject body;
	
	public class Header{
		private String service;
		private String method;
		private String tag;
		
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public String getService() {
			return service;
		}
		public void setService(String service) {
			this.service = service;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	
	public JSONObject getBody() {
		return body;
	}

	public void setBody(JSONObject body) {
		this.body = body;
	}
	
	public Object getArgument(String argsName,Type type) throws RPCException{
		
		Object val=body.get(argsName);
		
		return TypeConverter.compatible2JavaObject(val, type);
//		return val;
	}
}
