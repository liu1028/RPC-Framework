package org.eagle.rpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.eagle.rpc.service.AnimalImpl;
import org.junit.Test;

public class testReflectionDirect {

	@Test
	public void testSimpleDemo() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		AnimalImpl impl=new AnimalImpl();
		
		Class<?> clz=impl.getClass();
		
		Method method=clz.getMethod("simple",int.class, double.class,float.class);
		
		method.invoke(impl, 4,0.234,0.2f);
		
	}
	
	@Test
	public void testSuperClass(){
		
		BigDecimal decimal=new BigDecimal("353453434234.45332");
		System.out.println(decimal);
	}
}
