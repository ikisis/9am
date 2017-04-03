package controllers.vo;

public class Login {
	
	private String userGuid;
	private String email;
	private String password;
	private String name;
	private String[] groupGuids;
	
	public Login(){}
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String[] getGroupGuids() {
		return groupGuids;
	}

	public void setGroupGuids(String[] groupGuids) {
		this.groupGuids = groupGuids;
	}
	
}
