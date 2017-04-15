package org.eagle.registration;

import java.nio.charset.Charset;
import java.util.Random;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
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
		String instancePath;
		while (true) {
			int num = new Random().nextInt(instanceUpperBound);
			instancePath= String.format(detailInsPathTemplate, serviceName, num);

			if (!zkClient.exists(instancePath)) {
				zkClient.createPersistent(instancePath, true);
				break;
			}
		}

		String json = JSONObject.toJSONString(info);

		zkClient.writeData(instancePath, json);
	}

	private void registerPolicy(String serviceName) {
		String policyPath = String.format(policyPathTemplate, serviceName);

		if (!zkClient.exists(policyPath)) {
			zkClient.createPersistent(policyPath, true);
		}
	}

	private void registerConfig(String serviceName) {
		String configPath = String.format(configPathTemplate, serviceName);

		if (!zkClient.exists(configPath)) {
			zkClient.createPersistent(configPath, true);
		}
	}

	private void registerRouter(String serviceName) {
		String routePath = String.format(routerPathTemplate, serviceName);

		if (!zkClient.exists(routePath)) {
			zkClient.createPersistent(routePath, true);
		}
	}

}
