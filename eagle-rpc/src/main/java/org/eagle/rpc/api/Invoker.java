package org.eagle.rpc.api;

import org.eagle.common.exception.RPCException;
import org.eagle.rpc.transport.bio.client.Caller;

/**
 * @author lhs
 * @Time 2017-04-03 19:48:19
 * @Description 客户端的执行接口
 */
public interface Invoker {

	/**
	 * @throws RPCException 
	 * @description 执行方法
	 */
	Object invoke(Caller caller) throws RPCException;
}
