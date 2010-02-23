package com.forum.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("adminTasks")
public interface AdminService extends RemoteService {

	boolean deleteUser(String username);

	boolean setUserPrivileges(String username, int privilegeLevel);

	boolean addCategory(String category, String description);

	boolean editCategory(String oldCategory, String newCategory,
			String description);

	boolean removeCategory(String category);

	boolean setCategoryPosition(String category, int position);

	int hasPrivileges(int privilegeLevel);

}
