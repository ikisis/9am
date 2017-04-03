package domain;

public class User {
	private String guid;
	
	private String password;
		
	private long ts;
	
	public User(String guid, String password, long ts) {
		this.guid = guid;
		this.password = password;
		this.ts = ts;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}
	
	
}
