package controllers.vo;

public class Email {
	private String email;
	private String id;
	private String domain;
	
	public static Email of(String email){
		return new Email(email);
	}
	
	public Email(){}
	
	public Email(String email){
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		
		if(email != null && id == null)split();

		return id;
	}

	private void split() {
		String[] tokens = email.split("[@]");

		if (tokens == null || tokens.length < 2)
			throw new RuntimeException("unvalid email address");

		setId(tokens[0]);
		setDomain(tokens[1]);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		
		if(email != null && domain == null)split();

		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
