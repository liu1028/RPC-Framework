package org.eagle.protocol;

import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.protocol.EagleRequest.Header;
import org.eagle.protocol.api.EagleHandler;
import org.eagle.protocol.api.ProtocolHandler;
import org.eagle.protocol.api.Request;
import org.eagle.protocol.api.Response;
import org.eagle.protocol.codec.JsonSerializator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lhs
 * @Time 2017-03-27 21:55:26
 * @Description eagle协议处理类。 请求协议定义如下: { tag:[远程ID] s:[service-name],
 *              m:[api-name], args:{ [arg1]:{ ... }, [arg2]:{ ... } ..... } }
 */
public class EagleProtoHandler implements ProtocolHandler, EagleHandler {

	private static Logger logger = LoggerFactory.getLogger(EagleProtoHandler.class);

	private static final String TAG = "tag";
	private static final String SERVICE = "service"; // "s";
	private static final String METHOD = "method"; // m";
	private static final String HEADER = "header";
	private static final String BODY = "body";
	
	private static final String STATUS="status";
	private static final Object RESULT="result";

	private JsonSerializator jSerializator;

	private EagleRequest request;

	private EagleResponse response;

	public EagleProtoHandler() {
		jSerializator = new JsonSerializator();
		request = new EagleRequest();
		response=new EagleResponse();
	}

	@Override
	public void decode4Request(byte[] mesg) {
		Object obj = jSerializator.deserialize(mesg);
		if (obj instanceof JSONObject) {
			JSONObject message = (JSONObject) obj;
			parseHeader4Req(message);
			parseBody4Req(message);
		} else {
			logger.error(RPCExcptionStatus.EAGLE_PROTOCOL_PARSE_FAILURE.toString());
		}
	}

	@Override
	public void parseHeader4Req(JSONObject mesg) {
		JSONObject jheader = mesg.getJSONObject(HEADER);
		Header header = request.new Header();
		header.setTag(jheader.getString(TAG));
		header.setService(jheader.getString(SERVICE));
		header.setMethod(jheader.getString(METHOD));
		request.setHeader(header);
	}

	@Override
	public void parseBody4Req(JSONObject mesg) {
		JSONObject jbody = mesg.getJSONObject(BODY);
		request.setBody(jbody/*jbody.getJSONObject(ARGS)*/);
	}

	@Override
	public void parseResponse(JSONObject mesg) {
		response.setTag(mesg.getString(TAG));
		response.setStatus(mesg.getString(STATUS));
		response.setResult(RESULT);
	}
	
	@Override
	public void decode4Response(byte[] mesg) {
		Object obj = jSerializator.deserialize(mesg);
		if (obj instanceof JSONObject) {
			JSONObject message = (JSONObject) obj;
			parseResponse(message);
		} else {
			logger.error(RPCExcptionStatus.EAGLE_PROTOCOL_PARSE_FAILURE.toString());
		}
	}
	
	@Override
	public byte[] encodeRequest(Object req) {
		return jSerializator.serialize(req);
	}

	@Override
	public byte[] encodeResponse(Object resp) {
		return jSerializator.serialize(resp);
	}

	@Override
	public Request fetchRequest() {
		return request;
	}
	
	@Override
	public Response fetchResponse() {
		return response;
	}
}
