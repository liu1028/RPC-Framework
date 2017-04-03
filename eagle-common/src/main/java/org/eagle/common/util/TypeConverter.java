package org.eagle.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TypeConverter {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object compatible2JavaObject(Object val, Type type) throws RPCException {

		if (val == null)
			return null;

		if (type instanceof Class) //当类型为class时
		{
			Class<?> clz = (Class<?>) type;
			
			if (val instanceof JSONObject) {    //是jsonObject就直接转
				return ((JSONObject) val).toJavaObject(clz);
			}else if (val instanceof JSONArray) {  //是json数组，就按集合来讨论
				JSONArray jsonArr = (JSONArray) val;

				if (List.class.isAssignableFrom(clz)) {
					return jsonArr.toJavaList(Object.class);
				} else if (Set.class.isAssignableFrom(clz)) {
					Collection collection = new HashSet();
					for (int i = 0; i < jsonArr.size(); i++) {
						collection.add(jsonArr.get(i));
					}
					return collection;
				} else if (clz.isArray()) {
					return jsonArr.toArray(new Object[0]);
				}
			} else if (val instanceof Number) { // 是数值类型，进行转换
				Number number = (Number) val;
				if (clz == int.class || clz == Integer.class) {
					return number.intValue();
				} else if (clz == short.class || clz == Short.class) {
					return number.shortValue();
				} else if (clz == byte.class || clz == Byte.class) {
					return number.byteValue();
				} else if (clz == double.class || clz == Double.class) {
					return number.doubleValue();
				} else if (clz == float.class || clz == Float.class) {
					return number.floatValue();
				} else if (clz == long.class || clz == Long.class) {
					return number.longValue();
				} else if (clz == BigInteger.class) {
					return BigInteger.valueOf(number.longValue());
				}else if (clz == BigDecimal.class) {
					return new BigDecimal(number.doubleValue());
				} else if(clz== Date.class){
					Calendar calendar=Calendar.getInstance();
					calendar.setTimeInMillis(number.longValue());
					return calendar.getTime();
				}
			}else if(val instanceof String){
				String value=(String)val;
				if(clz==String.class){
					return value;
				}else if(clz==char.class || clz==Character.class){
					if(value.length()!=1){
						throw new RPCException(RPCExcptionStatus.TYPE_CAST_FAILURE,"String的字符大于1或为空，不能转换为char");
					}
					return value.charAt(0);
				}else if (clz == short.class || clz == Short.class) {
					return Short.valueOf(value);
				} else if (clz == byte.class || clz == Byte.class) {
					return Byte.valueOf(value);
				} else if (clz == int.class || clz == Integer.class) {
					return Integer.valueOf(value);
				} else if (clz == double.class || clz == Double.class) {
					return Double.valueOf(value);
				} else if (clz == float.class || clz == Float.class) {
					return Float.valueOf(value);
				} else if (clz == long.class || clz == Long.class) {
					return Long.valueOf(value);
				}else if (clz == boolean.class || clz == Boolean.class) {
					return new Boolean(value);
				} else if (clz == BigDecimal.class) {
					return new BigDecimal(value);
				} else if (clz == BigInteger.class) {
					return new BigInteger(value);
				}else if(clz.isEnum()){
					Enum.valueOf((Class<Enum>) clz, value);
				}else if(clz==Date.class){
					try{
						if(value.indexOf(":")!=-1){
							SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							return format.parse(value);
						}else{
							SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
							return format.parse(value);
						}
					}catch(ParseException ex){
						throw new RPCException(RPCExcptionStatus.STRING_TO_DATE_PARSE_ERROR,value+"-->日期失败！！");
					}
				}
			}else {
				return val;
			}
		} 
		else if (type instanceof ParameterizedType)  //当类型为泛型时
		{  
			ParameterizedType ptype = (ParameterizedType) type;
//			Class<?> rawClass = (Class<?>) (ptype.getRawType());  //泛型的原生类，即List，Set
//			Type actualType=ptype.getActualTypeArguments()[0];
			
			return JSONObject.parseObject(JSON.toJSONString(val), ptype);

		/*	if(Map.class.isAssignableFrom(rawClass))
			{
			}
			else
			{
				Collection collection=null;
				 JSONArray jsonArr = (JSONArray) val;
				if (List.class.isAssignableFrom(rawClass)) {
					//return jsonArr.toJavaList(actualType.getClass());
					//JSON.parseObject("", new TypeReference(){});
					collection= new ArrayList<>();
				} else if (Set.class.isAssignableFrom(rawClass)) {
					collection= new TreeSet<>();
				}else{
					throw new RPCException(RPCExcptionStatus.VERSION_NOT_SUPPORT_COMPLEX_PARAMETERIZED_TYPE);
				}
				for (int i = 0; i < jsonArr.size(); i++) {
					Object objTmp=jsonArr.get(i);
					collection.add(JSONObject.parseObject(JSON.toJSONString(objTmp), actualType));
				}
				return collection;
			}
	     */
		}
		
		return null;
	}
}
