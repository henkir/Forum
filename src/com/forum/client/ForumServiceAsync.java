package com.forum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;

public interface ForumServiceAsync {

	void getPosts(int threadID,AsyncCallback<Post[]> callback);

	void getThreads(int categoryID, Canvas parent, AsyncCallback<ForumThread[]> callback);

	void getCategories(AsyncCallback<String[]> callback);

	void addPost(String text,int thrID,int authID, AsyncCallback<Integer> callback);

	void addThread(String name, int catID, int authID, AsyncCallback<Integer> callback);

}
