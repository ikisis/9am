package domain;

public class GroupCardNews {
	private String groupGuid;
	private String cardGuid;
	private String detailedCategory;
	private String type;
	private String sourceUrl;
	private String title;
	private String imageUrl;
	private String thumbnail;
	private String userGuid;
	private long ts;
	
	public GroupCardNews(String groupGuid, String cardGuid, String detailedCategory, String type, String sourceUrl,
			String title, String imageUrl, String thumbnail, String userGuid, long ts) {
		super();
		this.groupGuid = groupGuid;
		this.cardGuid = cardGuid;
		this.detailedCategory = detailedCategory;
		this.type = type;
		this.sourceUrl = sourceUrl;
		this.title = title;
		this.imageUrl = imageUrl;
		this.thumbnail = thumbnail;
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
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
