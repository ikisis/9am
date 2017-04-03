package service.vo;

public class CardComment {
	private String commentGuid;
	private String comment;
	private String userGuid;
	private String name;
	private String email;
	private Long ts;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getTs() {
		return ts;
	}
	public void setTs(Long ts) {
		this.ts = ts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
