package com.groupproject.moneproject.model;

/*@Setter
@Getter*/
public class CustomerDecision {
	private String fullName;
	private String contactNumber;
	private String email;
	
	private String loanAmount;
	private String interestRate;
	private String years;
	
	private String idCardNumber;
	private String userDecision;
	private String comments;
	
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getUserDecision() {
		return userDecision;
	}
	public void setUserDecision(String userDecision) {
		this.userDecision = userDecision;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	@Override
	public String toString() {
		return "CustomerDecision [fullName=" + fullName + ", contactNumber=" + contactNumber + ", email=" + email
				+ ", loanAmount=" + loanAmount + ", interestRate=" + interestRate + ", years=" + years
				+ ", idCardNumber=" + idCardNumber + ", userDecision=" + userDecision + ", comments=" + comments + "]";
	}
	
}
