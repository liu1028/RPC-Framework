package org.eagle.registration.router;

public class All2OneValve implements Valve{

	private String url;
	
	@Override
	public String handle(String matcher) {
		return url;
	}

	@Override
	public void setNext(Valve valve) {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
