package controllers.vo;

import domain.GroupMemberProfile;

public class UpdateMemberProfile {
	private String groupGuid;
	private String email;
	private GroupMemberProfile profile;
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
	public GroupMemberProfile getProfile() {
		return profile;
	}
	public void setProfile(GroupMemberProfile profile) {
		this.profile = profile;
	}
	
	
}
