package com.forum.client.admin;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

@RemoteServiceRelativePath("adminTasks")
public interface AdminService extends RemoteService {

	public static class Util {
		public static final String SERVICE_URI = "adminTasks";

		public static AdminServiceAsync getInstance() {
			AdminServiceAsync instance = (AdminServiceAsync) GWT
					.create(AdminService.class);
			ServiceDefTarget target = (ServiceDefTarget) instance;
			target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
			return instance;
		}
	}

	AdminCategory[] getCategories(String sid);

	Privileges getPrivileges(String sid);

	User[] getUsers(String sid);

	int hasPrivileges(Privileges privilegeLevel, String sid);

	String logIn(String username, String password, String sid);

	void logOut(String sid);

	boolean setCategories(AdminCategory[] categories, String sid);

	boolean setUsers(User[] users, String sid);
}
