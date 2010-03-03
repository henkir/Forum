package com.forum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {

	void getUsers(AsyncCallback<User[]> users);

	void getUsers(String filter, AsyncCallback<User[]> users);

	void getCategories(AsyncCallback<AdminCategory[]> categories);

	void getCategories(String filter, AsyncCallback<AdminCategory[]> categories);

	void deleteUser(User user, AsyncCallback<Boolean> callback);

	void setUser(User user, AsyncCallback<Boolean> callback);

	void addCategory(AdminCategory category, AsyncCallback<Boolean> callback);

	void editCategory(AdminCategory oldCategory, AdminCategory newCategory,
			AsyncCallback<Boolean> callback);

	void removeCategory(AdminCategory category, AsyncCallback<Boolean> callback);

	void setCategoryPosition(AdminCategory category, int position,
			AsyncCallback<Boolean> callback);

	void hasPrivileges(int privilegeLevel, AsyncCallback<Integer> callback);

}
