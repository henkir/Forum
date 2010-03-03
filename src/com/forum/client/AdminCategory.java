package com.forum.client;

import java.io.Serializable;

public class AdminCategory implements Serializable {

	private String name = null;
	private String description = null;
	private int position = 0;

	public AdminCategory() {

	}

	public AdminCategory(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public AdminCategory(String name, String description, int position) {
		this.name = name;
		this.description = description;
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
