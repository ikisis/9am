package domain;

public class GroupCardTag {
	private String groupGuid;
	private String cardGuid;
	private String tag;
	private String tagGuid;
	private String userGuid;
	private long ts;
	
	
	
	public GroupCardTag(String groupGuid, String cardGuid, String tag, String tagGuid, String userGuid, long ts) {
		super();
		this.groupGuid = groupGuid;
		this.cardGuid = cardGuid;
		this.tag = tag;
		this.tagGuid = tagGuid;
		this.userGuid = userGuid;
		this.ts = ts;
	}
	public String getGroupGuid() {
		return groupGuid;
	}
	public void setGroupGuid(String groupGuid) {
		this.groupGuid = groupGuid;
	}
	public String getCardGuid() {
		return cardGuid;
	}
	public void setCardGuid(String cardGuid) {
		this.cardGuid = cardGuid;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}
	
}
