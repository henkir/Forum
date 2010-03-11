package com.forum.client;

import com.forum.client.data.CategoryData;
import com.forum.client.data.PostData;
import com.forum.client.data.TopicData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForumServiceAsync {

	void addPost(String text, int thrID, int authID,
			AsyncCallback<Integer> callback);

	void addThread(String name, int catID, int authID,
			AsyncCallback<Integer> callback);

	void getCategories(AsyncCallback<CategoryData[]> callback);

	void getCategories(String sid, AsyncCallback<CategoryData[]> categories);

	void getPosts(int threadID, AsyncCallback<PostData[]> callback);

	void getPrivileges(String sid, AsyncCallback<Privileges> callback);

	void getThreads(int categoryID, AsyncCallback<TopicData[]> callback);

	void getUser(String sid, AsyncCallback<User> user);

	void getUsers(String sid, AsyncCallback<User[]> users);

	void hasPrivileges(Privileges privilegeLevel, String sid,
			AsyncCallback<Integer> callback);

	void logIn(String username, String password, String sid,
			AsyncCallback<String> callback);

	void logOut(String sid, AsyncCallback<Void> callback);

	void setCategories(CategoryData[] categories, String sid,
			AsyncCallback<Boolean> callback);

	void setUsers(User[] users, String sid, AsyncCallback<Boolean> callback);

}
