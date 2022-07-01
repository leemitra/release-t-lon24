package com.groupproject.moneproject.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.groupproject.moneproject.model.CustomerDecision;
import com.groupproject.moneproject.model.LoanRequest;
import com.groupproject.moneproject.service.LoanProcessService;

@Controller
public class LoanProcesssController {
	
	@Autowired
	private LoanProcessService loanProcessService;
	
	
	@GetMapping(value = "/")
	public String defaultPageLoad() {
		return "taskCenter";
	}
	
	@GetMapping(value = "/customer")
	public ModelAndView getLoanRequest() {
		ModelAndView andView=new ModelAndView("loanRequest");
		andView.addObject("custReq", new CustomerDecision());
		
		return andView;
	}
	
	@PostMapping("/customerRequestProcess")
	public ModelAndView	processCustomerRequest(@ModelAttribute("custReq")CustomerDecision customerDecision) {
		ModelAndView model = new ModelAndView("response");
		String result = loanProcessService.submitCustomerRequest(customerDecision);
		model.addObject("result", result);
		return model;
				
	}
	@GetMapping("/manager")
	public ModelAndView getManagerTasks() {
		ModelAndView model = new ModelAndView("loanOfficer");
		List<String> groups = Arrays.asList("MANAGER");
		
		List<LoanRequest> loans = loanProcessService.getPendingTasks(groups);
		model.addObject("loans", loans);
		return model;
	}
	@GetMapping("/managerApproved/{taskId}")
	public ModelAndView managerApproved(@PathVariable("taskId")Long taskId) {
		ModelAndView model = new ModelAndView("response");
		String result = loanProcessService.submitTaskFromManager(taskId, "AM2456K");
		model.addObject("result", result);
		return model;
	}
}
