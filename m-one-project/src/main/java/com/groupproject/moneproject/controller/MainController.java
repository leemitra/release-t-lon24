package com.groupproject.moneproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupproject.moneproject.component.AppJbpmComponent;
import com.groupproject.moneproject.service.EmployeeService;
import com.groupproject.moneproject.service.LoanProcessService;

@RestController
public class MainController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private LoanProcessService loanProcessService;
	
	@Autowired
	private AppJbpmComponent appJbpmComponent;
	
	
	
	@GetMapping(value = "/version")
	public String getVersions(HttpServletRequest request, HttpServletResponse response) {
		String res = employeeService.submitLeaves("emp111", 2);
		System.out.println(res);
		System.out.println(request.getHeaderNames().toString());
		System.out.println("");
		return request.getRemoteHost();
	}
	
	@GetMapping("/loans-backup")
	public String triggerLoanFlow() {
		String res = loanProcessService.loanProcessTrigger();
		System.out.println(res);
		return res;
	}
	
	@GetMapping("/tasks")
	public String checkTaskLists() {
		 appJbpmComponent.getPendingTaskByGroupId("customerId");
		return "completed";
	}
	
}
