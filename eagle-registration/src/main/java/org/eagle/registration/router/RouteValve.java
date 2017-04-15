package org.eagle.registration.router;

public class RouteValve implements Valve {

	private Valve next;
	
	private String url;
	
	private String api;
	
	@Override
	public String handle(String match) {
		if(api.equalsIgnoreCase(match)){
			return url;
		}else{
			return next.handle(match);
		}
	}

	@Override
	public void setNext(Valve valve) {
		this.next=valve;
	}

	public void setCalleeAndMethod(String url,String api){
		this.url=url;
		this.api=api;
	}
}
