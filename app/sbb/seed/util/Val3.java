package sbb.seed.util;

public class Val3 <A,B,C>{
	private A a;
	private B b;
	private C c;
	
	public Val3(){}
	
	public Val3(A a, B b, C c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	public C getC() {
		return c;
	}
	public void setC(C c) {
		this.c = c;
	}
	
	
}
