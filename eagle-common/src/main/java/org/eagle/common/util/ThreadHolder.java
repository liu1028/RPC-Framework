package org.eagle.common.util;

import java.lang.reflect.Method;

/**
 * @Time 2017-03-27 20:25:25
 * @Description 线程持有类
 */
public class ThreadHolder extends Thread{

	private Object obj;
	
	private String methodName;
	
	private Object[] args;
	
	public ThreadHolder(Object obj,String methodName) {
		this.obj=obj;
		this.methodName=methodName;
	}
	
	public ThreadHolder(Object obj,String methodName,Object ...args) {
		this.obj=obj;
		this.methodName=methodName;
		this.args=args;
	}
	
	
	@Override
	public void run() {
		Class<?> clz=obj.getClass();
		
		/*
		 * 通过反射执行方法。避免Runnable侵入到各种线程执行类中
		 */
		try {
			Method method=clz.getMethod(methodName);
			if(args==null){
				method.invoke(obj);
			}else{
				method.invoke(obj,args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
