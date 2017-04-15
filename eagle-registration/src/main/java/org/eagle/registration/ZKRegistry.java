package org.eagle.registration;

import java.util.Random;
import org.apache.zookeeper.common.PathUtils;
import org.eagle.registration.common.RegistryInfo;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lhs
 * @Time 2017-04-09 21:35:18
 * @Description zookeeper服务端注册
 */
public class ZKRegistry extends AbstractGenericZK {

	public ZKRegistry(String zkAddr) {
		super(zkAddr);
	}

	public void register(RegistryInfo info) {
		String serviceName = info.getService();
		PathUtils.validatePath(serviceName);

		registerInstance(serviceName, info);
		registerPolicy(serviceName);
		registerConfig(serviceName);
		registerRouter(serviceName);
	}

	private void registerInstance(String serviceName, RegistryInfo info) {
		String instancePath=String.format(instancePathTemplate, serviceName);
		createParentsPersistent(instancePath);
		
		while (true) {
			int num = new Random().nextInt(instanceUpperBound);
			String insOnePath= String.format(detailInsPathTemplate, serviceName, num);

			if(existPath(insOnePath)){
				continue;
			}
			
			String json = JSONObject.toJSONString(info);
		
			createEphmeral(insOnePath,json);
			
			break;
		}
	}

	private void registerPolicy(String serviceName) {
		String policyPath = String.format(policyPathTemplate, serviceName);

		createParentsPersistent(policyPath);
	}

	private void registerConfig(String serviceName) {
		String configPath = String.format(configPathTemplate, serviceName);

		createParentsPersistent(configPath);
	}

	private void registerRouter(String serviceName) {
		String routePath = String.format(routerPathTemplate, serviceName);

		createParentsPersistent(routePath);
	}

}
