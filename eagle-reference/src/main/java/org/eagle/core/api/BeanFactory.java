package org.eagle.core.api;

/**
 * @author lhs
 * @Time 2017-04-02 11:14:03
 * @Description bean工厂，维护实体域，作为服务域
 */
public interface BeanFactory {

	/**
	 * @description 根据api的名字，获取实例
	 * @return 如果没有，返回null
	 */
	Object getBean(String apiName);
	
	/**
	 * @description 根据API名字和类型，转换成对应的类型
	 * @return 如果转化失败，返回null
	 */
	<T> T getBean(String apiName,Class<T> clz);
	
	/**
	 * @description 添加一个bean实例
	 * @param 如果factory存在这个apiName，那么原来的被替换掉
	 */
	void addBean(String apiName,Object bean);
	
	/**
	 * @description 删除一个bean实体
	 * @Param 如果没有，返回false；成功，返回true
	 */
	boolean removeBean(String apiName);
}
