package org.eagle.rpc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.core.Exporter;
import org.eagle.core.ReferObjContext;
import org.eagle.protocol.EagleRequest;
import org.eagle.protocol.EagleRequest.Header;
import org.eagle.rpc.service.Animal;
import org.eagle.rpc.service.AnimalImpl;
import org.eagle.rpc.transport.bio.server.EagleCalleeProcessor;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class testServerProcess {
	
	private ReferObjContext ctx;
	
	private EagleRequest request;
	
	@Before
	public void initContainer(){
		List<Exporter> exporters=new ArrayList<>();
		Exporter export=new Exporter(Animal.class, new AnimalImpl());
		exporters.add(export);
		
		ctx=ReferObjContext.getInstance();
		ctx.init();
		
		try {
			ctx.createContainer(exporters);
		} catch (RPCException e) {
			e.printStackTrace();
		}
		
		System.out.println("运行池构造完毕。。。");
	}
	
	@Before
	public void initRequest(){
		request=new EagleRequest();
		Header header=request.new Header();
		header.setTag("343545340434");
		header.setService("/biz/t8t-wkf-ctl/app");
//		header.setMethod("echo");
//		header.setMethod("argsHaveList");
//		header.setMethod("animal.getApple");
		header.setMethod("specialPrimitive");
		request.setHeader(header);
		
//		String content="hello world";
		/*******很重要**********/
		JSONObject r=new JSONObject();
		
/*       argsHaveList
 */
 /*     Set<Apple> apples=new HashSet<>();
		apples.add(new Apple());
		Apple a=new Apple();
		a.setAge(23232);
		a.setName("senix");
		apples.add(a);
		
		Set<Apple> apples2=new HashSet<>();
		apples2.add(new Apple());
		Apple a2=new Apple();
		a2.setAge(999);
		a2.setName("dfafdsaf");
		apples.add(a2);

		List<Set<Apple>> li=new ArrayList<>();
		li.add(apples);
		li.add(apples2);
		
		r.put("list1", li);
		r.put("list2", Arrays.asList("ffree","344jf","ter"));
		r.put("list3", new TreeSet<Integer>(Arrays.asList(45,6,76,2,65,8)));
		
		Map<String,Apple> maps=new HashMap<>();
		maps.put("map1", a);
		maps.put("map2", a2);
		r.put("map", maps);
	*/
		
/*      echo
 */    
/*        r.put("content", content);
		r.put("age", 33);
		r.put("dd", 0.234);
		r.put("ff", 0.24);
		r.put("bol", true);
		r.put("short", 34);
		r.put("by", 23);*/

		
		
/*		animal.getApple
 */ 
/*		r.put("app", new Apple());
		r.put("addr", "university");
		r.put("age", 23);
		*/
		

		Calendar cal=Calendar.getInstance();
		Date date1=cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -20);
		Date date2=cal.getTime();
		BigInteger inte=new BigInteger("11111222222244444");
		BigDecimal decimal=new BigDecimal("353453434234.45332");
		r.put("datelong", date1);
		r.put("dateStr", date2);
		r.put("bigInteger", inte);
		r.put("decimal", decimal);
		
		/**********************/
		
		request.setBody(r);
		
		String requ=
		JSONObject.toJSONString(request, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteDateUseDateFormat
				/*,SerializerFeature.WriteClassName*/);
		
		req=requ.getBytes(Charset.forName("UTF-8"));
		System.out.println("请求："+requ);
	}
	
	private byte[] req;

	@Test
	public void testSimpleAPI(){
//		ParserConfig.getGlobalInstance().setAutoTypeSupport(true); 
		
		EagleCalleeProcessor process=new EagleCalleeProcessor();
		((Lifecycle)process).init();
		
		process.forwardBinaryRequest(req); 
		process.process();
		byte[] response=process.retrieveBinaryResponse();
		
		System.out.println(new String(response,Charset.forName("UTF-8")));
	}
	
}
