package com.forum.client.data;

import java.io.Serializable;

public class TopicData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2723527676068586212L;
	int id,categoryID,authorID;
	String name,date;
	public TopicData(int id, int categoryID, int authorID, String name,
			String date) {
		super();
		this.id = id;
		this.categoryID = categoryID;
		this.authorID = authorID;
		this.name = name;
		this.date = date;
	}
	
	public TopicData(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public int getAuthorID() {
		return authorID;
	}
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
