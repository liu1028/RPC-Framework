package org.eagle.registration.router;

import java.util.List;
import java.util.Random;

import org.eagle.common.util.PairValue;
import org.eagle.registration.common.RegistryInfo;

/**
 * @author lhs
 * @Time 2017-04-10 23:48:20
 * @Description 默认的阀，在每个阀链的最末尾，进行最后的权重路由匹配
 */
public class DefaultValve implements Valve{

	private final int defaultWeight=5;
	private final int maxWeight=10;
	
	//维持当前服务的所有主机的元信息，组成列表
	private List<RegistryInfo>  registries;
	
	@Override
	public String handle(String matcher) {
		int seq=loadBalanceByWeight();
		
		String host=registries.get(seq).getHost();
		int port=registries.get(seq).getPort();
		
		return host+":"+port;
	}

	@Override
	public void setNext(Valve valve) {
	}

	public List<RegistryInfo> getRegistries() {
		return registries;
	}

	public void setRegistries(List<RegistryInfo> registries) {
		this.registries = registries;
	}
	
	private int loadBalanceByWeight(){
		int instanceNum=registries.size();
		int[] weights=new int[instanceNum+1];
		
		int sum=0;
		weights[0]=0;
		for(int i=1;i<=instanceNum;i++){
			Integer weight=registries.get(i-1).getWeight();
			if(weight==null){
				sum+=defaultWeight;
				weights[i]=sum;
			}else if(weight>maxWeight){
				sum+=maxWeight;
				weights[i]=sum;
			}else{
				sum+=weight;
				weights[i]=sum;
			}
		}
		
		int position=new Random().nextInt(sum);
		int hostIndex=0;
		for(int i=0;i<instanceNum;i++){
			if(weights[i]<=position && weights[i+1]>position){
				hostIndex=i+1;
				break;
			}
		}
		
		return hostIndex-1;
	}
	
	
}
