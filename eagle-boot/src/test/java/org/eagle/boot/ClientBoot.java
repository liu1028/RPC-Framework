package org.eagle.boot;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eagle.boot.api.Animal;
import org.eagle.boot.api.Apple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientBoot {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("eagle-client-spring.xml");

		Animal animal = (Animal) context.getBean("animal");

		Set<Apple> apples = new HashSet<>();
		apples.add(new Apple());
		Apple a = new Apple();
		a.setAge(23232);
		a.setName("senix");
		apples.add(a);

		Set<Apple> apples2 = new HashSet<>();
		apples2.add(new Apple());
		Apple a2 = new Apple();
		a2.setAge(999);
		a2.setName("dfafdsaf");
		apples.add(a2);

		List<Set<Apple>> li = new ArrayList<>();
		li.add(apples);
		li.add(apples2);

		List<String> strs = Arrays.asList("ffree", "344jf", "ter");
		Set<Short> shorts = new TreeSet(Arrays.asList(45, 6, 76, 2, 65, 8));

		Map<String, Apple> maps = new HashMap<>();
		maps.put("map1", a);
		maps.put("map2", a2);

		String r=animal.argsHaveList(li, strs, shorts, maps);
		System.out.println(r);
		
		String str=animal.echo("hello", 23, 0.34, 0.31f, true, (short)23, (byte) 2);
		System.out.println(str);
		
		Calendar cal=Calendar.getInstance();
		Date date1=cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -20);
		Date date2=cal.getTime();
		BigInteger inte=new BigInteger("11111222222244444");
		BigDecimal decimal=new BigDecimal("353453434234.45332");
		
		String ss=animal.specialPrimitive(date1, date2, inte, decimal);
		System.out.println(ss);
	}
}
