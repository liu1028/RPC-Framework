package org.eagle.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lhs
 * @Time 2017-03-26 22:37:02
 * @Description 系统配置类，从环境变量和Java系统变量中获取值。
 *                   
 */
public class SystemConfig {
	
	/**
	 * @description   [**约定]：当两个环境变量名字相同发生冲突时，Java系统变量的优先级 >> 环境变量
	 */
	public static String getNativeThanEnv(String key){
		String env=getEnvVariable(key);
		String nativ=getNativeVariable(key);
		
		if(StringUtils.isBlank(nativ)){
			return env;
		}else{
			return nativ;
		}
	}

	/**
	 * @description 获取环境变量
	 */
	public static String getEnvVariable(String key){
		return System.getenv(key);
	}
	
	/**
	 * @description 获取系统变量
	 */
	public static String getNativeVariable(String key){
		return System.getProperty(key);
	}
	
	public static void main(String[] args) {
		String port=getEnvVariable("java_service_port");
		System.out.println(port);
	}
}
