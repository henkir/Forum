package com.forum.client.admin;

import java.io.Serializable;

public class AdminCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9180699994616026038L;
	private String name = null;
	private String description = null;
	private int id = 0;

	public AdminCategory() {

	}

	public AdminCategory(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
