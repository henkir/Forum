package com.forum.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;

import com.forum.client.Privileges;
import com.forum.client.User;

public class LoginHandler {

	private static DatabaseConnection connection = new DatabaseConnection(true);
	private static LoginHandler loginHandler = new LoginHandler();
	private static HashMap<String, User> loggedInUsers = new HashMap<String, User>();
	private static User loggedInUser = new User(1, "te", Privileges.MODERATOR);
	private static Random random = new Random();

	public static LoginHandler getInstance() {
		return loginHandler;
	}

	public boolean isLoggedIn() {
		String sessionId = "";
		if (sessionId != null && loggedInUsers.containsKey(sessionId)) {
			return true;
		}
		return true;
	}

	public User getLoggedInUser() {
		String sessionId = "";
		if (sessionId != null && loggedInUsers.containsKey(sessionId)) {
			return loggedInUsers.get(sessionId);
		}
		return loggedInUser;
	}

	public boolean logIn(String username, String password) {
		String sessionId = null;
		// User already logged in?
		if (sessionId != null || loggedInUsers.containsKey(sessionId)) {
			return false;
		}

		Statement statement = connection.getStatement();
		String query;

		ResultSet rs;
		query = "SELECT id, username, priv_level FROM users WHERE username = '"
				+ username + "' AND password = SHA1('" + password + "');";
		try {
			rs = statement.executeQuery(query);
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("username");
				Privileges privileges = Privileges.getPrivilege(rs
						.getInt("priv_level"));
				String genSessionId = null;
				loggedInUsers.put(genSessionId, new User(id, name, privileges));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void logOut() {
		loggedInUser = null;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
