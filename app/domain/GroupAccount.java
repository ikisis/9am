package domain;

public class GroupAccount {
	
	private String guid;
	private String name;
	private String publicYn;
	private String autoJoinYn;
	private String created;
	private long ts;
	
	public GroupAccount(String guid, String name, String publicYn, String autoJoinYn, String created, long ts) {
		this.guid = guid;
		this.name = name;
		this.publicYn = publicYn;
		this.autoJoinYn = autoJoinYn;
		this.created = created;
		this.ts = ts;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublicYn() {
		return publicYn;
	}
	public void setPublicYn(String publicYn) {
		this.publicYn = publicYn;
	}
	public String getAutoJoinYn() {
		return autoJoinYn;
	}
	public void setAutoJoinYn(String autoJoinYn) {
		this.autoJoinYn = autoJoinYn;
	}
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
	

}
