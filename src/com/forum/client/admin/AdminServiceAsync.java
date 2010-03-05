package com.forum.client.admin;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A service providing methods for doing administrative tasks.
 * 
 * @author henrik
 * 
 */
public interface AdminServiceAsync {

	void addCategory(AdminCategory category, AsyncCallback<Boolean> callback);

	void deleteUser(User user, AsyncCallback<Boolean> callback);

	void editCategory(AdminCategory oldCategory, AdminCategory newCategory,
			AsyncCallback<Boolean> callback);

	void getCategories(AsyncCallback<AdminCategory[]> categories);

	void getCategories(String filter, AsyncCallback<AdminCategory[]> categories);

	void getPrivileges(AsyncCallback<Privileges> callback);

	void getUsers(AsyncCallback<User[]> users);

	void getUsers(String filter, AsyncCallback<User[]> users);

	void hasPrivileges(Privileges privilegeLevel,
			AsyncCallback<Integer> callback);

	void removeCategory(AdminCategory category, AsyncCallback<Boolean> callback);

	void setCategories(AdminCategory[] categories,
			AsyncCallback<Boolean> callback);

	void updateUser(User user, AsyncCallback<Boolean> callback);
}
