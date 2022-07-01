package com.groupproject.moneproject.model;

import lombok.Getter;
import lombok.Setter;

/*@Setter
@Getter*/
public class LoanRequest {
private String loanName;
private Long taskId;
private String description;
private String role;
private String actorName;
public String getLoanName() {
	return loanName;
}
public void setLoanName(String loanName) {
	this.loanName = loanName;
}
public Long getTaskId() {
	return taskId;
}
public void setTaskId(Long taskId) {
	this.taskId = taskId;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getActorName() {
	return actorName;
}
public void setActorName(String actorName) {
	this.actorName = actorName;
}

}
