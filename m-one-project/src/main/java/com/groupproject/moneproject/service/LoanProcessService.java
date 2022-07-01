package com.groupproject.moneproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupproject.moneproject.component.AppJbpmComponent;
import com.groupproject.moneproject.model.CustomerDecision;
import com.groupproject.moneproject.model.LoanRequest;

@Service
public class LoanProcessService {

	@Autowired
	private KieSession kieSession;
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private AppJbpmComponent appJbpmComponent;
	public String getStringResponse() {
		System.out.println("=======Hello===");
		return "Hello user";
	}
	public String loanProcessTrigger() {
		
		ProcessInstance processInstance =  kieSession.startProcess("com.mygroup.loanProcess");
		System.out.println(processInstance.getId());
		return processInstance.getProcessId();
	}
	
	public String submitCustomerRequest(CustomerDecision decision) {
		System.out.println(decision.toString());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("comments", decision.getComments());
		map.put("loanAmount", decision.getLoanAmount());
		map.put("interestRate", decision.getInterestRate());
		map.put("tenor", decision.getYears());
		map.put("loanForm","Hello user taks");
		ProcessInstance processInstance =  kieSession.startProcess("com.mygroup.loanProcess");
		List<String> groups = new ArrayList<String>();
		groups.add("CUSTOMER");
		List<TaskSummary> tasks= appJbpmComponent.getPendingTasksByGroupId(groups);
		map.put("managerId", "MN234DS");
		map.put("bankManagerRole", "MANAGER");
		Long taskId = tasks.get(0).getId();
		String userId = decision.getIdCardNumber();
		claimTaskByTaskAndUserId(taskId, userId);
		startTaskByTaskAndUserId(taskId, userId);
		completeTaskByTaskAndUserId(map, taskId, userId);
		return "success";
	}

	private void completeTaskByTaskAndUserId(Map<String, Object> map, Long taskId, String userId) {
		taskService.complete(taskId, userId, map);
	}

	private void startTaskByTaskAndUserId(Long taskId, String userId) {
		taskService.start(taskId, userId);
	}

	private void claimTaskByTaskAndUserId(Long taskId, String userId) {
		taskService.claim(taskId, userId);
	}
	
	
	
	public List<LoanRequest> getPendingTasks(List<String> groups){
		List<LoanRequest> loans = new ArrayList<LoanRequest>(); 
		List<TaskSummary> tasks= appJbpmComponent.getPendingTasksByGroupId(groups);
		for (TaskSummary task : tasks) {
			LoanRequest loan = new LoanRequest();
			loan.setTaskId(task.getId());
			loan.setRole("MANAGER");
			loan.setDescription("Customer Loan Request");
			loans.add(loan);
		}
		
		 
		return loans;
	}
	
	public String submitTaskFromManager(Long taskId, String userId) {
		taskService.claim(taskId, userId);
		startTaskByTaskAndUserId(taskId, userId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userId", userId);
		completeTaskByTaskAndUserId(data, taskId, userId);
		return "Loan Approved for this Customer";
	}
	
}
