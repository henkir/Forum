package com.forum.client.admin;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("adminTasks")
public interface AdminService extends RemoteService {

	// Users
	User[] getUsers();

	User[] getUsers(String filter);

	boolean deleteUser(User user);

	boolean updateUser(User user);

	// Categories
	AdminCategory[] getCategories();

	AdminCategory[] getCategories(String filter);

	boolean addCategory(AdminCategory category);

	boolean editCategory(AdminCategory oldCategory, AdminCategory newCategory);

	boolean removeCategory(AdminCategory category);

	boolean setCategoryPosition(AdminCategory category, int position);

	// User checking
	int hasPrivileges(Privileges privilegeLevel);

	Privileges getPrivileges();
}
