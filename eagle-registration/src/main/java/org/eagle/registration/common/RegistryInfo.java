package org.eagle.registration.common;

import java.util.List;

/**
 * @author lhs
 * @Time 2017-04-09 22:34:12
 * @Description 向ZK注册的元数据
 */
public class RegistryInfo {

	//服务名称(zk路径)
	private String service;
	
	//服务主机ip
	private String host;
	
	//服务主机端口
	private Integer port;
	
	//服务启动方名称(用于路由)
	private String callee;
	
	//服务暴露的API列表
	private List<String> apis;
	
	//权重。服务器性能越好，权重越大
	private Integer weight;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getCallee() {
		return callee;
	}

	public void setCallee(String callee) {
		this.callee = callee;
	}

	public List<String> getApis() {
		return apis;
	}

	public void setApis(List<String> apis) {
		this.apis = apis;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}
