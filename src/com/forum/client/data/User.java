package com.forum.client.data;

import java.io.Serializable;

/**
 * A class for storing information about a user.
 * 
 * @author henrik
 * 
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136270130312848857L;
	private int id = 0;

	private String name = null;

	private Privileges privileges = Privileges.MEMBER;

	private boolean changed = false;

	/**
	 * Creates a new User.
	 */
	public User() {

	}

	/**
	 * Creates a new User.
	 * 
	 * @param id
	 *            the ID of the user
	 * @param name
	 *            the name of the user
	 * @param privileges
	 *            the Privileges of the user
	 */
	public User(int id, String name, Privileges privileges) {
		this.name = name;
		this.privileges = privileges;
		this.id = id;
	}

	/**
	 * Creates a new User.
	 * 
	 * @param name
	 *            the name of the user
	 * @param privileges
	 *            the Privileges of the user
	 */
	public User(String name, Privileges privileges) {
		this.name = name;
		this.privileges = privileges;
	}

	/**
	 * Creates a new User.
	 * 
	 * @param name
	 *            the name of the user
	 * @param privileges
	 *            the Privileges of the user
	 * @param changed
	 *            if the user has changed
	 */
	public User(String name, Privileges privileges, boolean changed) {
		this.name = name;
		this.privileges = privileges;
		this.changed = changed;
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
	 * @return the privileges
	 */
	public Privileges getPrivileges() {
		return privileges;
	}

	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed
	 *            the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
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

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(Privileges privileges) {
		this.privileges = privileges;
	}

}
