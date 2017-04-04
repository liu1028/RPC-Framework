package org.eagle.rpc.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eagle.common.annotation.API;
import org.eagle.common.annotation.Arg;

public interface Animal {

	@API("saySomething")
	void saySomething(@Arg("content") String content);
	
	@API("echo")
	String echo(@Arg("content") String content,@Arg("age") int age,
			@Arg("dd") double d,@Arg("ff")float f,
			 @Arg("bol") boolean b,@Arg("short") short s,@Arg("by") byte by);
	
	@API("animal.getApple")
	List<Apple> getApple(@Arg("app") Apple app,@Arg("addr") String address,@Arg("age") int age);
	
	String simple(int age,double d,float f);
	
	//参数中含有map或者是List
	@API("argsHaveList")
	String argsHaveList(@Arg("list1") List<Set<Apple>> apples,@Arg("list2") List<String> strs,
			@Arg("list3") Set<Short> shorts,@Arg("map") Map<String,Apple> maps);
	
	@API("specialPrimitive")
	String specialPrimitive(@Arg("datelong") Date date,@Arg("dateStr") Date date2,
			@Arg("bigInteger") BigInteger bigInt,@Arg("decimal") BigDecimal decimal);
}
