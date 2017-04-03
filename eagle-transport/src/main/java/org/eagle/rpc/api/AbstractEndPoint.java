package org.eagle.rpc.api;

import javax.swing.text.html.HTMLEditorKit.LinkController;

import org.apache.commons.lang3.StringUtils;
import org.eagle.common.Constants;
import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.common.util.SystemConfig;

public abstract class AbstractEndPoint implements Lifecycle{

	private int port;
	
	protected int getPort() throws RPCException{
		//从环境变量获取端口
		String portStr = SystemConfig.getEnvVariable(Constants.JAVA_SERVICE_PORT);
		
		//校验并返回端口
		port= validatePort(portStr);
		return port;
	}
	
	/**
	 * @description 校验服务器端口属性，对于这种用户定义的变量，一定要强加检验！
	 */
	private int validatePort(String portStr) throws RPCException {
		if (StringUtils.isBlank(portStr)) {
			throw new RPCException(RPCExcptionStatus.SERVER_PORT_NOT_CLAIM);
		}

		if (StringUtils.isNumeric(portStr)) {
			
			int port = Integer.parseInt(portStr);
			
			if (port < 0 || port > 65535) {
				throw new RPCException(RPCExcptionStatus.SERVER_PORT_NOT_IN_RANGE);
			} else {
				return port;
			}
		} else {
			throw new RPCException(RPCExcptionStatus.SERVER_PORT_NOT_QULIFIED);
		}
	}
	
}
