package com.forum.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.forum.client.data.CategoryData;
import com.forum.client.data.ForumService;
import com.forum.client.data.PostData;
import com.forum.client.data.Privileges;
import com.forum.client.data.TopicData;
import com.forum.client.data.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server end of the AsyncCallbacks.
 * 
 * @author henrik
 * 
 */
public class ForumServiceImpl extends RemoteServiceServlet implements
		ForumService {

	private static final long serialVersionUID = 3531226764827229641L;
	/**
	 * The connection to the database.
	 */
	private DatabaseConnection connection = null;

	/**
	 * Creates a new ForumServiceImpl.
	 */
	public ForumServiceImpl() {

		connection = new DatabaseConnection(true);
	}

	@Override
	public int addPost(String text, int thrID, int authID) {

		String query = "INSERT INTO posts(topic_id,author_id,time_posted,post) VALUES (?, ?, NOW(), ?);";

		try {
			PreparedStatement statement = connection
					.getPreparedStatement(query);
			statement.setInt(1, thrID);
			statement.setInt(2, authID);
			statement.setString(3, text);
			statement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int addThread(String name, int catID, int authID) {
		String query = "INSERT INTO topics(category_id, author_id, name, time_created) VALUES (?, ?, ?, NOW());";

		try {
			PreparedStatement statement = connection
					.getPreparedStatement(query);
			statement.setInt(1, catID);
			statement.setInt(2, authID);
			statement.setString(3, name);
			statement.executeUpdate();
			String selectQuery = "SELECT id FROM topics WHERE category_id = ? AND author_id = ? AND name = ? AND time_created < (NOW() + 1);";
			statement = connection.getPreparedStatement(selectQuery);
			statement.setInt(1, catID);
			statement.setInt(2, authID);
			statement.setString(3, name);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {

		}
		return 0;
	}

	@Override
	public CategoryData[] getCategories() {
		ArrayList<CategoryData> result = new ArrayList<CategoryData>();
		ResultSet rs;
		String query = "SELECT id, name, description FROM categories ORDER BY position;";
		Statement statement = connection.getStatement();
		try {
			rs = statement.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				result.add(new CategoryData(id, name, description));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result.toArray(new CategoryData[0]);
	}

	@Override
	public PostData[] getPosts(int threadID) {

		ArrayList<PostData> result = new ArrayList<PostData>();
		ResultSet rs;
		String query = "SELECT t1.id, t1.time_posted, t1.post, t2.id AS author FROM posts AS t1 INNER JOIN users AS t2 ON t1.author_id = t2.id WHERE t1.topic_id = ? ORDER BY t1.time_posted;";

		try {
			PreparedStatement statement = connection
					.getPreparedStatement(query);
			statement.setInt(1, threadID);
			rs = statement.executeQuery();
			while (rs.next()) {
				String date = rs.getString("time_posted");
				String text = rs.getString("post");
				long id = rs.getLong("id");
				int authID = rs.getInt("author");

				result.add(new PostData(id, threadID, authID, date, text));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result.toArray(new PostData[0]);
	}

	@Override
	public Privileges getPrivileges(String sid) {
		User user = LoginHandler.getUser(sid);
		if (user != null) {
			return user.getPrivileges();
		} else {
			return null;
		}
	}

	@Override
	public TopicData[] getThreads(int categoryID) {
		ArrayList<TopicData> result = new ArrayList<TopicData>();
		ResultSet rs;
		String query = "SELECT t1.id, t1.name, t1.time_created,t1.category_id, t2.id AS author FROM topics AS t1 INNER JOIN users AS t2 ON t1.author_id = t2.id WHERE t1.category_id = ? ORDER BY t1.time_created;";

		try {
			PreparedStatement statement = connection
					.getPreparedStatement(query);
			statement.setInt(1, categoryID);
			rs = statement.executeQuery();
			while (rs.next()) {
				String date = rs.getString("time_created");
				String name = rs.getString("name");
				int id = rs.getInt("id");
				int catID = rs.getInt("category_id");
				int authID = rs.getInt("author");
				result.add(new TopicData(id, catID, authID, name, date));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result.toArray(new TopicData[0]);
	}

	@Override
	public User getUser(String sid) {
		return LoginHandler.getUser(sid);
	}

	@Override
	public User[] getUsers(String sid) {
		if (hasPrivileges(Privileges.MODERATOR, sid) == 2) {
			if (connection == null) {
				return null;
			}

			int privileges = Privileges.getInteger(LoginHandler.getUser(sid)
					.getPrivileges());
			String query = "SELECT username, priv_level FROM users WHERE priv_level <= ? AND username != ?;";
			try {
				PreparedStatement statement = connection
						.getPreparedStatement(query);
				statement.setInt(1, privileges);
				statement.setString(2, LoginHandler.getUser(sid).getName());
				ResultSet rs = statement.executeQuery();
				ArrayList<User> users = new ArrayList<User>();
				Privileges tempPrivs;
				String tempName;
				while (rs.next()) {
					tempPrivs = Privileges
							.getPrivilege(rs.getInt("priv_level"));
					tempName = rs.getString("username");
					users.add(new User(tempName, tempPrivs));
				}
				User[] u = new User[0];
				return users.toArray(u);
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return null;
	}

	@Override
	public int hasPrivileges(Privileges privilegeLevel, String sid) {
		if (LoginHandler.isLoggedIn(sid)) {
			User user = LoginHandler.getUser(sid);
			if (Privileges.getInteger(user.getPrivileges()) >= Privileges
					.getInteger(privilegeLevel)) {
				return 2;
			}
			return 1;
		}
		return 0;
	}

	@Override
	public boolean isLoggedIn(String sid) {
		return LoginHandler.isLoggedIn(sid);
	}

	@Override
	public String logIn(String username, String password, String sid) {
		return LoginHandler.logIn(username, password, sid);
	}

	@Override
	public String logOut(String sid) {
		return LoginHandler.logOut(sid);
	}

	@Override
	public boolean setCategories(CategoryData[] categories, String sid) {
		if (hasPrivileges(Privileges.MODERATOR, sid) == 2) {
			String querySelect = "";
			String queryDelete = "";
			String queryInsert = "";
			String queryUpdate = "";
			String querySelect2 = "";

			try {
				querySelect = "SELECT id FROM categories;";
				PreparedStatement statement = connection
						.getPreparedStatement(querySelect);
				String name;
				String description;

				ResultSet rs;

				rs = statement.executeQuery();
				int tempId;
				boolean remove;
				while (rs.next()) {
					remove = true;
					tempId = rs.getInt("id");
					if (tempId > 0) {
						for (int i = 0; i < categories.length; i++) {
							if (tempId == categories[i].getId()) {
								remove = false;
								break;
							}
						}
						if (remove) {
							queryDelete = "DELETE FROM categories WHERE id = ?;";
							statement = connection
									.getPreparedStatement(queryDelete);
							statement.setInt(1, tempId);
							statement.executeUpdate();
						}
					}
				}
				for (int i = 0; i < categories.length; i++) {
					name = categories[i].getName();
					description = categories[i].getDescription();
					if (name.length() >= 3 && name.length() <= 20
							&& description.length() <= 100) {
						querySelect2 = "SELECT id FROM categories WHERE id = ?;";
						statement = connection
								.getPreparedStatement(querySelect2);
						statement.setInt(1, categories[i].getId());
						rs = statement.executeQuery();
						if (rs.next()) {
							queryUpdate = "UPDATE categories SET name = ?, description = ?, position = ? WHERE id = ?;";
							statement = connection
									.getPreparedStatement(queryUpdate);
							statement.setString(1, name);
							statement.setString(2, description);
							statement.setInt(3, i + 1);
							statement.setInt(4, categories[i].getId());
							statement.executeUpdate();
						} else {
							queryInsert = "INSERT INTO categories(name, description, position) VALUES(?, ?, ?);";
							statement = connection
									.getPreparedStatement(queryInsert);
							statement.setString(1, name);
							statement.setString(2, description);
							statement.setInt(3, i);
							statement.executeUpdate();
						}
					}
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean setUsers(User[] users, String sid) {
		if (hasPrivileges(Privileges.MODERATOR, sid) == 2) {
			String querySelect = "";
			String queryDelete = "";
			String queryUpdate = "";

			try {
				querySelect = "SELECT username FROM users WHERE priv_level <= ?;";
				int curPrivs = Privileges.getInteger(LoginHandler.getUser(sid)
						.getPrivileges());
				PreparedStatement statement = connection
						.getPreparedStatement(querySelect);
				statement.setInt(1, curPrivs);

				String name;
				Privileges privileges;
				ResultSet rs;

				rs = statement.executeQuery();
				String tempName;
				boolean remove;
				while (rs.next()) {
					remove = true;
					tempName = rs.getString("username");

					for (int i = 0; i < users.length; i++) {
						if (tempName.equals(users[i].getName())) {
							remove = false;
							break;
						}
					}
					if (remove) {
						queryDelete = "DELETE FROM users WHERE username = ?;";
						statement = connection
								.getPreparedStatement(queryDelete);
						statement.setString(1, tempName);
						statement.executeUpdate();
					}
				}
				boolean edited;
				for (int i = 0; i < users.length; i++) {
					edited = users[i].isChanged();
					if (edited) {
						name = users[i].getName();
						privileges = users[i].getPrivileges();
						queryUpdate = "UPDATE users SET priv_level = ? WHERE username = ?;";
						statement = connection
								.getPreparedStatement(queryUpdate);
						statement.setInt(1, Privileges.getInteger(privileges));
						statement.setString(2, name);
						statement.executeUpdate();
					}

				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean register(String username, String password) {
		String existsQuery = "SELECT username FROM users WHERE username = ?";
		String query = "INSERT INTO users(username, password) VALUES(?, SHA1(?));";
		PreparedStatement statement = connection
				.getPreparedStatement(existsQuery);
		try {
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return false;
			}
			statement = connection.getPreparedStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removePost(long id) {
		String query = "DELETE FROM posts WHERE id = ?";
		PreparedStatement statement = connection.getPreparedStatement(query);
		try {
			statement.setLong(1, id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User getUser(int id) {
		String query = "SELECT username, priv_level FROM users WHERE id = ?";
		PreparedStatement statement = connection.getPreparedStatement(query);
		try {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String username = rs.getString("username");
				Privileges privs = Privileges.getPrivilege(rs
						.getInt("priv_level"));
				User user = new User(id, username, privs);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean usernameExists(String username) {
		String existsQuery = "SELECT username FROM users WHERE username = ?";
		PreparedStatement statement = connection
				.getPreparedStatement(existsQuery);
		try {
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updatePost(long id, String text) {
		String query = "UPDATE posts SET post = ? WHERE id = ?";
		PreparedStatement statement = connection.getPreparedStatement(query);
		try {
			statement.setString(1, text);
			statement.setLong(2, id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
