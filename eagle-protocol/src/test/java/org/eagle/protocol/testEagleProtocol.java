package org.eagle.protocol;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;

public class testEagleProtocol {

	@Test
	public void testEagleRequest(){
		String raw=   "     {"
				        + "        \"tag\":\"34345g4g4554\",             "
				        + "        \"s\":\"/biz/app/wkf/\",                  "
				        + "        \"m\":\"someMethod\",                  "
				        + "        \"args\":{                                    "
				        + "             \"list\":[4,5,6,7,8],                   "
				        + "             \"age\"34,                               "
				        + "             \"isSuccess\"true                      "
				        + "          }"
				        + "     }                                                    ";
		
		
		System.out.println(raw);
		 
	
	}
	
	@Test
	public void tesJSONObject(){
		
		/*if(int.class instanceof Class){
			System.out.println("int.class 是class");
		}
		*/
	
		
		String str="{\"age\":[4,3,6,98]}";
		Object obj=JSONObject.parse(str);
	/*	if(obj instanceof JSONObject){
			JSONObject o=((JSONObject)obj);
			A a=o.toJavaObject(A.class);
			System.out.println(a);
		}*/
		
		if(obj instanceof JSONObject){
			Object o=((JSONObject)obj).get("age");
			if(o instanceof JSONObject){
				System.out.println();
			}else if(o instanceof JSONArray){
				JSONArray arr=(JSONArray)o;
				List<Object> list=arr.toJavaList(Object.class);
				System.out.println(list);
			}else if(o instanceof Boolean){
				System.out.println("bool");
			}
			//	System.out.println(o);
		}else if(obj instanceof JSONArray){
			
		}
	}
	

}

@Data
class A{
	Set<Integer> age;
}
