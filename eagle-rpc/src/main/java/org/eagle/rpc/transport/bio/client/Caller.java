package org.eagle.rpc.transport.bio.client;

import java.lang.reflect.Method;

import org.eagle.protocol.api.Request;
import org.eagle.protocol.api.Response;

/**
 * @author lhs
 * @Time 2017-04-03 16:30:49
 * @Description 调用实体，每个发起的调用都有一个caller实体 
 */
public class Caller{

	private boolean done=false;
	
	private String serviceName;
	
	private String host;
	
	private int port;
	
	private Method method;
	
	private Object[] args;
	
	private Request request;
	
	private byte[] response;

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}


	public byte[] getResponse() {
		return response;
	}

	public void setResponse(byte[] response) {
		this.response = response;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
