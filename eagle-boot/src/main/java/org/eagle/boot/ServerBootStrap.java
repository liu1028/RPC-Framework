package org.eagle.boot;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.common.util.DataStreamHelper;
import org.eagle.common.util.SequGenerator;
import org.eagle.core.Exporter;
import org.eagle.core.ReferObjContext;
import org.eagle.registration.ZKRegistry;
import org.eagle.registration.common.RegistryInfo;
import org.eagle.rpc.transport.bio.server.ServerEndPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lhs
 * @Time 2017-04-09 17:03:57
 * @Description  服务端启动类
 */
public class ServerBootStrap implements ApplicationContextAware {

	private static final Logger logger=LoggerFactory.getLogger(ServerBootStrap.class);
	
	private ApplicationContext applicationContext;
	
	private String zkAddress;
	
	private String servicePath;
	
	private String reportHost;
	
	private Integer port;
	
//	private String reportHost;
	
	private Integer weight;
	
	private ZKRegistry zkRegistry;

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
	 * @throws RPCException 
	 * @description 初始化zookeeper
	 */
	private void initZookeeper() throws RPCException {
		try{
			zkRegistry=new ZKRegistry(zkAddress);
		}catch(Exception ex){
			throw new RPCException(RPCExcptionStatus.SERVER_CONNECT_ZOOKEEPER_TIMEOUT);
		}
	}

	/**
	 * @throws RPCException 
	 * @throws UnknownHostException 
	 * @description 上报ZK服务端配置的元信息
	 */
	private static final String calleeFileName="/callee";
	private void reportMe() throws RPCException  {
		RegistryInfo info=new RegistryInfo();
		info.setService(servicePath);
/*		try{
			info.setHost(InetAddress.getLocalHost().getHostAddress());
		}catch(Exception e){
			throw new RPCException("不能获取本地ip地址！");
		}
		*/

		if(reportHost==null){
			throw new RPCException("必须给出本地上传ZK的本地联网IP地址！");
		}
		info.setHost(reportHost);
		info.setPort(port);
		info.setWeight(weight);
		
		ReferObjContext context = ReferObjContext.getInstance();
		info.setApis(context.getAPINames());
		
		InputStream in=this.getClass().getResourceAsStream(calleeFileName);
		String callee=DataStreamHelper.output2LittleBytesAndGC(in, 20);
		
		if(callee==null){
			callee=SequGenerator.withoutMultiSecond().suffixFix(5).gen();
		}
		info.setCallee(callee);
		
		logger.info("上报的数据为："+JSONObject.toJSON(info));
		
		zkRegistry.register(info);
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
