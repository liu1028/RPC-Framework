package org.eagle.registration.router;

/**
 * @author lhs
 * @Time 2017-04-10 23:04:28
 * @Description 阀，采用责任链的方式
 */
public interface Valve {

	/**
	 * @description 进行真正的逻辑判断处理，返回被调者
	 */
	String handle(String matcher);
	
	/**
	 * @description 设置下一个阀
	 */
	void setNext(Valve valve);
}
