package service.vo;

public class CardThumbnail {
	
	private String cardGuid;
	private String category;
	private String detailedCategory;
	private String type;
	
	private Object contents;
	
	private int expressionCount;
	
	private Boolean isExprOwnMe = false;
	
	private int commentCount;
	
	private Boolean isCommentOwnMe = false;
	
	private Long ts;

	public String getCardGuid() {
		return cardGuid;
	}

	public void setCardGuid(String cardGuid) {
		this.cardGuid = cardGuid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public Object getContents() {
		return contents;
	}

	public void setContents(Object contents) {
		this.contents = contents;
	}

	public int getExpressionCount() {
		return expressionCount;
	}

	public void setExpressionCount(int expressionCount) {
		this.expressionCount = expressionCount;
	}

	public Boolean getIsExprOwnMe() {
		return isExprOwnMe;
	}

	public void setIsExprOwnMe(Boolean isExprOwnMe) {
		this.isExprOwnMe = isExprOwnMe;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Boolean getIsCommentOwnMe() {
		return isCommentOwnMe;
	}

	public void setIsCommentOwnMe(Boolean isCommentOwnMe) {
		this.isCommentOwnMe = isCommentOwnMe;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}
	
}
