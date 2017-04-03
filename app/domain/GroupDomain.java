package domain;

public class GroupDomain {
	private String groupGuid;
	private String domain;

	public GroupDomain(String groupGuid, String domain) {
		this.groupGuid = groupGuid;
		this.domain = domain;
	}
	public String getGroupGuid() {
		return groupGuid;
	}
	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}
