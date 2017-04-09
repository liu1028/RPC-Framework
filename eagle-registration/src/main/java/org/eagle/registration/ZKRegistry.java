package org.eagle.registration;

import java.nio.charset.Charset;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * @author lhs
 * @Time 2017-04-09 21:35:18
 * @Description
 */
public class ZKRegistry {

	private ZkClient zkClient;

	private final int sessionTimeout = 10000;

	private final int connectionTimeout = 10000;

	public ZKRegistry(String zkAddr) {
		this.zkClient = new ZkClient(zkAddr, 10000, 10000, new ZkSerializer() {

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
