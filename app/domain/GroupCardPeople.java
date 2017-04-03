package domain;

public class GroupCardPeople {
	private String groupGuid;
	private String cardGuid;
	private String detailedCategory;
	private String type;
	private String email;
	private String contentsJson;
	private String userGuid;
	private long ts;
	
	public GroupCardPeople(String groupGuid, String cardGuid, String detailedCategory, String type, String email,
			String contentsJson, String userGuid, long ts) {
		super();
		this.groupGuid = groupGuid;
		this.cardGuid = cardGuid;
		this.detailedCategory = detailedCategory;
		this.type = type;
		this.email = email;
		this.contentsJson = contentsJson;
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
	public String getDetailedCategory() {
		return detailedCategory;
	}
	public void setDetailedCategory(String detailedCategory) {
		this.detailedCategory = detailedCategory;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContentsJson() {
		return contentsJson;
	}
	public void setContentsJson(String contentsJson) {
		this.contentsJson = contentsJson;
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
