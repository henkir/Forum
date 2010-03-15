package com.forum.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import com.forum.client.data.Privileges;
import com.forum.client.data.User;

/**
 * A static class that keeps track of logged in users and provides methods for
 * logging in/out user and checking if a user is logged in or not.
 * 
 * @author henrik
 * 
 */
public class LoginHandler {

	/**
	 * Connection to the database.
	 */
	private static DatabaseConnection connection = new DatabaseConnection(true);
	/**
	 * Contains mapping from session id to logged in users.
	 */
	private static HashMap<String, User> loggedInUsers = new HashMap<String, User>();
	/**
	 * Contains mapping from session id to last activity.
	 */
	private static HashMap<String, Date> lastActivity = new HashMap<String, Date>();
	/**
	 * Random generator for generating a random session id.
	 */
	private static Random random = new Random();
	/**
	 * The maximum duration of the session. 30 minutes.
	 */
	private static final int duration = 1000 * 60 * 30;
	/**
	 * Characters that can be used in the session id.
	 */
	private static final char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'Y',
			'Z' };

	/**
	 * Removes old logins from the HashMaps.
	 */
	private static void cleanUp() {
		String sid;
		for (Entry<String, Date> entry : lastActivity.entrySet()) {
			if (entry.getValue().before(
					new Date(System.currentTimeMillis() - duration))) {
				sid = entry.getKey();
				loggedInUsers.remove(sid);
				lastActivity.remove(sid);
			}
		}
	}

	/**
	 * Generates a random session id that is not currently in use.
	 * 
	 * @return the generated session id
	 */
	private static String generateSid() {
		if (random.nextInt(20) == 0) {
			random = new Random();
		}

		StringBuilder sid = new StringBuilder();
		while (sid.length() == 0 || lastActivity.containsKey(sid.toString())) {
			sid.delete(0, sid.length());

			for (int i = 0; i < 40; i++) {
				sid.append(chars[random.nextInt(chars.length)]);
			}
		}
		return sid.toString();
	}

	/**
	 * Gets the user associated with the session id.
	 * 
	 * @param sid
	 *            the session id
	 * @return the user with the session id, or null
	 */
	public static User getUser(String sid) {

		return loggedInUsers.get(sid);
	}

	/**
	 * Checks if a user is logged in. Also updates last activity so the users
	 * session won't expire.
	 * 
	 * @param sid
	 *            the session id
	 * @return true if user is logged in, or false
	 */
	public static boolean isLoggedIn(String sid) {
		if (sid != null && loggedInUsers.containsKey(sid)) {
			lastActivity.put(sid, new Date(System.currentTimeMillis()));
			if (random.nextInt(20) == 0) {
				cleanUp();
			}
			return true;
		}
		return false;
	}

	/**
	 * Logs in a user if the username and password match.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param sid
	 *            the current session id
	 * @return the new session id
	 */
	public static String logIn(String username, String password, String sid) {
		String query = "SELECT id, username, priv_level FROM users WHERE username = ? AND password = SHA1(?);";
		PreparedStatement statement = connection.getPreparedStatement(query);

		if (sid != null && loggedInUsers.containsKey(sid)) {
			return sid;
		}

		ResultSet rs;
		try {
			statement.setString(1, username);
			statement.setString(2, password);
			rs = statement.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("username");
				Privileges privileges = Privileges.getPrivilege(rs
						.getInt("priv_level"));
				User user = new User(id, name, privileges);
				sid = generateSid();
				loggedInUsers.put(sid, user);
				lastActivity.put(sid, new Date(System.currentTimeMillis()));
				return sid;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Logs out the user with the associated session id.
	 * 
	 * @param sid
	 *            the session id
	 */
	public static String logOut(String sid) {
		loggedInUsers.remove(sid);
		lastActivity.remove(sid);
		return generateSid();
	}

	private LoginHandler() {

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
