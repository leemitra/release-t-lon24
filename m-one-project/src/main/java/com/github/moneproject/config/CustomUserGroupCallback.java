package com.github.moneproject.controller.config;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.task.UserGroupCallback;

public class CustomUserGroupCallback  implements UserGroupCallback {
//extends AbstractUserGroupInfo
	@Override
	public boolean existsUser(String userId) {
		return true;
		// as per app requirement
		}

	@Override
	public boolean existsGroup(String groupId) {
		return true;
	}

	@Override
	public List<String> getGroupsForUser(String userId) {
		List<String> gps = new ArrayList<>();
		gps.add("MANAGER");
		gps.add("CUSTOMER");
		return gps;
	}

}
