package org.eagle.rpc.transport.bio.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.rpc.api.AbstractEndPoint;

/**
 * @author lhs
 * @Time 2017-04-03 16:12:50
 * @Description 客户端类
 */
public class ClientEndPoint extends AbstractEndPoint{

	private static final Charset CHARSET=Charset.forName("UTF-8");
	
	private static ClientEndPoint endPoint=new ClientEndPoint();
	
	private ConnectionPool connectionPool;
	
	private void ClientEndpoin() {
	}
	
	@Override
	public void init() {
		connectionPool=ConnectionPool.getInstance();	
	}

	@Override
	public void start() {
		
	}

	public void sendMessage(String host,int port,byte[] req){
		
		//从连接池获取TCP连接
		String connKey=getConnectionKey(host,port);
		RPCConnection conn=connectionPool.getConnection(connKey);
		
		if(conn==null){
			// 连接池没有连接，则创建TCP连接
			conn=createRPCConnection(host,port);
			if(conn!=null){
				//并加入连接池
				connectionPool.put(connKey, conn);
				
				//TODO：开启读线程.连接同一个服务器，复用一个TCP连接
				
			}
		}
		
		if(conn==null)
			return;
			
		
		//发送请求。。。
		BufferedWriter writer=conn.getWriter();
		try {
			writer.write(new String(req,CHARSET));
			writer.newLine();
		} catch (IOException e) {
			connectionPool.remove(connKey);
			conn.destroy();
			e.printStackTrace();
		}
	
	}
	
	private String getConnectionKey(String host,int port){
		return host+":"+port; 
	}
	
	private RPCConnection createRPCConnection(String host,int port){
		Socket socket=null;
		try {
			socket = new Socket(host, port);
			RPCConnection conn=new RPCConnection(socket);
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
			try{
				if(socket!=null)
					socket.close();
				
			}catch(IOException ex){
				ex.printStackTrace();
			}
			return null;
		}
	}
	
	@Override
	public void destroy() {
	
	}

	public static ClientEndPoint getInstance(){
		return endPoint;
	}
	
}
