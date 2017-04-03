package domain;

public class UserEmail {
	private String userGuid;
	
	private String id;
	
	private String domain;
	
	private String verified;
	
	

	public UserEmail(String userGuid, String id, String domain, String verified) {
		this.userGuid = userGuid;
		this.id = id;
		this.domain = domain;
		this.verified = verified;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}
	
	
	
}
