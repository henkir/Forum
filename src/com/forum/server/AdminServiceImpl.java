package com.forum.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.forum.client.AdminService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = 7552004959637612351L;
	private DatabaseConnection connection = null;
	private HttpServletRequest request;
	private HttpSession session;

	public AdminServiceImpl() {
		// super();
		// request = this.getThreadLocalRequest();
		// System.out.println("request " + request);
		// session = request.getSession();
		// connection = new DatabaseConnection(true);
	}

	@Override
	public boolean addCategory(String category, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editCategory(String oldCategory, String newCategory,
			String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCategory(String category) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setCategoryPosition(String category, int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setUserPrivileges(String username, int privilegeLevel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hasPrivileges(int privilegeLevel) {

		String userId = null; // session.getAttribute("user_id").toString();
		if (userId == null) {
			return 0;
		}

		try {
			ResultSet rs = connection.getStatement().executeQuery(
					"SELECT priv_level FROM users WHERE id='"
							+ Integer.parseInt(userId) + "';");
			if (rs.next()) {
				if (rs.getInt("priv_level") >= privilegeLevel) {
					return 2;
				}
			}
		} catch (SQLException e) {
			System.err.println("ERROR: " + e.toString());
			return 0;
		}
		return 0;
	}

}
