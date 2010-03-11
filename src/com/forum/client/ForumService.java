package com.forum.client;

import com.forum.client.data.CategoryData;
import com.forum.client.data.PostData;
import com.forum.client.data.TopicData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("forumTasks")
public interface ForumService extends RemoteService {

	// Adds a new Post to a thread returns ID
	int addPost(String text, int thrID, int authID);

	// Adds a Thread to a category returns ID
	int addThread(String name, int catID, int authID);

	// //gets Categories
	CategoryData[] getCategories();

	CategoryData[] getCategories(String sid);

	// //gets posts of a thread
	PostData[] getPosts(int threadID);

	Privileges getPrivileges(String sid);

	// //gets Threads in a category
	TopicData[] getThreads(int categoryID);

	User getUser(String sid);

	User[] getUsers(String sid);

	int hasPrivileges(Privileges privilegeLevel, String sid);

	String logIn(String username, String password, String sid);

	void logOut(String sid);

	boolean setCategories(CategoryData[] categories, String sid);

	boolean setUsers(User[] users, String sid);

}
