package org.eagle.protocol.codec;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonSerializator implements Serializator{

	private static final Charset CHARSET=Charset.forName("UTF-8");
	
	@Override
	public byte[] serialize(Object obj) {
		/**
		 * SerializerFeature.WriteDateUseDateFormat   日期格式化
		 * SerializerFeature.WriteMapNullValue            输出空置字段
		 * SerializerFeature.WriteNullListAsEmpty        list字段如果为null，输出为[]，而不是null 
		 * SerializerFeature.WriteNullNumberAsZero     数值字段如果为null，输出为0，而不是null
		 * SerializerFeature.WriteNullBooleanAsFalse    Boolean字段如果为null，输出为false，而不是null
		 * SerializerFeature.WriteNullStringAsEmpty     字符类型字段如果为null，输出为""，而不是null
		 * SerializerFeature.WriteClassName           在序列化的文本中加入@type信息
		 *   比如：String str={"@type":"java.awt.Color","r":255,"g":0,"b":0,"alpha":255}
		 *   可以直接解析 JSON.parse(str);
		 */
		String jsonStr = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteDateUseDateFormat
				/*,SerializerFeature.WriteClassName*/);
		return jsonStr.getBytes(CHARSET);
	}

	@Override
	public Object deserialize(byte[] mesg) {
		String jsonStr=new String(mesg, CHARSET);
//		return JSONObject.parseObject(jsonStr);
		return JSONObject.parse(jsonStr);
	}
}
