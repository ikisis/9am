package service.vo;

public class CardTag{
	
	private String tag;
	private String tagGuid;
	private int agreeCount;
	private boolean isAgreed = false;
	
	public String getTagGuid() {
		return tagGuid;
	}
	public void setTagGuid(String tagGuid) {
		this.tagGuid = tagGuid;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(int agreeCount) {
		this.agreeCount = agreeCount;
	}
	public boolean isAgreed() {
		return isAgreed;
	}
	public void setAgreed(boolean isAgreed) {
		this.isAgreed = isAgreed;
	}

}