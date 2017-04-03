package org.eagle.common.api;

import org.eagle.common.exception.RPCException;

/**
 * @author lhs
 * @Time 2017-03-26 21:38:18
 * @Description 生命周期接口，所有组件都会继承这个接口，来完成生命各阶段的操作。
 */
public interface Lifecycle {
	
	/**
	 * 执行生命周期的初始化操作
	 * @throws RPCException 
	 */
	void init();
	
	/**
	 * 执行生命周期的启动操作
	 */
	void start();
	
	/**
	 * 执行生命周期的销毁操作
	 */
	void destroy();
}
