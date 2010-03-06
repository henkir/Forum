package com.forum.client.data;

import java.io.Serializable;

public class PostData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6110974045186942272L;
	private long id;
	private int topicID,authorID;
	private String date, text;
	
	public PostData(){
		
	}
	
	public PostData(long id, int topicID, int authorID, String date, String text) {
		super();
		this.id = id;
		this.topicID = topicID;
		this.authorID = authorID;
		this.date = date;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTopicID() {
		return topicID;
	}

	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

	
}
