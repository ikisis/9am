package domain;

public class GroupCardTagAgree {
	
	private String tagGuid;
	private String userGuid;

	public GroupCardTagAgree(String tagGuid, String userGuid) {
		super();
		this.tagGuid = tagGuid;
		this.userGuid = userGuid;
	}
	public String getTagGuid() {
		return tagGuid;
	}
	public void setTagGuid(String tagGuid) {
		this.tagGuid = tagGuid;
	}
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	
	
}
