package com.forum.client.data;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("forumTasks")
public interface ForumService extends RemoteService {

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
	int addPost(String text, int thrID, int authID);

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
	int addThread(String name, int catID, int authID);

	/**
	 * Gets all Categories.
	 * 
	 * @return the Categories
	 */
	CategoryData[] getCategories();

	/**
	 * Gets all Posts in a Thread
	 * 
	 * @param threadID
	 *            the ID of the Thread
	 * @return the Posts
	 */
	PostData[] getPosts(int threadID);

	/**
	 * Get the privileges of the currently logged in User.
	 * 
	 * @param sid
	 *            the session ID
	 * @return the Privileges
	 */
	Privileges getPrivileges(String sid);

	/**
	 * Gets all Threads in a Category.
	 * 
	 * @param categoryID
	 *            the Category ID
	 * @return the Threads
	 */
	TopicData[] getThreads(int categoryID);

	/**
	 * Gets the currently logged in User.
	 * 
	 * @param sid
	 *            the session ID
	 * @return the logged in User, or null
	 */
	User getUser(String sid);

	/**
	 * Gets all Users that the currently logged in User is privileged to modify.
	 * 
	 * @param sid
	 *            the session ID
	 * @return the Users
	 */
	User[] getUsers(String sid);

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
	int hasPrivileges(Privileges privilegeLevel, String sid);

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
	String logIn(String username, String password, String sid);

	/**
	 * Logs out the currently logged in User.
	 * 
	 * @param sid
	 *            the session ID
	 */
	String logOut(String sid);

	/**
	 * Registers a user with the specified details.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return true if successful, otherwise false
	 */
	boolean register(String username, String password);

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
	boolean setCategories(CategoryData[] categories, String sid);

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
	boolean setUsers(User[] users, String sid);

}
