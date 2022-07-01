package com.groupproject.moneproject.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.services.task.commands.TaskCommand;
import org.jbpm.services.task.commands.TaskContext;
import org.jbpm.services.task.utils.ClassUtil;
import org.kie.api.runtime.Context;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppJbpmComponent {
	@Autowired
	private KieSession kieSession;
	
	@Autowired
	private TaskService taskService;
	 
	
	public Long startProcessByName(String processId, Map<String, Object> parameters) {
		ProcessInstance instance = kieSession.startProcess(processId, parameters);
		return instance.getId();
	}
	public void completeTaskByTaskAndUserId(Long taskId, String userId, Map<String, Object> map) {
		taskService.complete(taskId, userId, map);
	}

	public void startTaskByTaskAndUserId(Long taskId, String userId) {
		taskService.start(taskId, userId);
	}

	public void claimTaskByTaskAndUserId(Long taskId, String userId) {
		taskService.claim(taskId, userId);
	}
	public void getPendingTaskByGroupId(String groupid) {
		   List<String> groups = new ArrayList<String>();
	        groups.add("CUSTOMER");
	     
	        List<Status> status = Arrays.asList(Status.Created, Status.Ready, Status.Reserved, Status.InProgress, Status.Suspended);
	        
	        Map<String, Object> params = new HashMap<>();
	        params.put("userId", null);
	        params.put("status", status);
	        params.put("groupIds", groups);
	        List<TaskSummary>  tasks = getTaskListByNamedQuery("TasksAssignedAsPotentialOwnerByStatusWithGroups", params);
	        for (TaskSummary task : tasks) {
				System.out.println(task.getStatus().name() +"  " +task.getId() +"  "+task.getProcessInstanceId());
			}
		 
	}
	
	public List<TaskSummary> getPendingTasksByGroupId(List<String> groups ){
		List<Status> status = Arrays.asList(Status.Ready);
		return getTaskListByStatusGroupsAndUserId(status, groups, null);
	}
	
	private List<TaskSummary> getTaskListByStatusGroupsAndUserId(List<Status> status, List<String> groups, String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", status);
		params.put("groupIds", groups);
		List<TaskSummary> tasks = getTaskListByNamedQuery("TasksAssignedAsPotentialOwnerByStatusWithGroups", params);
		return tasks;
	}
	
	public List<TaskSummary>  getNewTasksOwnedByStatus(String userId, String status) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("status", Arrays.asList(status)); 
		List<TaskSummary> tasks = getTaskListByNamedQuery("NewTasksOwned", params);
		return tasks;
	}
	
	private List<TaskSummary> getTaskListByNamedQuery(String query, Map<String, Object> params) {

		List<TaskSummary> tasks = taskService.execute(new TaskCommand<List<TaskSummary>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<TaskSummary> execute(Context cntxt) {
				TaskContext context = (TaskContext) cntxt;
				return context.getPersistenceContext().queryWithParametersInTransaction(query, params,
						ClassUtil.<List<TaskSummary>>castClass(List.class));
			}
		});

		return tasks;

	}
	
	

}
