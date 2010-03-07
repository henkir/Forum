package com.forum.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.forum.client.admin.AdminCategory;
import com.forum.client.admin.AdminService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("unused")
public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = 7552004959637612351L;
	private DatabaseConnection connection = new DatabaseConnection(true);
	private LoginHandler loginHandler = LoginHandler.getInstance();
	private HttpServletRequest request;
	private HttpSession session;
	private String test;
	private final int authorized = 2;
	private final int loggedIn = 1;
	private final int notLoggedIn = 0;

	public AdminServiceImpl() {

	}

	@Override
	public boolean addCategory(AdminCategory category) {
		if (hasPrivileges(Privileges.MODERATOR) == authorized) {
			String name = category.getName();
			String description = category.getDescription();
			String query = "INSERT INTO categories(name, description, position) VALUES('"
					+ name + "', '" + description + "', MAX(position));";
			return queryUpdate(query);
		}
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		Privileges privileges = user.getPrivileges();
		if (hasPrivileges(Privileges.MODERATOR) == authorized
				&& hasPrivileges(privileges) == authorized) {
			String name = user.getName();
			String query = "DELETE FROM users WHERE name='" + name + "');";
			return queryUpdate(query);
		}
		return false;
	}

	@Override
	public boolean editCategory(AdminCategory oldCategory,
			AdminCategory newCategory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AdminCategory[] getCategories() {
		if (hasPrivileges(Privileges.MODERATOR) == 2) {
			if (connection == null) {
				return null;
			}

			String query = "SELECT id, name, description, position FROM categories ORDER BY position;";
			Statement statement = connection.getStatement();
			try {
				ResultSet rs = statement.executeQuery(query);
				ArrayList<AdminCategory> categories = new ArrayList<AdminCategory>();
				String tempDescription;
				String tempName;
				int tempId;
				int tempPos;
				while (rs.next()) {
					tempId = rs.getInt("id");
					tempDescription = rs.getString("description");
					tempName = rs.getString("name");
					categories.add(new AdminCategory(tempId, tempName,
							tempDescription));
				}
				AdminCategory[] c = new AdminCategory[0];
				return categories.toArray(c);
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return null;
	}

	@Override
	public AdminCategory[] getCategories(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public Privileges getPrivileges() {
		return Privileges.MODERATOR;
	}

	@Override
	public User[] getUsers() {
		if (hasPrivileges(Privileges.MODERATOR) == 2) {
			if (connection == null) {
				return null;
			}

			int privileges = Privileges.getInteger(loginHandler
					.getLoggedInUser().getPrivileges());
			String query = "SELECT username, priv_level FROM users WHERE priv_level <= "
					+ privileges + ";";
			Statement statement = connection.getStatement();
			try {
				ResultSet rs = statement.executeQuery(query);
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
	public User[] getUsers(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hasPrivileges(Privileges privilegeLevel) {
		if (loginHandler.isLoggedIn()) {
			User user = loginHandler.getLoggedInUser();
			if (Privileges.getInteger(user.getPrivileges()) >= Privileges
					.getInteger(privilegeLevel)) {
				return 2;
			}
			return 1;
		}
		return 0;
	}

	private boolean queryUpdate(String query) {
		try {
			connection.getStatement().executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.err.println("ERROR: " + e.toString());
			return false;
		}
	}

	@Override
	public boolean removeCategory(AdminCategory category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setCategories(AdminCategory[] categories) {
		if (hasPrivileges(Privileges.MODERATOR) == 2) {
			String querySelect = "";
			String queryDelete = "";
			String queryInsert = "";
			String queryUpdate = "";
			String querySelect2 = "";

			try {
				Statement statement = connection.getStatement();
				String name;
				String description;

				ResultSet rs;
				querySelect = "SELECT id FROM categories;";
				rs = statement.executeQuery(querySelect);
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
							queryDelete = "DELETE FROM categories WHERE id = "
									+ tempId + ";";
							statement.executeUpdate(queryDelete);
						}
					}
				}
				for (int i = 0; i < categories.length; i++) {
					name = categories[i].getName();
					description = categories[i].getDescription();
					if (name.length() >= 3 && name.length() <= 20
							&& description.length() <= 100) {
						querySelect2 = "SELECT id FROM categories WHERE name = '"
								+ name + "';";
						rs = statement.executeQuery(querySelect2);
						if (rs.next()) {
							queryUpdate = "UPDATE categories SET name = '"
									+ name + "', description = '" + description
									+ "', position = " + (i + 1)
									+ " WHERE id = " + categories[i].getId()
									+ ";";
							statement.executeUpdate(queryUpdate);
						} else {
							queryInsert = "INSERT INTO categories(name, description, position) VALUES('"
									+ name
									+ "', '"
									+ description
									+ "', "
									+ i
									+ ");";
							statement.executeUpdate(queryInsert);
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

	public boolean setUsers(User[] users) {
		if (hasPrivileges(Privileges.MODERATOR) == 2) {
			String querySelect = "";
			String queryDelete = "";
			String queryUpdate = "";

			try {
				Statement statement = connection.getStatement();
				String name;
				Privileges privileges;
				int curPrivs = Privileges.getInteger(loginHandler
						.getLoggedInUser().getPrivileges());
				ResultSet rs;
				querySelect = "SELECT username FROM users WHERE priv_level <= "
						+ curPrivs + ";";
				rs = statement.executeQuery(querySelect);
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
						queryDelete = "DELETE FROM users WHERE username = '"
								+ tempName + "';";
						statement.executeUpdate(queryDelete);
					}
				}
				boolean edited;
				for (int i = 0; i < users.length; i++) {
					edited = users[i].isChanged();
					if (edited) {
						name = users[i].getName();
						privileges = users[i].getPrivileges();
						queryUpdate = "UPDATE users SET priv_level = "
								+ Privileges.getInteger(privileges)
								+ " WHERE username = '" + name + "';";
						statement.executeUpdate(queryUpdate);
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
	public boolean updateUser(User user) {
		return false;
	}

	public boolean logIn(String username, String password) {

		return loginHandler.logIn(username, password);
	}

	public void logOut() {
		loginHandler.logOut();
	}

}
