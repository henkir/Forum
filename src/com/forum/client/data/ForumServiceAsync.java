package com.forum.client.data;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ForumServiceAsync {
	/**
	 * Adds a new Post to a Thread returns the ID.
	 * 
	 * @param text
	 *            the post contents
	 * @param thrID
	 *            the thread ID
	 * @param authID
	 *            the author ID
	 * @return the ID of the Post
	 */
	void addPost(String text, int thrID, int authID,
			AsyncCallback<Integer> callback);

	/**
	 * Adds a Thread to a Category and returns the ID.
	 * 
	 * @param name
	 *            the name of the thread
	 * @param catID
	 *            the category ID
	 * @param authID
	 *            the author ID
	 * @return the ID of the Thread
	 */
	void addThread(String name, int catID, int authID,
			AsyncCallback<Integer> callback);

	/**
	 * Gets all Categories.
	 * 
	 * @return the Categories
	 */
	void getCategories(AsyncCallback<CategoryData[]> callback);

	/**
	 * Gets all Posts in a Thread
	 * 
	 * @param threadID
	 *            the ID of the Thread
	 * @return the Posts
	 */
	void getPosts(int threadID, AsyncCallback<PostData[]> callback);

	/**
	 * Get the privileges of the currently logged in User.
	 * 
	 * @param sid
	 *            the session ID
	 * @return the Privileges
	 */
	void getPrivileges(String sid, AsyncCallback<Privileges> callback);

	/**
	 * Gets all Threads in a Category.
	 * 
	 * @param categoryID
	 *            the Category ID
	 * @return the Threads
	 */
	void getThreads(int categoryID, AsyncCallback<TopicData[]> callback);

	/**
	 * Gets the currently logged in User.
	 * 
	 * @param sid
	 *            the session ID
	 * @return the logged in User, or null
	 */
	void getUser(String sid, AsyncCallback<User> user);

	/**
	 * Gets all Users that the currently logged in User is privileged to modify.
	 * 
	 * @param sid
	 *            the session ID
	 * @return the Users
	 */
	void getUsers(String sid, AsyncCallback<User[]> users);

	/**
	 * Checks if a User has enough Privileges.
	 * 
	 * @param privilegeLevel
	 *            the Privileges required
	 * @param sid
	 *            the session ID
	 * @return 0 if User is not logged in, 1 if the Privileges is too low, 2 if
	 *         User has the Privileges
	 */
	void hasPrivileges(Privileges privilegeLevel, String sid,
			AsyncCallback<Integer> callback);

	/**
	 * Logs in a User if username and password are correct. Make sure to use the
	 * new session ID.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param sid
	 *            the session ID
	 * @return the new session ID
	 */
	void logIn(String username, String password, String sid,
			AsyncCallback<String> callback);

	/**
	 * Logs out the currently logged in User.
	 * 
	 * @param sid
	 *            the session ID
	 */
	void logOut(String sid, AsyncCallback<String> callback);

	/**
	 * Registers a user with the specified details.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return true if successful, otherwise false
	 */
	void register(String username, String password,
			AsyncCallback<Boolean> callback);

	/**
	 * Sets the Categories in the database to match those of the array, if the
	 * User has the privileges.
	 * 
	 * @param categories
	 *            the array with Categories
	 * @param sid
	 *            the session ID
	 * @return true if successful, otherwise false
	 */
	void setCategories(CategoryData[] categories, String sid,
			AsyncCallback<Boolean> callback);

	/**
	 * Sets the Users in the database to match those of the array, with the
	 * exception of those that have higher privileges.
	 * 
	 * @param users
	 *            the array of Users
	 * @param sid
	 *            the session ID
	 * @return true if successful, otherwise false
	 */
	void setUsers(User[] users, String sid, AsyncCallback<Boolean> callback);

}