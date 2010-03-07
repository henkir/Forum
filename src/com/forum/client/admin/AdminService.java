package com.forum.client.admin;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("adminTasks")
public interface AdminService extends RemoteService {

	boolean addCategory(AdminCategory category);

	boolean deleteUser(User user);

	boolean editCategory(AdminCategory oldCategory, AdminCategory newCategory);

	AdminCategory[] getCategories();

	AdminCategory[] getCategories(String filter);

	Privileges getPrivileges();

	User[] getUsers();

	User[] getUsers(String filter);

	int hasPrivileges(Privileges privilegeLevel);

	boolean removeCategory(AdminCategory category);

	boolean setCategories(AdminCategory[] categories);

	boolean setUsers(User[] users);

	boolean updateUser(User user);

	boolean logIn(String username, String password);

	void logOut();
}
