package org.eagle.rpc.transport.bio.client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lhs
 * @Time 2017-04-03 16:14:54
 * @Description 维护从客户端到服务器的长连接池
 */
public class ConnectionPool {

	private static final ConnectionPool connectionPool=new ConnectionPool();
	
	private ConcurrentHashMap<String, RPCConnection> pool;
	
	private ConnectionPool(){
		pool=new ConcurrentHashMap<>();
	}
	
	/**
	 * @description 添加连接到池中；池中存在则替换
	 */
	public void put(String url,RPCConnection conn){
		pool.put(url, conn);
	}
	
	public void put(String url,int port,RPCConnection conn){
		pool.put(url+":"+port, conn);
	}
	
	public RPCConnection getConnection(String host,int port){
		return pool.get(host+":"+port);
	}
	
	public RPCConnection getConnection(String uri){
		return pool.get(uri);
	}
	
	public static ConnectionPool getInstance(){
		return connectionPool;
	}
	
	public void destroy(){
		pool.clear();
		
		pool=null;
	}
}
