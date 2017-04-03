package org.eagle.common.exception;

/**
 * @author lhs
 * @Time 2017-03-26 22:35:53
 * @Description 自定义RPC异常类，统一处理所有的异常情况
 */
public class RPCException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public RPCException() {
		super();
	}
	
    public RPCException(String message) {
        super(message);
    }
	
    public RPCException(RPCExcptionStatus status){
    	super("["+status.getStatusCode()+"]:"+status.getMessage());
    }
    
    public RPCException(RPCExcptionStatus status,String reason){
    	super("["+status.getStatusCode()+"]:"+reason);
    }
}
