package org.eagle.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerBoot {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
	
		new ClassPathXmlApplicationContext("eagle-server-spring.xml");
	
	}
	
}
