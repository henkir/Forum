package com.forum.client.data;

import java.io.Serializable;

public class CategoryData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6947233962770339525L;
	private String name = null;
	private String description = null;

	private int id = 0;

	public CategoryData() {

	}

	public CategoryData(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public CategoryData(int id, String name, String description) {
		this.name = name;
		this.description = description;
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
