package org.eagle.registration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eagle.registration.common.RegistryInfo;
import org.eagle.registration.common.RegistryInfoParser;
import org.eagle.registration.router.Valve;
import org.eagle.registration.router.ValveBuilder;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author lhs
 * @Time 2017-04-13 23:32:23
 * @Description zk的发现类
 */
public class ZKDiscovery extends AbstractGenericZK {

	public static ZKDiscovery zkDiscovery = new ZKDiscovery();

	// 维护一个从serviceName到zk注册配置的映射
	public ConcurrentHashMap<String, List<RegistryInfo>> services = new ConcurrentHashMap<>();

	//// 维护一个从service=>callee=>url的映射
	public ConcurrentHashMap<String, Map<String, String>> callees = new ConcurrentHashMap<>();

	// 维护一个从serviceName到调用链的映射
	public ConcurrentHashMap<String, Valve> valveMap = new ConcurrentHashMap<>();

	private ZKDiscovery() {
	}

	public void initZookeeper(String zkAddr) {
		initZK(zkAddr);
	}

	public void discover(List<String> serviceNames) {

		loadInstanceConf(serviceNames);
		
		loadServiceValve(serviceNames);
		
	}

	private void loadInstanceConf(List<String> serviceNames) {
		for (String service : serviceNames) {
			String instancePath = String.format(instancePathTemplate, service);
			String routerPath = String.format(routerPathTemplate, service);

			List<RegistryInfo> infos = buildRegistryInfo(instancePath);
			services.put(service, infos);

			Map<String, String> map = buildSerCalleeURL(infos);
			callees.put(service, map);
		}		
	}
	
	private void loadServiceValve(List<String> serviceNames) {
		for (String service : serviceNames) {
			String routePath=String.format(routerPathTemplate, service);
			String routeTxt=readData(routePath);
			
			Valve valve=ValveBuilder.newValveBuilder().takeRawRouteText(routeTxt)
					.takeRegInfo(services.get(service))
					.takeCallee2UrlMap(callees.get(service))
					.buildValve();
			
			valveMap.put(service, valve);
		}
	}

	private List<RegistryInfo> buildRegistryInfo(String instancePath) {
		List<RegistryInfo> infos = Lists.newArrayList();

		List<String> children = getChildren(instancePath);

		for (String child : children) {
			String fullChild = String.format(instancePath + "/%s", child);
			String content = readData(fullChild);

			infos.add(RegistryInfoParser.parse(content));
		}

		return infos;
	}

	private Map<String, String> buildSerCalleeURL(List<RegistryInfo> infos) {
		Map<String, String> map = Maps.newHashMap();

		for (RegistryInfo info : infos) {
			map.put(info.getCallee(), info.getHost() + ":" + info.getPort());
		}

		return map;
	}

	public static ZKDiscovery getInstance() {
		return zkDiscovery;
	}
}
