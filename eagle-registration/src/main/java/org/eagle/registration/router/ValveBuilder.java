package org.eagle.registration.router;

import java.util.List;
import java.util.Map;

import org.eagle.registration.common.RegistryInfo;

public class ValveBuilder {

	private String routeText;
	
	private List<RegistryInfo> infos;
	
	private Map<String, String> calleeMap;
	
	private ValveBuilder(){
	}
	
	public static ValveBuilder newValveBuilder(){
		return new ValveBuilder();
	}
	
	public ValveBuilder takeRawRouteText(String content){
		routeText=content;
		return this;
	}
	
	public ValveBuilder takeRegInfo(List<RegistryInfo> rgs){
		infos=rgs;
		return this;
	}
	
	public ValveBuilder takeCallee2UrlMap(Map<String, String> map){
		calleeMap=map;
		return this;
	}
	
	public Valve buildValve(){
		//TODO 具体构建valve的过程
		
		return null;
	}
}
