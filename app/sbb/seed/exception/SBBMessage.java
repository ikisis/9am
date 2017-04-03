package sbb.seed.exception;

public class SBBMessage extends RuntimeException{

	private static final long serialVersionUID = 4060208180257837344L;
	
	private String msg;
	
	public SBBMessage(String msg){
		super(msg);
		this.msg = msg;
	}
	
	private int code;
	public SBBMessage(int code){
		super(Integer.toString(code));

		this.code = code;
	}
	
	public String getMSG(){
		return this.msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	

}
