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

public class ServerBootStrap implements ApplicationContextAware{

	
	public ServerBootStrap(){
		
	}
	
	public void initReferObjContext(){
		//初始化核心调用池
		ReferObjContext context=ReferObjContext.getInstance();
		context.init();
	}
	
	public void initServer(){
		//初始化服务器连接
		ServerEndPoint server=new ServerEndPoint();
		server.init();
		server.start();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		initReferObjContext();
		
		Map<String, Exporter> map=applicationContext.getBeansOfType(Exporter.class);
		List<Exporter> exporters=new ArrayList<>();
		map.forEach((beanName,exporter)->exporters.add(exporter));
		
		ReferObjContext context=ReferObjContext.getInstance();
		
		try {
			context.createContainer(exporters);
			
			initServer();
		} catch (RPCException e) {
			e.printStackTrace();
		}
	}
}
