package org.eagle.core.api;

import java.lang.reflect.Method;

/**
 * @author lhs
 * @Time 2017-04-02 11:23:56
 * @Description 这个工厂维护了从apiName到反射类method的映射
 */
public interface MethodFactory {

	/**
	 * @description 根据apiName返回method类。
	 *  如果没有，则返回null；
	 */
	Method getMethod(String apiName);
	
	/**
	 * @description 添加一个方法到工厂
	 * 如果工厂中有apiName的映射，则会被覆盖
	 */
	void addMethod(String apiName,Method method);
	
	/**
	 * @description 删除一个方法从工厂
	 * 如果工厂没有，则返回false；成功返回true
	 */
	boolean removeMethod(String apiName,Method method);
	
}
