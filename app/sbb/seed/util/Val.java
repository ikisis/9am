package sbb.seed.util;

public class Val<T> {
	T t;
	public Val(){}
	public Val(T t) {
		super();
		this.t = t;
	}
	public T get(){
		return t;
	}
	public void set(T t){
		this.t = t;
	}
	
	public static<T> Val<T> of(T t){
		return new Val<T>(t);
	}
}
