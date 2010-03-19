package com.forum.client.data;

import java.io.Serializable;

/**
 * Contains data about a post.
 * 
 * @author henrik
 * 
 */
public class PostData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6110974045186942272L;
	private long id;
	private int topicID, authorID;
	private String date, text;

	/**
	 * Creates a new PostData.
	 */
	public PostData() {

	}

	/**
	 * Creates a new PostData.
	 * 
	 * @param id
	 *            the ID of the post
	 * @param topicID
	 *            the ID of the topic that the post belongs to
	 * @param authorID
	 *            the ID of the author
	 * @param date
	 *            the date posted
	 * @param text
	 *            the contents of the post
	 */
	public PostData(long id, int topicID, int authorID, String date, String text) {
		super();
		this.id = id;
		this.topicID = topicID;
		this.authorID = authorID;
		this.date = date;
		this.text = text;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the topicID
	 */
	public int getTopicID() {
		return topicID;
	}

	/**
	 * @param topicID
	 *            the topicID to set
	 */
	public void setTopicID(int topicID) {
		this.topicID = topicID;
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

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
