package org.eagle.rpc.transport.bio.client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lhs
 * @Time 2017-04-03 16:45:42
 * @Description 调用池，为了解决多个调用复用一个TCP连接时，返回的响应不能匹配正确调用的情况
 */
public class CallerPool {

	private static final CallerPool callerPool=new CallerPool();
	
	private ConcurrentHashMap<String, Caller> pool;
	
	private CallerPool(){
		pool=new ConcurrentHashMap<>();
	}
	
	public void put(String remoteId,Caller caller){
		pool.put(remoteId,caller);
	}
	
	public Caller get(String remoteId){
		return pool.get(remoteId);
	}
	
	public void remove(String remoteId){
		pool.remove(remoteId);
	}
	
	public static CallerPool getInstance(){
		return callerPool;
	}
}
