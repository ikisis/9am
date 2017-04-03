package domain;

public class UserPushInfo {
	private String userGuid;
	private String pushId;
	private String pushType;
	
	public UserPushInfo(){}
	
	public UserPushInfo(String userGuid, String pushId, String pushType) {
		super();
		this.userGuid = userGuid;
		this.pushId = pushId;
		this.pushType = pushType;
	}
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	public String getPushId() {
		return pushId;
	}
	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	
	
}
