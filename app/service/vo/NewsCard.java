package service.vo;

public class NewsCard {
	private String sourceUrl;
	private String title;
	private String imageUrl;
	private String thumbnail;
	
	public NewsCard(){}
	
	public NewsCard(String sourceUrl, String title, String imageUrl, String thumbnail) {
		super();
		this.sourceUrl = sourceUrl;
		this.title = title;
		this.imageUrl = imageUrl;
		this.thumbnail = thumbnail;
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
	
	
}
