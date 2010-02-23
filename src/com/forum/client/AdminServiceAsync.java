package com.forum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AdminServiceAsync {

	void deleteUser(String username, AsyncCallback<Boolean> callback);

	void setUserPrivileges(String username, int privilegeLevel,
			AsyncCallback<Boolean> callback);

	void addCategory(String category, String description,
			AsyncCallback<Boolean> callback);

	void editCategory(String oldCategory, String newCategory,
			String description, AsyncCallback<Boolean> callback);

	void removeCategory(String category, AsyncCallback<Boolean> callback);

	void setCategoryPosition(String category, int position,
			AsyncCallback<Boolean> callback);

	void hasPrivileges(int privilegeLevel, AsyncCallback<Integer> callback);

}
