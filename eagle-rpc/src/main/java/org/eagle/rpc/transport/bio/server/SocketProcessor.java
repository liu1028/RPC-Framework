package org.eagle.rpc.transport.bio.server;

import java.net.Socket;

import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.rpc.api.AbstractSocketProcessor;

/**
 * @author lhs
 * @Time 2017-03-31 00:17:11
 * @Description socketProcessor，socket的具体逻辑处理
 */
public class SocketProcessor extends AbstractSocketProcessor {

	public SocketProcessor(Socket socket) {
		super(socket);
	}

	@Override
	public void process() {
		try {
			/**
			 * 开始循环接收客户端的消息，并进行处理
			 */
			while (true) {
				byte[] mesg = receiveMessage();
				
				if(mesg==null){
					throw new RPCException("TCP连接已经关闭");
				}
				
				//实例化处理类
				EagleCalleeProcessor process=new EagleCalleeProcessor();
				
				//初始化
				((Lifecycle)process).init();
				
				//转发请求
				process.forwardBinaryRequest(mesg); 
				
				//正式的处理请求
				process.process();
				
				//获取最终的调用结果
				byte[] response=process.retrieveBinaryResponse();
				
				//发送响应到远端
				sendMessage(response);
			}
		} catch (RPCException ex) {
			//调用销毁操作，关闭socket
			destroy();
			ex.printStackTrace();
		}
	}

	
	@Override
	public void start() {

	}
}
