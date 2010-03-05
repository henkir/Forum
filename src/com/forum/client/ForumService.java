package com.forum.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.smartgwt.client.widgets.Canvas;

public interface ForumService extends RemoteService{

	
	//gets posts of a thread
	Post[] getPosts(int threadID);
	//gets Threads in a category
	ForumThread[] getThreads(int categoryID, Canvas parent);
	//gets Categories
	String[] getCategories();
	//Adds a new Post to a thread returns ID
	int addPost(String text, int thrID, int authID);
	//Adds a Thread to a category returns ID
	int addThread(String name, int catID, int authID);
	
	
}
