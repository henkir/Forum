package com.forum.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("adminTasks")
public interface AdminService extends RemoteService {

	// Users
	User[] getUsers();

	User[] getUsers(String filter);

	boolean deleteUser(User user);

	boolean setUser(User user);

	// Categories
	AdminCategory[] getCategories();

	AdminCategory[] getCategories(String filter);

	boolean addCategory(AdminCategory category);

	boolean editCategory(AdminCategory oldCategory, AdminCategory newCategory);

	boolean removeCategory(AdminCategory category);

	boolean setCategoryPosition(AdminCategory category, int position);

	// User checking
	int hasPrivileges(int privilegeLevel);

}
