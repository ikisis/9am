package domain;

public class GroupCardTimeline {
	
	private String groupGuid;
	private String cardGuid;
	private Long ts;
	private String category;
	
	public GroupCardTimeline(String groupGuid, String cardGuid, Long ts, String category) {
		super();
		this.groupGuid = groupGuid;
		this.cardGuid = cardGuid;
		this.ts = ts;
		this.category = category;
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
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
