package org.eagle.registration;

import java.nio.charset.Charset;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.common.PathUtils;

public class AbstractGenericZK {
	
	private final int sessionTimeout = 10000;

	private final int connectionTimeout = 10000;
	
	private final Charset charset = Charset.forName("UTF-8");
	
	protected ZkClient zkClient;
	
	protected final int instanceUpperBound=50;

	protected final String servicePrefix = "/eagle";

	protected final String instancePathTemplate = servicePrefix + "%s/instance";

	protected final String detailInsPathTemplate = servicePrefix + "%s/instance/%s";
	
	protected final String policyPathTemplate = servicePrefix + "%s/policy";

	protected final String configPathTemplate = servicePrefix + "%s/config";

	protected final String routerPathTemplate = servicePrefix + "%s/policy/router";

	public AbstractGenericZK(){
		
	}
	
	public AbstractGenericZK(String zkAddr) {
		createZKClient(zkAddr);
	}
	
	public void initZK(String zkAddr){
		createZKClient(zkAddr);
	}
	
	protected void validateZKPath(String path){
		PathUtils.validatePath(path);
	}
	
	protected List<String> getChildren(String path){
		validateZKPath(path);
		return zkClient.getChildren(path);
	}
	
	public boolean existPath(String path){
		return zkClient.exists(path);
	}
	
	public String readData(String path){
		validateZKPath(path);
		String val=zkClient.readData(path);
		return val;
	}
	
	private void createZKClient(String zkAddr){
		this.zkClient = new ZkClient(zkAddr, sessionTimeout, connectionTimeout, new ZkSerializer() {

			@Override
			public byte[] serialize(Object data) throws ZkMarshallingError {
				String content = (String) data;
				return content.getBytes(Charset.forName("UTF-8"));
			}

			@Override
			public Object deserialize(byte[] data) throws ZkMarshallingError {
				String content = new String(data, Charset.forName("UTF-8"));
				return content;
			}
		});
	}
}
