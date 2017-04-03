package org.eagle.rpc.transport.bio.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author lhs
 * @Time 2017-04-03 16:13:15
 * @Description RPC的套接字封装类
 */
public class RPCConnection {

	private static final Charset CHARSET=Charset.forName("UTF-8");
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public RPCConnection(Socket socket) throws IOException{
		this.socket=socket;
		this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));
		this.writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),CHARSET));
	}

	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public BufferedWriter getWriter() {
		return writer;
	}

}
