package domain;

public class GroupMember {
	private String groupGuid;
	private String userGuid;
	private String adminYn;
	private long ts;
	
	public GroupMember(String groupGuid, String userGuid, String adminYn, long ts) {
		super();
		this.groupGuid = groupGuid;
		this.userGuid = userGuid;
		this.adminYn = adminYn;
		this.ts = ts;
	}
	public String getGroupGuid() {
		return groupGuid;
	}
	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	public String getAdminYn() {
		return adminYn;
	}
	public void setAdminYn(String adminYn) {
		this.adminYn = adminYn;
	}
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
}
