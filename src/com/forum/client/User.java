package com.forum.client;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136270130312848857L;
	private String name = null;
	private Privileges privileges = Privileges.MEMBER;

	public User() {

	}

	public User(String name, Privileges privileges) {
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
	public Privileges getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(Privileges privileges) {
		this.privileges = privileges;
	}

}
