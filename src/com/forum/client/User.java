package com.forum.client;

import java.io.Serializable;

public class User implements Serializable {

	private String name = null;
	private int privileges = 0;

	public User() {

	}

	public User(String name, int privileges) {
		this.name = name;
		this.privileges = privileges;
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
	 * @return the privileges
	 */
	public int getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(int privileges) {
		this.privileges = privileges;
	}

}
