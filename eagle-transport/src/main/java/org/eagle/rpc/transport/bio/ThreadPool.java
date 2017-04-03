package org.eagle.rpc.transport.bio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eagle.common.api.Lifecycle;

/**
 * @Time 2017-03-27 20:16:19
 * @Description 服务器端线程池
 */
public class ThreadPool implements Lifecycle{

	private ThreadPoolExecutor poolExecutor;
	
	//TODO 未来从配置文件中读取参数
	private final int corePoolSize=50;
	
	private final int maxPoolSize=100;
	
	private final long keepAliveTime=10;
	
	public ThreadPool(){
		
	}
	
	@Override
	public void init() {
		BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
		poolExecutor=new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime
				, TimeUnit.SECONDS,queue);
	}

	/**
	 * @description 提交任务给线程池
	 */
	public void execute(Runnable task){
		poolExecutor.execute(task);
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void destroy() {
		poolExecutor.shutdown();
	}
	
	
}
