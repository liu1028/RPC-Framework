package org.eagle.registration.router;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.registration.common.RegistryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.base.Splitter.MapSplitter;

public class ValveBuilder {
	private static final String routeFormatSplittor="=>";

	//private static final String lineSeparator = System.getProperty("line.separator", "\n"); //获取当前系统换行符
	private static final String separator=";";
	
	private static final String allFormat="all";
	
	private static Logger logger=LoggerFactory.getLogger(ValveBuilder.class);
	
	private String routeText;
	
	private List<RegistryInfo> infos;
	
	private Map<String, String> calleeMap; //callee->url
	
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
		
		Map<String,String> routeRules=parseRouteRaw(routeText);
		
		if(routeRules==null){
			DefaultValve valve=new DefaultValve();
			valve.setRegistries(infos);
			return valve;
		}
		
		Valve pre=null,first=null;
		for(String method:routeRules.keySet())
		{
			//如果有方法名为all，那么用All2OneValve，并且直接返回
			if(method.equalsIgnoreCase(allFormat))
			{
				String callee=routeRules.get(method);
				String url=calleeMap.get(callee);
				if(url!=null)
				{
					All2OneValve all=new All2OneValve();
					all.setUrl(url);
					if(first==null){
						first=all;
					}else{
						pre.setNext(all);
					}
					return first;
				}
			}
			
			String callee=routeRules.get(method);
			String url=calleeMap.get(callee);
			
			if(url!=null)
			{
				RouteValve valveIntern=new RouteValve();
				valveIntern.setCalleeAndMethod(url, method);
				if(first==null){
					first=pre=valveIntern;
				}else{
					pre.setNext(valveIntern);
					pre=valveIntern;
				}
			}
		}
		
		//最后加上默认的Valve，责任链不关乎前面有哪些结点，只需要往链上放，该是谁的责任谁就去处理
		DefaultValve valve=new DefaultValve();
		valve.setRegistries(infos);
		if(first==null){
			first=valve;
		}else{
			pre.setNext(valve);
		}
		
		return first;
	}
	
	private Map<String,String> parseRouteRaw(String content){
		if(StringUtils.isBlank(content)){
			return null;
		}
		
		try{
			MapSplitter mapSplitter=Splitter.on(separator).omitEmptyStrings().trimResults().withKeyValueSeparator(routeFormatSplittor);
			Map<String, String> routeMeta=mapSplitter.split(routeText);
			
			return routeMeta;
		}catch(Exception ex){
			logger.warn(RPCExcptionStatus.ROUTE_FILE_PARSE_FAILURE+"; 文件内容为："+content);
			
			return null;
		}
	}
	
	public static void main(String[] args) {
		MapSplitter mapSplitter=Splitter.on(separator).omitEmptyStrings().withKeyValueSeparator(routeFormatSplittor);
		Map<String, String> routeMeta=mapSplitter.split("method1=>cave\r\nmethod2=>senix\r\nmetd=>sa\r\nall=>dsfa");
		
		for(String key:routeMeta.keySet()){
			System.out.println(key+":"+routeMeta.get(key));
		}
		
	}
}
