package com.forum.client.data;

import java.io.Serializable;

/**
 * Contains data about a category.
 * 
 * @author henrik
 * 
 */
public class CategoryData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6947233962770339525L;
	/**
	 * The name of the category.
	 */
	private String name = null;
	/**
	 * The description of the category.
	 */
	private String description = null;
	/**
	 * The ID of the category.
	 */
	private int id = 0;

	/**
	 * Creates a new CategoryData.
	 */
	public CategoryData() {

	}

	/**
	 * Creates a new CategoryData.
	 * 
	 * @param id
	 *            the ID of the category
	 * @param name
	 *            the name of the category
	 */
	public CategoryData(int id, String name) {
		this.name = name;
		this.id = id;
	}

	/**
	 * Creates a new CategoryData.
	 * 
	 * @param id
	 *            the ID of the category
	 * @param name
	 *            the name of the category
	 * @param description
	 *            the description of the category
	 */
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
