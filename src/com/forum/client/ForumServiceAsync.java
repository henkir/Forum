package com.forum.client;

import com.forum.client.data.CategoryData;
import com.forum.client.data.PostData;
import com.forum.client.data.TopicData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;

public interface ForumServiceAsync {

	void getPosts(int threadID,AsyncCallback<PostData[]> callback);

	void getThreads(int categoryID, AsyncCallback<TopicData[]> callback);

	void getCategories(AsyncCallback<CategoryData[]> callback);

	void addPost(String text,int thrID,int authID, AsyncCallback<Integer> callback);

	void addThread(String name, int catID, int authID, AsyncCallback<Integer> callback);

}
