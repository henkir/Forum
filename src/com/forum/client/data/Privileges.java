package com.forum.client.data;

/**
 * Contains the different Privileges available, and also some help methods.
 * 
 * @author henrik
 * 
 */
public enum Privileges {

	/**
	 * A regular member.
	 */
	MEMBER,
	/**
	 * A moderator with the privileges to edit/remove posts and edit categories
	 * and users.
	 */
	MODERATOR,
	/**
	 * An administrator which has all privileges.
	 */
	ADMINISTRATOR;

	/**
	 * Gets the equivalent integer for a privilege.
	 * 
	 * @param privilege
	 *            the Privileges
	 * @return the equivalent integer
	 */
	public static int getInteger(Privileges privilege) {
		switch (privilege) {
		case MEMBER:
			return 0;
		case MODERATOR:
			return 1;
		case ADMINISTRATOR:
			return 2;
		default:
			return 0;
		}
	}

	/**
	 * Gets the Privileges ordered from lowest to highest Privileges.
	 * 
	 * @return the ordered Privileges
	 */
	public static Privileges[] getOrderedArray() {
		Privileges[] privs = { MEMBER, MODERATOR, ADMINISTRATOR };
		return privs;
	}

	/**
	 * Gets the Privileges equivalent to and integer.
	 * 
	 * @param privilege
	 *            0, 1 or 2
	 * @return the equivalent Privileges
	 */
	public static Privileges getPrivilege(int privilege) {
		switch (privilege) {
		case 0:
			return Privileges.MEMBER;
		case 1:
			return Privileges.MODERATOR;
		case 2:
			return Privileges.ADMINISTRATOR;
		default:
			return Privileges.MEMBER;
		}
	}

	@Override
	public String toString() {
		return this.name().substring(0, 1).toUpperCase()
				+ this.name().substring(1).toLowerCase();
	}

}
