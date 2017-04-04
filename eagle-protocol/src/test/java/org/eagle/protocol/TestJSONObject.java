package org.eagle.protocol;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class TestJSONObject {

	@Test
	public void testJSON(){
		String str="{\"age\":34,\"name\":\"dsaf\"}";
		
		JSONObject jsonObj=JSONObject.parseObject(str);
		Object age=jsonObj.get("age");
		if(age instanceof JSONObject){
			System.out.println("jsonobject");
		}else if(age instanceof Integer){
			System.out.println("integer");
		}
		
	}
	
	@Test
	public void testEnheritance(){
		B b=new B();
		b.a=23;
		b.b="yes";
		b.c=555;
		A a=b;
		System.out.println(JSONObject.toJSONString(a));
	}
	
	
	class A{
		public int a;
		public String b;
	}
	
	class B extends A{
		public int c;
	}
}
