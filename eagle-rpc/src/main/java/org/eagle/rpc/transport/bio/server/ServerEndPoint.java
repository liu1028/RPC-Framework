package org.eagle.rpc.transport.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.eagle.common.Constants;
import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.common.util.SystemConfig;
import org.eagle.common.util.ThreadHolder;
import org.eagle.rpc.api.AbstractEndPoint;
import org.eagle.rpc.api.Processor;
import org.eagle.rpc.transport.bio.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lhs
 * @Time 2017-03-26 23:58:35
 * @Description
 */
public class ServerEndPoint extends AbstractEndPoint{

	private static Logger logger=LoggerFactory.getLogger(ServerEndPoint.class);
	
	private ServerSocket serverSocket;

	private ThreadPool threadPool;
	
	private int port;

	public ServerEndPoint() {
		this.threadPool=new ThreadPool();
	}
	
	public void init(Integer port){
		setPort(port);
		init();
	}
	
	public void init() {
		((Lifecycle)threadPool).init();
		
		try {
			port = getPort();
			 
			serverSocket = new ServerSocket(port);
			
			System.out.println("  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("  @                                                                                                     ");
			System.out.println("  @                服务绑定地址：0.0.0.0   端口："+port+"                                      ");
			System.out.println("  @               上报地址：234.13.1.4  端口："+port+"                                        ");
			System.out.println("  @                                                                                                      ");
			System.out.println("  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		} catch (Exception e) {
			e.printStackTrace();
			
			destroy();
		}
	}

	

	@Override
	public void start() {
		try{
			logger.info("开始监听。。。");
			while(true){
				
				Socket socket=serverSocket.accept();
				
				logger.info("接收到请求"+socket);
				
				// 接收socket，开启线程处理。这是一个长连接，保持在调用客户端与服务器之间
				Processor processor=new SocketProcessor(socket);
				
				//初始化=处理器
				((Lifecycle)processor).init();
				
				//交给线程持有类
				ThreadHolder holder=new ThreadHolder(processor, "process");
				
				//启动线程
				threadPool.execute(holder);
				
			}
		}catch(IOException ex){
			
			ex.printStackTrace();
			
			//关闭底层连接
			destroy();
		}
	}

	@Override
	public void destroy() {
		try{
			if(serverSocket!=null)
				serverSocket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
