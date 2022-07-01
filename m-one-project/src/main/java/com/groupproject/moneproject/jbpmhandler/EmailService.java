package com.groupproject.moneproject.jbpmhandler;

public class EmailService {
	
	private static EmailService emailService=null;

	private EmailService() {
		
	}
	public static EmailService getInstance() {
		if(emailService == null) {
			emailService = new EmailService();
		}
		return emailService;
	}
	public void sendEmail(String from, String to, String string, String message) {
		 
		
	}
	
	
	 public static void main(String[] args) {
	        double principal = Double.parseDouble("20000");
	        double years = Double.parseDouble("12");
	        double rate = Double.parseDouble("5");

	        double r = (rate / 100) / 12;   // monthly interest rate
	        double n = 12 * years;          // number of months

	        double payment  = (principal * r) / (1 - Math.pow(1+r, -n));
	        double interest = payment * n - principal;

	        System.out.println("Monthly payments = " + (payment*n) );
	        System.out.println("Total interest   = " + interest);
	    }


}
