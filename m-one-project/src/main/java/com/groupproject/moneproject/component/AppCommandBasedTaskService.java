package com.groupproject.moneproject.component;

import org.jbpm.services.task.events.TaskEventSupport;
import org.jbpm.services.task.impl.command.CommandBasedTaskService;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.ExecutableRunner;

public class AppCommandBasedTaskService extends CommandBasedTaskService {

	public AppCommandBasedTaskService(ExecutableRunner executor, TaskEventSupport taskEventSupport,
			Environment environment) {
		super(executor, taskEventSupport, environment);
	}

}
