package org.eagle.boot;

import org.eagle.boot.api.Animal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientBoot {

	public static void main(String[] args) {
		
		ApplicationContext context=new ClassPathXmlApplicationContext("eagle-client-spring.xml");
		
		Animal animal=(Animal)context.getBean("animal");
		
		animal.saySomething("this is the first test");
	}
}
