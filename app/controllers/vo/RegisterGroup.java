package controllers.vo;

public class RegisterGroup {
	private String name;
	private String publicYn;
	private String autoJoinYn;
	private String email;
	
	
	@Override
	public String toString() {
		return "RegisterGroup [name=" + name + ", publicYn=" + publicYn + ", autoJoinYn=" + autoJoinYn + ", email="
				+ email + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublicYn() {
		return publicYn;
	}
	public void setPublicYn(String publicYn) {
		this.publicYn = publicYn;
	}
	public String getAutoJoinYn() {
		return autoJoinYn;
	}
	public void setAutoJoinYn(String autoJoinYn) {
		this.autoJoinYn = autoJoinYn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
