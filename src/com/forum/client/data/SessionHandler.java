package com.forum.client.data;

import java.util.Date;

import com.google.gwt.user.client.Cookies;

/**
 * A static class for handling the session on the client side.
 * 
 * @author henrik
 * 
 */
public class SessionHandler {

	private static Date expires = null;
	private static final long duration = 1000 * 60 * 30; // 30 minutes

	private SessionHandler() {

	}

	/**
	 * Sets the session ID.
	 * 
	 * @param sessionId
	 *            the session ID
	 */
	public static void setSessionId(String sessionId) {
		expires = new Date(System.currentTimeMillis() + duration);
		Cookies.setCookie("sid", sessionId, expires);
	}

	/**
	 * Gets the session ID.
	 * 
	 * @return the session ID
	 */
	public static String getSessionId() {
		return Cookies.getCookie("sid");
	}

}
