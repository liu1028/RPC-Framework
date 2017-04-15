package org.eagle.registration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.commons.lang3.StringUtils;
import org.eagle.registration.common.RegistryInfo;
import org.eagle.registration.common.RegistryInfoParser;
import org.eagle.registration.router.Valve;
import org.eagle.registration.router.ValveBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author lhs
 * @Time 2017-04-13 23:32:23
 * @Description zk的发现类
 */
public class ZKDiscovery extends AbstractGenericZK implements IZkDataListener,IZkChildListener{

	private static Logger logger=LoggerFactory.getLogger(ZKDiscovery.class);
	
	public static ZKDiscovery zkDiscovery = new ZKDiscovery();

	// 维护一个从service => zk注册配置的映射
	public ConcurrentHashMap<String, List<RegistryInfo>> services = new ConcurrentHashMap<>();

	// 维护一个从service => callee => url的映射
	public ConcurrentHashMap<String, Map<String, String>> callees = new ConcurrentHashMap<>();

	// 维护一个从service => chain 的映射
	public ConcurrentHashMap<String, Valve> valveMap = new ConcurrentHashMap<>();

	public static ZKDiscovery getInstance() {
		return zkDiscovery;
	}

	private ZKDiscovery() {
	}

	public void initZookeeper(String zkAddr) {
		initZK(zkAddr);
	}

	public void discover(List<String> serviceNames) {
		
		logger.info("ZK加载实例信息");
		loadInstanceConf(serviceNames);
		
		logger.info("ZK创建valve责任链");
		loadServiceValve(serviceNames);
		
	}
	
	public Valve getValve(String service){
		return valveMap.get(service);
	}

	private void loadInstanceConf(List<String> serviceNames) {
		for (String service : serviceNames) {
			String instancePath = String.format(instancePathTemplate, service);

			List<RegistryInfo> infos=buildRegInfoAndPutMap(service,instancePath);

			buildCalleeAndPutMap(service,infos);
			
			//订阅实例下子节点的变化
			subscribeChildChanges(instancePath);
		}		
	}
	
	private List<RegistryInfo> buildRegInfoAndPutMap(String service,String instancePath){
		List<RegistryInfo> infos = buildRegistryInfoAndSubcribe(instancePath);
		services.put(service, infos);
		
		return infos;
	}
	
	private void buildCalleeAndPutMap(String service,List<RegistryInfo> infos){
		Map<String, String> map = buildSerCalleeURL(infos);
		callees.put(service, map);
	}
	
	private void reloadInstanceAndValve(String service,String instancePath){

		List<RegistryInfo> infos=buildRegInfoAndPutMap(service,instancePath);

		buildCalleeAndPutMap(service,infos);
		
		reloadServiceValve(service);
	}
	
	private void loadServiceValve(List<String> serviceNames) {
		for (String service : serviceNames) {
			String routePath=String.format(routerPathTemplate, service);
			String routeTxt=readData(routePath);
			
			buildValveChain(routeTxt,service);
			
			subscribeDataChanges(routePath);
		}
	}
	
	private void reloadServiceValve(String service){
		String routePath=String.format(routerPathTemplate, service);
		String routeTxt=readData(routePath);
		
		buildValveChain(routeTxt,service);
	}
	
	private void reloadServiceValve(String service,String routeTxt){
		buildValveChain(routeTxt,service);
	}
	
	private void buildValveChain(String routeTxt,String service){
		Valve valve=ValveBuilder.newValveBuilder().takeRawRouteText(routeTxt)
				.takeRegInfo(services.get(service))
				.takeCallee2UrlMap(callees.get(service))
				.buildValve();
		
		valveMap.put(service, valve);
	}
	
	
	private List<RegistryInfo> buildRegistryInfoAndSubcribe(String instancePath) {
		List<RegistryInfo> infos = Lists.newArrayList();

		List<String> children = getChildren(instancePath);

		for (String child : children) {
			String fullChild = String.format(instancePath + "/%s", child);
			String content = readData(fullChild);

			infos.add(RegistryInfoParser.parse(content));
			
			//订阅实例节点的上报数据变化，使得服务有效调用
			subscribeDataChanges(fullChild);
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

	@Override
	public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
		logger.warn("ZK监听到："+parentPath+"目录下节点有改变，当前节点为："+currentChilds.toArray());
		if(parentPath.indexOf(insAbbrevPath)>-1){
			String service=StringUtils.substringBetween(parentPath, servicePrefix, insAbbrevPath);
			reloadInstanceAndValve(service,parentPath);
		}
		
	}

	@Override
	public void handleDataChange(String dataPath, Object data) throws Exception {
		
		if(dataPath.indexOf(policyAbbrevPath)>-1){
			logger.warn("ZK监听到："+dataPath+"的值有变化，当前值："+data);
			String service=StringUtils.substringBetween(dataPath, servicePrefix, routeAbbrevPath);
			reloadServiceValve(dataPath,(String)data);
		}else if(dataPath.indexOf(insAbbrevPath)>-1){
			//暂不实现，默认上报的实例只会添加和删除
		}
	}

	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
	}

	private void subscribeChildChanges(String path) {
		validateZKPath(path);
		getZkClient().subscribeChildChanges(path, this);
	}

	private void subscribeDataChanges(String path) {
		validateZKPath(path);
		getZkClient().subscribeDataChanges(path, this);
	}
}
