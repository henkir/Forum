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

	void getUsers(AsyncCallback<User[]> users);

	void getUsers(String filter, AsyncCallback<User[]> users);

	void getCategories(AsyncCallback<AdminCategory[]> categories);

	void getCategories(String filter, AsyncCallback<AdminCategory[]> categories);

	void deleteUser(User user, AsyncCallback<Boolean> callback);

	void updateUser(User user, AsyncCallback<Boolean> callback);

	void addCategory(AdminCategory category, AsyncCallback<Boolean> callback);

	void editCategory(AdminCategory oldCategory, AdminCategory newCategory,
			AsyncCallback<Boolean> callback);

	void removeCategory(AdminCategory category, AsyncCallback<Boolean> callback);

	void setCategoryPosition(AdminCategory category, int position,
			AsyncCallback<Boolean> callback);

	void hasPrivileges(Privileges privilegeLevel,
			AsyncCallback<Integer> callback);

	void getPrivileges(AsyncCallback<Privileges> callback);
}
