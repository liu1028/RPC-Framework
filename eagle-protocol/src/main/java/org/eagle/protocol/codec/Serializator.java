package org.eagle.protocol.codec;

/**
 * @author lhs
 * @Time 2017-03-27 20:56:32
 * @Description 序列化/反序列化类
 */
public interface Serializator {

	/**
	 * 序列化
	 */
	byte[] serialize(Object obj);
	
	/**
	 *  反序列化
	 */
	Object deserialize(byte[] mesg);
}
