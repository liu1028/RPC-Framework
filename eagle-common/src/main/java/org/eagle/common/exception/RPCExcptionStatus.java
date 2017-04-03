package org.eagle.common.exception;

/**
 * @author lhs
 * @Time 2017-03-26 22:36:23
 * @Description RPC中所有异常信息的状态码及相关说明信息
 */
public enum RPCExcptionStatus {

	/**
	 *  服务器环境变量异常信息。状态码：1000-1200
	 */
	SERVER_PORT_NOT_CLAIM("7000","服务器端口未在环境变量中声明！"),	
	SERVER_PORT_NOT_QULIFIED("7001","服务端口形式不符合要求，应为正数"),
	SERVER_PORT_NOT_IN_RANGE("7002","服务端口应在0~65535范围内"),
	CLIENT_HOST_NOT_QULIFIED("7003","客户端所连主机IP不符要求或找不到"),
	CLIENT_PORT_NOT_QULIFIED("7004","客户端所连主机端口不符要求"),
	
	/**
	 * 协议异常信息。状态码：2000-2100
	 */
	EAGLE_PROTOCOL_PARSE_FAILURE("8000","eagle协议解析失败"),

	
	/**
	 * 网络通信异常信息。状态码：3000-3100
	 */
	SOCKET_RECEIVE_DATA_FAILURE("9000","socket接收数据失败"),
	SOCKET_SEND_DATA_FAILURE("9001","socket输出数据失败"),
	SOCKET_GET_STREAM_FAILURE("9002","获取socket输入或输出流异常"),
	CLIENT_CONNECT_SERVER_FAILURE("9003","客户端连接服务器失败"),
	
	/**
	 * 暴露的接口中相关的异常信息。状态码：4000-4100
	 */
	EXPOSE_CLASS_NOT_INTERFACE("4000","暴露的类不是接口类"),
	EXPOSE_CLASS_NOT_MATCH_INSTANCE("4001","暴露的类型不与实例匹配"),
	EXPOSE_METHOD_ANNOTATION_NOT_BLANK("4002","暴露的方法API注解的value不能为空"),
	EXPOSE_PARAM_ANNOTATION_NOT_BLANK("4003","暴露的参数Arg注解的value不能为空"),
	EXPOSE_PARAM_ANNOTATION_CANT_NULL("4004","暴露的方法中的参数Arg注解必须有"),
	
	/**
	 * 类型转换异常信息。状态码：5000-5001
	 */
	TYPE_CAST_FAILURE("5000","类型转换异常"),
	STRING_TO_DATE_PARSE_ERROR("5001","日期类型转换失败"),
	VERSION_NOT_SUPPORT_COMPLEX_PARAMETERIZED_TYPE("5002","当前版本不支持复杂的泛型类型，包括：泛型的继承，泛型的嵌套。若有，请外套一个类来包装");
	
	
	private String statusCode;
	
	private String message;
	
	private RPCExcptionStatus(String statusCode,String message){
		this.statusCode=statusCode;
		this.message=message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
