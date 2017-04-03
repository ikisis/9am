package sbb.seed.util;

public class Val2<A,B> {
	private A a;
	private B b;
	
	public Val2(){}
	public Val2(A a, B b){
		this.a = a;
		this.b = b;
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

}
