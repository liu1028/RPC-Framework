package org.eagle.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eagle.common.annotation.API;
import org.eagle.common.annotation.Arg;
import org.eagle.common.api.Lifecycle;
import org.eagle.common.exception.RPCException;
import org.eagle.common.exception.RPCExcptionStatus;
import org.eagle.common.util.PairValue;
import org.eagle.core.api.BeanFactory;
import org.eagle.core.api.MethodFactory;

/**
 * @author lhs
 * @Time 2017-04-02 14:26:11
 * @Description 公用对象上下文类。
 *  两个功能：1. 适配BeanFactory,MethodFactory两个工厂，来维护实体域
 *             2.根据expoter的上下文情况，来创建这两个工厂
 */
public class ReferObjContext implements BeanFactory,MethodFactory,Lifecycle{

	//单例
	private static ReferObjContext context=new ReferObjContext();
	
	private ReferObjContainer container;
	
	/**
	 * 私有构造
	 */
	private ReferObjContext(){
	}
	
	public void init(){
		container=new ReferObjContainer();
		((Lifecycle)container).init();
	}

	public void start() {
		//Nothing need to do....
	}
	
	
	/**
	 * @param 导出类的列表
	 * @throws RPCException 
	 * @description 根据导出类列表，将服务实体填充进container中
	 */
	public void createContainer(List<Exporter> exporters) throws RPCException{
		
		for (Exporter exporter : exporters) 
		{
			Class<?> clz=exporter.getInterfaceClz();
			Object instance=exporter.getObj();
			
			if(!clz.isInterface()){
				throw new RPCException(RPCExcptionStatus.EXPOSE_CLASS_NOT_INTERFACE);
			}
			
			if(!clz.isInstance(instance)){
				throw new RPCException(RPCExcptionStatus.EXPOSE_CLASS_NOT_MATCH_INSTANCE);
			}
			
			//获取接口内的所有public方法
			Method[] methods=clz.getMethods();
			
			for (Method interfaceMethod : methods)
			{
				//获取方法上标注的所有注解类
				Annotation[] methodAnnotations=interfaceMethod.getAnnotations();
				
				for (Annotation methodAnnotation : methodAnnotations)
				{
					if(methodAnnotation instanceof API)
					{
						API api=(API)methodAnnotation;  //找到API注解，并向上转换类型

						String apiName=api.value();
						if(StringUtils.isBlank(apiName))
						{
							throw new RPCException(RPCExcptionStatus.EXPOSE_METHOD_ANNOTATION_NOT_BLANK);
						}
						
						addBean(apiName, instance);  //将实例加入容器中
						addMethod(apiName, interfaceMethod); //将反射的方法加入到容器中
					}
				}
			}
		}
	}
	
	/**
	 * @description 获取指定api的参数标注arg的名字列表
	 */
	public List<PairValue<String, Type>> getArgNames(String apiName) throws RPCException{
		Method interfaceMethod=getMethod(apiName);
		List<PairValue<String, Type>> pairs=new ArrayList<PairValue<String, Type>>();
		
		Parameter[] params=interfaceMethod.getParameters(); //获取当前标注了API注解的方法中的所有参数
		for (Parameter param : params) {
			Arg arg=param.getAnnotation(Arg.class);
			
			if(arg==null){
				throw new RPCException(RPCExcptionStatus.EXPOSE_PARAM_ANNOTATION_CANT_NULL);
			}else{
				String argName=arg.value();
				if(StringUtils.isBlank(argName)){
					throw new RPCException(RPCExcptionStatus.EXPOSE_PARAM_ANNOTATION_NOT_BLANK);
				}else{
//					Type type=param.getType();
					Type type=param.getParameterizedType();
					PairValue<String, Type> pair=new PairValue<>(argName,type);
					pairs.add(pair);
				}
			}
		}

		return pairs;
	}

	public Method getMethod(String apiName) {
		return container.getMethod(apiName);
	}

	public void addMethod(String apiName, Method method) {
		 container.addMethod(apiName, method);
	}

	public boolean removeMethod(String apiName, Method method) {
		return container.removeMethod(apiName, method);
	}

	public Object getBean(String apiName) {
		return container.getBean(apiName);
	}

	public <T> T getBean(String apiName, Class<T> clz) {
		return container.getBean(apiName,clz);
	}

	public void addBean(String apiName, Object bean) {
		container.addBean(apiName, bean);
	}

	public boolean removeBean(String apiName) {
		return container.removeBean(apiName);
	}
	
	public void destroy() {
		container.destroy();
	}
	
	public static ReferObjContext getInstance(){
		return context;
	}
}
