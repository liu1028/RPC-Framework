package org.eagle.common.util;

import java.util.Date;
import java.util.Random;

import com.google.common.base.Splitter;

/**
 * @author lhs
 * @Time 2017-04-03 16:52:58
 * @Description 序列生成器,使用链式创建法
 */
public class SequGenerator {
	
	private static boolean withMulitiSecond=false;
	private int default_fix=6;
	
	private SequGenerator(){
	}
	
	public static SequGenerator withMultiSecond(){
		withMulitiSecond=true;
		return new SequGenerator();
	}
	
	public static SequGenerator withoutMultiSecond(){
		withMulitiSecond=false;
		return new SequGenerator();
	}

	public SequGenerator suffixFix(int num){
		default_fix=num;
		return this;
	}
	
	public String gen(){
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		long result = 0;
		for(int i = 0; i < default_fix; i++)
		    result = result * 10 + array[i];
		
		if(withMulitiSecond){
			return getMacroSecond()+String.valueOf(result);
		}else{
			return String.valueOf(result);
		}
	}
	
	private String getMacroSecond(){
		Date date=new Date();
		return String.valueOf(date.getTime());
	}
	
	public static void main(String[] args) {
		String s1=SequGenerator.withMultiSecond().suffixFix(6).gen();
		
		String s2=SequGenerator.withoutMultiSecond().suffixFix(6).gen();
		
		System.out.println(s1+"\n"+s2);
	}
}
