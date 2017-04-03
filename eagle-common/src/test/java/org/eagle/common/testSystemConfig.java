package org.eagle.common;


import org.eagle.common.util.SystemConfig;
import org.junit.Test;

public class testSystemConfig {

	@Test
	public void testSimple(){
		System.out.println(SystemConfig.getEnvVariable(Constants.JAVA_SERVICE_PORT));
		System.out.println(SystemConfig.getEnvVariable(Constants.ZOOKEEPER_IP_PORT));
		System.out.println(SystemConfig.getNativeVariable(Constants.REGISTER_IP));
		System.out.println(SystemConfig.getNativeVariable(Constants.JAVA_SERVICE_PORT));
	}
	
	@Test
	public void testConventionThanConfig(){
		System.out.println(SystemConfig.getNativeThanEnv(Constants.JAVA_SERVICE_PORT));
	}
}
