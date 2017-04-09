package org.eagle.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eagle.common.exception.RPCException;
import org.eagle.core.Exporter;
import org.eagle.core.ReferObjContext;
import org.eagle.rpc.transport.bio.server.ServerEndPoint;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author lhs
 * @Time 2017-04-09 17:03:57
 * @Description  服务端启动类
 */
public class ServerBootStrap implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	private String zkAddress;
	
	private String servicePath;
	
	private Integer port;
	
	private String reportHost;

	public ServerBootStrap() {

	}

	public void init() throws RPCException {

		// 初始化服务端endpoint
		initServer();
		
		// 初始化实体域与服务域
		initReferObjContext();

		//初始化ZK
		initZookeeper();
		
		//上报ZK信息
		reportMe();
	}


	public void initServer() {
		// 初始化服务器连接
		ServerEndPoint server = new ServerEndPoint();
		server.init(port);
		server.start();
	}

	public void initReferObjContext() throws RPCException {
		ReferObjContext context = ReferObjContext.getInstance();
		context.init(getExporters());
	}
	
	private List<Exporter> getExporters() {
		Map<String, Exporter> map = applicationContext.getBeansOfType(Exporter.class);
		List<Exporter> exporters = new ArrayList<>();
		map.forEach((beanName, exporter) -> exporters.add(exporter));

		return exporters;
	}

	/**
	 * @description 初始化zookeeper
	 */
	private void initZookeeper() {
		// TODO 初始化zookeeper
		
	}

	/**
	 * @description 上报ZK服务端配置的元信息
	 */
	private void reportMe() {
		// TODO 上报ZK服务端配置的元信息
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public String getZkAddress() {
		return zkAddress;
	}

	public void setZkAddress(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getReportHost() {
		return reportHost;
	}

	public void setReportHost(String reportHost) {
		this.reportHost = reportHost;
	}
}
