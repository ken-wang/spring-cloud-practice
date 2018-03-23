package project.k.w.oauth.view.json;

public class AccountValidationRequest {

	private String email;
	private String phone;
	private String password;

	public AccountValidationRequest() {}
	
	public AccountValidationRequest(String email, String phone, String password) {
		this.email = email;
		this.phone = phone;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
