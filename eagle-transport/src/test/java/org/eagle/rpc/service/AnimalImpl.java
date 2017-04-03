package org.eagle.rpc.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eagle.rpc.service.Apple.B;

public class AnimalImpl implements Animal {

	@Override
	public void saySomething(String content) {
		System.out.println(content);
	}

	@Override
	public String echo(String content, int age, double d,float f, boolean b, short s, byte by) {
		String str=content+" "+age+" "+d+" "+f+b+" "+s+" "+by;
		System.out.println(str);
		return str;
	}

	@Override
	public List<Apple> getApple(Apple app,String address,int age) {
		System.out.println(app);
		System.out.println(address+" "+age);
		List<Apple> apples=new ArrayList<>();
		apples.add(new Apple());
		Apple a=new Apple();
		a.setAge(23232);
		a.setName("senix");
		apples.add(a);
		
		B b=app.getB().get(0);
		System.out.println(b);
		return apples;
	}

	@Override
	public String simple(int age, double d, float f) {
		String str=age+" "+d+" "+f;
		System.out.println(str);
		return str;
	}

	@Override
	public String argsHaveList(List<Set<Apple>> apples, List<String> strs, 
			Set<Short> shorts, Map<String,Apple> maps) {
		System.out.println("\n进入方法里面");
		System.out.println(apples+"\n"+strs+"\n"+shorts);
		System.out.println("出方法\n");
		
		for(Set<Apple> apps:apples){
			for(Apple app:apps){
				System.out.println(app);
			}
		}
		
		System.out.println("===========");
		
		for (String key : maps.keySet()) {
			System.out.println("key:"+key+"  val:"+(Apple)maps.get(key));
		}
		
		return null;
	}

	@Override
	public String specialPrimitive(Date date, Date date2,
			BigInteger bigInt, BigDecimal decimal) {
		System.out.println(date+"\n"+date2+"\n"+bigInt+"\n"+decimal);
		
		return null;
	}

}
