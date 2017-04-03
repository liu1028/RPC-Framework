package org.eagle.core;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.eagle.common.api.Lifecycle;
import org.eagle.core.api.BeanFactory;
import org.eagle.core.api.MethodFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lhs
 * @Time 2017-04-02 13:15:58
 * @Description 核心实体域工厂，用于存放实例和方法反射类
 */
public class ReferObjContainer implements BeanFactory,MethodFactory,Lifecycle{

	private Logger logger=LoggerFactory.getLogger(ReferObjContainer.class);
	
	private ConcurrentHashMap<String, Object> instanceMap;
	
	private ConcurrentHashMap<String, Method> methodMap;
	
	public void init() {
		instanceMap=new ConcurrentHashMap<String, Object>();
		methodMap=new ConcurrentHashMap<String, Method>();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(String apiName, Class<T> clz) {
		Object obj=getBean(apiName);
		
		if(clz.isAssignableFrom(obj.getClass())){
			//执行到此，表示T至少是obj.Class的同类或父类
			return (T)obj;
		}else{
			logger.error(obj.getClass().getName()+"不能转化为："+clz.getName());
			return null;
		}
	}
	
	public Object getBean(String apiName) {
		return instanceMap.get(apiName);
	}

	public Method getMethod(String apiName) {
		
		if(methodMap.containsKey(apiName)){
			return methodMap.get(apiName);
		}else{
			return null;
		}
	}		

	public void addMethod(String apiName, Method method) {
		methodMap.put(apiName, method);
	}

	public void addBean(String apiName, Object bean) {
		instanceMap.put(apiName, bean);
	}

	public boolean removeBean(String apiName) {
		Object obj=instanceMap.remove(apiName);
		if(obj!=null){
			return true;
		}
		
		return false;
	}

	public boolean removeMethod(String apiName, Method method) {
		Object obj=methodMap.remove(apiName);
		if(obj!=null){
			return true;
		}
		
		return false;
	}	

	public void start() {
		
	}

	public void destroy() {
		/**
		 * 清空容器中的所有对象，并将容器置为空，便于GC回收空间
		 */
		instanceMap.clear();
		methodMap.clear();
		
		instanceMap=null;
		methodMap=null;
	}
}
