package org.eagle.rpc.transport.bio.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.eagle.common.exception.RPCException;
import org.eagle.common.util.PairValue;
import org.eagle.core.ReferObjContext;
import org.eagle.protocol.EagleProtoHandler;
import org.eagle.protocol.EagleRequest;
import org.eagle.protocol.EagleResponse;
import org.eagle.protocol.api.ProtocolHandler;
import org.eagle.protocol.api.Request;
import org.eagle.protocol.api.Response;
import org.eagle.protocol.api.ResponseStatus;
import org.eagle.rpc.api.AbstractCalleeProcessor;

public class EagleCalleeProcessor extends AbstractCalleeProcessor {

	public EagleCalleeProcessor() {
		super();
	}

	@Override
	protected void initInternal() {
		ProtocolHandler protocolHandler = new EagleProtoHandler();
		setProtocolHandler(protocolHandler);
	}

	@Override
	protected EagleResponse processInternal(Request req,Response resp) {
		EagleRequest request = (EagleRequest) req;

		EagleResponse response = (EagleResponse)resp;

		// 将唯一标注写回响应中
		response.setTag(request.getHeader().getTag());

		// 取得API名字
		String methodName = request.getHeader().getMethod();

		// 取得实例池上下文，获取实例
		ReferObjContext context = ReferObjContext.getInstance();

		// 获取实例
		Object instance = context.getBean(methodName);

		// 获取方法
		Method method = context.getMethod(methodName);

		try {
			// 获取方法中的argName和参数类型
			List<PairValue<String, Type>> pairs = context.getArgNames(methodName);

			// 构造参数实例列表
			List<Object> args = new ArrayList<>();
			for (PairValue<String, Type> pair : pairs) {
				Object obj = request.getArgument(pair.getValue1(), pair.getValue2());
				args.add(obj);
			}
			
			
			//进行真正的调用
			Object result = method.invoke(instance, args.toArray());

			response.setStatus(ResponseStatus.SUCCESS.getStatusCode());
			
			response.setResult(result);
			
		} catch (RPCException ex) {
			response.setStatus(ResponseStatus.FAILURE.getStatusCode());
			response.setResult("[400] RPC代码级：RPC调用异常 ||  "+ex.getMessage());
			
			ex.printStackTrace();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			response.setStatus(ResponseStatus.FAILURE.getStatusCode());
			response.setResult("[500] || jdk代码级：反射invoke异常 ||  "+e.getMessage());
			
			e.printStackTrace();
		}
		
		return response;
	}

}
