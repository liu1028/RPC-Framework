package org.eagle.boot.api;

import java.util.ArrayList;
import java.util.List;

public class Apple {

	String name;
	int age;
	
	List<B> b=new ArrayList<>();
	
	public Apple(){
		name="apple";
		age=23;
		b.add(new B(4,3));
		b.add(new B(55,23));
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public List<B> getB() {
		return b;
	}


	public void setB(List<B> b) {
		this.b = b;
	}


	public static class B{
		int a;
		int b;
		
		public B(){
			
		}
		public B(int a, int b) {
			super();
			this.a = a;
			this.b = b;
		}
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public int getB() {
			return b;
		}
		public void setB(int b) {
			this.b = b;
		}
		@Override
		public String toString() {
			return "B [a=" + a + ", b=" + b + "]";
		}
	}


	@Override
	public String toString() {
		return "Apple [name=" + name + ", age=" + age + ", b=" + b + "]";
	}	
	
	
}
