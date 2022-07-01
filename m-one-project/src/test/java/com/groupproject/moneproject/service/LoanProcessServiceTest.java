package com.groupproject.moneproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class LoanProcessServiceTest {//
	//@Autowired
	private LoanProcessService loanProcessService;

	//@Test
	void testLoanProcessTrigger() {
		String str= loanProcessService.getStringResponse();
		System.out.println(str);
		System.out.println("-----");
	}

	//@Test
	void testSubmitCustomerRequest() {
	}

	//@Test
	void testGetPendingTasks() {
	}

	//@Test
	void testSubmitTaskFromManager() {
	}

}
