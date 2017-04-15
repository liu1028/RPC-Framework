package org.eagle.boot;

import javax.annotation.Resource;

import org.eagle.boot.api.ICallMyApple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:eagle-server-spring.xml")
public class TestClient {

	@Resource
	private ICallMyApple callMyApple;
	
	@Test
	public void launchClient(){
		
		String str=callMyApple.action(4);
		
		System.out.println(str);
	}
}
