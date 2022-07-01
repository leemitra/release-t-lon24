package com.groupproject.moneproject.service;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private KieSession kieSession;
	//private RuntimeManager runtimeManager;
	
	
	public String submitLeaves(String empId, Integer leaves) {
 
		//RuntimeEngine engine = runtimeManager.getRuntimeEngine(EmptyContext.get());
		//KieSession ksession = engine.getKieSession();
		//TaskService taskService = engine.getTaskService();
		//taskService.release(taskId, userId);
		ProcessInstance instance =   kieSession.startProcess("jbpm.processes.sample");
		System.out.println(instance.getId() +" "+instance.getProcessId() );
		
		return instance.getProcessName();
	}
}
