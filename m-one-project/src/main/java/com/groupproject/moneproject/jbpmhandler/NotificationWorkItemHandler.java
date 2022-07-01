package com.groupproject.moneproject.jbpmhandler;

import org.kie.api.internal.utils.ServiceRegistry;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class NotificationWorkItemHandler implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		// extract parameters

		String from = (String) workItem.getParameter("From");

		String to = (String) workItem.getParameter("To");

		String message = (String) workItem.getParameter("Message");

		String priority = (String) workItem.getParameter("Priority");
		EmailService service = EmailService.getInstance();

		service.sendEmail(from, to, "Notification", message);
		// notify manager that work item has been completed
		manager.completeWorkItem(workItem.getId(), null);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		manager.abortWorkItem(workItem.getId());

	}

}
