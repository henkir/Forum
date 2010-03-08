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

	void getCategories(String sid, AsyncCallback<AdminCategory[]> categories);

	void getPrivileges(String sid, AsyncCallback<Privileges> callback);

	void getUsers(String sid, AsyncCallback<User[]> users);

	void hasPrivileges(Privileges privilegeLevel, String sid,
			AsyncCallback<Integer> callback);

	void logIn(String username, String password, String sid,
			AsyncCallback<String> callback);

	void logOut(String sid, AsyncCallback<Void> callback);

	void setCategories(AdminCategory[] categories, String sid,
			AsyncCallback<Boolean> callback);

	void setUsers(User[] users, String sid, AsyncCallback<Boolean> callback);
}
