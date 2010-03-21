package com.forum.client.data;

import java.io.Serializable;

/**
 * Contains data about a topic.
 * 
 * @author henho106 + jonhe442
 * 
 */
public class TopicData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2723527676068586212L;
	int id, categoryID, authorID;
	String name, date;

	/**
	 * Creates a new TopicData.
	 * 
	 * @param id
	 *            the ID of the topic
	 * @param categoryID
	 *            the ID of the category that the topic belongs to
	 * @param authorID
	 *            the ID of the author
	 * @param name
	 *            the name of the topic
	 * @param date
	 *            the date posted
	 */
	public TopicData(int id, int categoryID, int authorID, String name,
			String date) {
		super();
		this.id = id;
		this.categoryID = categoryID;
		this.authorID = authorID;
		this.name = name;
		this.date = date;
	}

	/**
	 * Creates a new TopicData.
	 */
	public TopicData() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID
	 *            the categoryID to set
	 */
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @return the authorID
	 */
	public int getAuthorID() {
		return authorID;
	}

	/**
	 * @param authorID
	 *            the authorID to set
	 */
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

}
