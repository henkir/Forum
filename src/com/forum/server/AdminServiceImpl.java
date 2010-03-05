package com.forum.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.forum.client.admin.AdminCategory;
import com.forum.client.admin.AdminService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = 7552004959637612351L;
	private DatabaseConnection connection = null;
	private HttpServletRequest request;
	private HttpSession session;

	private final int authorized = 2;
	private final int loggedIn = 1;
	private final int notLoggedIn = 0;

	public AdminServiceImpl() {
		// super();
		// request = this.getThreadLocalRequest();
		// System.out.println("request " + request);
		// session = request.getSession();
		// connection = new DatabaseConnection(true);
	}

	@Override
	public boolean addCategory(AdminCategory category) {
		if (hasPrivileges(Privileges.MODERATOR) == authorized) {
			String name = category.getName();
			String description = category.getDescription();
			int position = category.getPosition();
			String query = "INSERT INTO categories(name, description, position VALUES('"
					+ name + "', '" + description + "', '" + position + "');";
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
	public boolean removeCategory(AdminCategory category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setCategoryPosition(AdminCategory category, int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(User user) {
		return false;
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

	public Privileges getPrivileges() {
		return Privileges.MODERATOR;
	}

	@Override
	public int hasPrivileges(Privileges privilegeLevel) {

		String userId = null; // session.getAttribute("user_id").toString();
		if (userId == null) {
			return 2;
		}

		try {
			ResultSet rs = connection.getStatement().executeQuery(
					"SELECT priv_level FROM users WHERE id='"
							+ Integer.parseInt(userId) + "';");
			if (rs.next()) {
				if (rs.getInt("priv_level") >= 2) {
					return 2;
				}
			}
		} catch (SQLException e) {
			System.err.println("ERROR: " + e.toString());
			return 0;
		}
		return 0;
	}

	@Override
	public AdminCategory[] getCategories() {
		AdminCategory[] categories = {
				new AdminCategory("gay stuff", "you know, like GAY", 1),
				new AdminCategory("straight stuff???", "NAAAA", 2),
				new AdminCategory("your mother", "on pizza you know", 3) };

		return categories;
	}

	@Override
	public AdminCategory[] getCategories(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User[] getUsers() {
		User[] users = { new User("user", Privileges.MEMBER),
				new User("user2", Privileges.MEMBER),
				new User("mod", Privileges.MODERATOR),
				new User("mod2", Privileges.MODERATOR),
				new User("admin", Privileges.ADMINISTRATOR) };
		return users;
	}

	@Override
	public User[] getUsers(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
