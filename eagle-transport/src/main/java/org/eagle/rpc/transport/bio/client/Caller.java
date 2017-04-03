package org.eagle.rpc.transport.bio.client;

import org.eagle.protocol.api.Request;
import org.eagle.protocol.api.Response;

/**
 * @author lhs
 * @Time 2017-04-03 16:30:49
 * @Description 调用实体，每个发起的调用都有一个caller实体 
 */
public class Caller {

	private boolean done=false;
	
	private String host;
	
	private int port;
	
	private Request request;
	
	private Response result;

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Response getResult() {
		return result;
	}

	public void setResult(Response result) {
		this.result = result;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
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
}
