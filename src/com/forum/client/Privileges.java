package com.forum.client;

public enum Privileges {

	MEMBER, MODERATOR, ADMINISTRATOR;

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

	public static Privileges[] getOrderedArray() {
		Privileges[] privs = { MEMBER, MODERATOR, ADMINISTRATOR };
		return privs;
	}

	public String toString() {
		return this.name().substring(0, 1).toUpperCase()
				+ this.name().substring(1).toLowerCase();
	}

}
