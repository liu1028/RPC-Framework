package org.eagle.registration.common;

import com.alibaba.fastjson.JSON;

public class RegistryInfoParser {

	public static RegistryInfo parse(String content){
		JSON json=JSON.parseObject(content);
		
		RegistryInfo info=JSON.toJavaObject(json, RegistryInfo.class);
		
		return info;
	}
}
