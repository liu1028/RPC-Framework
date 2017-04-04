package org.eagle.rpc.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lhs
 * @Time 2017-03-30 21:37:12
 * @Description 抽象套接字处理器
 */
public abstract class AbstractSocketProcessor implements Processor,Lifecycle{

	protected static Logger logger=LoggerFactory.getLogger(AbstractSocketProcessor.class);
	private static final Charset DEFAULT_CHARSET=Charset.forName("UTF-8");
	
	private Socket socket;
	
	/**
	 * 使用reader，writer是便于区分每个TCP发送的数据包的界限。read或write一行就是一个连接完整的请求或响应
	 */
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private ByteArrayOutputStream byteOut;
	
	public AbstractSocketProcessor(Socket socket){
		
		this.socket=socket;
	}
	 
	@Override
	public void init() {
		try {
			this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),DEFAULT_CHARSET));
			this.writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),DEFAULT_CHARSET));
			this.byteOut=new ByteArrayOutputStream();		
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(RPCExcptionStatus.SOCKET_GET_STREAM_FAILURE.toString());
			destroy();
		}
	}
	
	/**
	 * @throws RPCException 
	 * @description 接收数据报
	 */
	protected byte[] receiveMessage() throws RPCException{
		
		try {
			String rawStr;
			if((rawStr=reader.readLine())!=null){
				return rawStr.getBytes(DEFAULT_CHARSET);
			}else{
				return null;
			}
			
		} catch (IOException e) {
			//TODO:将来规范化日志与异常系统
			e.printStackTrace();
			throw new RPCException(RPCExcptionStatus.SOCKET_RECEIVE_DATA_FAILURE);
		}
		
	}
	
	/**
	 * @throws RPCException 
	 * @description 发送数据报
	 */
	protected void sendMessage(byte[] response) throws RPCException{
		try {
			
			writer.write(new String(response,DEFAULT_CHARSET));
			writer.newLine(); //写一个换行符表示当前一个请求的完整数据已经发送完毕
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RPCException(RPCExcptionStatus.SOCKET_RECEIVE_DATA_FAILURE);
		}
	}
		
/*	protected Socket getSocket(){
		return this.socket;
	}*/
	
	@Override
	public void destroy(){
		try {
			if(byteOut!=null){
				byteOut.close();
			}
			if(socket!=null){
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
