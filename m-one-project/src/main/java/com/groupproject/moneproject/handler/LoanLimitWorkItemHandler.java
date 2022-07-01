package com.groupproject.moneproject.handler;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class LoanLimitWorkItemHandler implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
	
		
		Map<String, Object> results = new HashMap<String, Object>();
		
		
		Float principal =  (Float) workItem.getParameter("loanAmount");
		Float  interestRate =  (Float) workItem.getParameter("interestRate");
		Float tenor = (Float) workItem.getParameter("tenor");
		System.out.println(workItem.getParameter("loanAmount"));
		System.out.println(workItem.getParameter("loanFrom"));
		System.out.println(workItem.getName());
		System.out.println("rateInterest "+interestRate);
		System.out.println("years "+tenor);
        double r = (interestRate / 100) / 12;   // monthly interest rate
        System.out.println("rate "+r);
        double n = 12 * tenor;          // number of months
    	System.out.println("number of months "+n);
        double payment  = (principal * r) / (1 - Math.pow(1+r, -n));
        double interest = payment * n - principal;
        double totalAmount = payment*n;
        int retval = Float.compare(0, tenor);
    	if (retval == 0) {
			throw new RuntimeException("Loan cannot issued for tenor 0");
		}
		else {
			// retrieve account from the database
			System.out.println("Monthly payments = " + payment );
	        System.out.println("Total interest   = " + interest);
	        System.out.println(" Final Result  "+(totalAmount>50000));
	        results.put("isCancelledCust", (totalAmount>50000));
	        results.put("isCancelled", (totalAmount>50000));
			manager.completeWorkItem(workItem.getId(), results);
		}
		
    	
        
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

}
