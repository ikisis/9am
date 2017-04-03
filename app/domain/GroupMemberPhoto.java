package domain;

public class GroupMemberPhoto {
	private String groupGuid;
	private String email;
	
	
	public GroupMemberPhoto(String groupGuid, String email) {
		super();
		this.groupGuid = groupGuid;
		this.email = email;
	}
	public String getGroupGuid() {
		return groupGuid;
	}
	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
}
