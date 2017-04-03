package org.eagle.rpc.api;

/**
 * @author lhs
 * @Time 2017-03-29 22:51:48
 * @Description 处理器接口
 */
public interface Processor {
	
	/**
	 * @description execute执行方法，进行解耦
	 */
	void process();
}
