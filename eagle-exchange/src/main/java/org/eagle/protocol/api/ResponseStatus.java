package org.eagle.protocol.api;

public enum ResponseStatus {

	SUCCESS("200"),
	FAILURE("500");
	
	private String statusCode;
	private ResponseStatus(String statusCode){
		this.statusCode=statusCode;
	}
	public String getStatusCode() {
		return statusCode;
	}
	
}
