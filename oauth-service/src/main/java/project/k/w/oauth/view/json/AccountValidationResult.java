package project.k.w.oauth.view.json;

import java.io.Serializable;

enum AccountStatus {
	ENABLE, DISABLE
}

public class AccountValidationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8835403243843213489L;
	
	private String accountId;
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private AccountStatus accountStatus;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

}
