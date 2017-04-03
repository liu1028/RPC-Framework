package org.eagle.rpc.transport.bio.client;

import java.io.IOException;
import java.net.Socket;

import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.rpc.api.AbstractEndPoint;

/**
 * @author lhs
 * @Time 2017-04-03 16:12:50
 * @Description 客户端类
 */
public class ClientEndPoint extends AbstractEndPoint{

	private ConnectionPool connectionPool;
	
	private CallerPool callerPool;
	
	@Override
	public void init() {
		connectionPool=ConnectionPool.getInstance();
		callerPool=CallerPool.getInstance();
	}

	@Override
	public void start() {
		
	}

	public void sendMessage(Caller caller) throws RPCException{
		//从连接池获取TCP连接
		String host=caller.getHost();
		int port=caller.getPort();
		String connKey=host+":"+port;
		RPCConnection conn=connectionPool.getConnection(connKey);
		
		/**
		 * 连接池没有连接，则创建TCP连接，并加入连接池
		 */
		if(conn==null){
			try {
				conn=createRPCConnection(host,port);
				connectionPool.put(connKey, conn);
				
				//TODO：开启读线程。。。
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RPCException(RPCExcptionStatus.CLIENT_CONNECT_SERVER_FAILURE,e.getMessage());
			}
		}
		
		
		
	}
	
	private RPCConnection createRPCConnection(String host,int port) throws IOException{
		Socket socket=new Socket(host, port);
		RPCConnection conn=new RPCConnection(socket);
		return conn;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
}
