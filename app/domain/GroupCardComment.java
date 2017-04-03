package domain;

public class GroupCardComment {
	private String groupGuid;
	private String cardGuid;
	private String commentGuid;
	private String comment;
	private String userGuid;
	private Long ts;
	
	
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
	public String getCommentGuid() {
		return commentGuid;
	}
	public void setCommentGuid(String commentGuid) {
		this.commentGuid = commentGuid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserGuid() {
		return userGuid;
	}
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	
	
}
