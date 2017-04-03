package org.eagle.core;

/**
 * @author lhs
 * @Time 2017-04-02 13:44:15
 * @Description 实例对象导出类，也就是将要公开的对象导出成服务里的实体域
 */
public class Exporter {

	private Class<?> interfaceClz;
	
	private Object obj;
	
	public Exporter(Class<?> interfaceClz,Object obj){
		this.interfaceClz=interfaceClz;
		this.obj=obj;
	}

	public Class<?> getInterfaceClz() {
		return interfaceClz;
	}

	public void setInterfaceClz(Class<?> interfaceClz) {
		this.interfaceClz = interfaceClz;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
