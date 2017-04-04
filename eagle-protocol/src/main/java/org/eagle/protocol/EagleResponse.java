package org.eagle.protocol;

import org.eagle.protocol.api.Response;

public class EagleResponse implements Response{

	private String tag;
	
	private String status;
	
	private Object result;
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag){
		this.tag=tag;
	}
	
}
