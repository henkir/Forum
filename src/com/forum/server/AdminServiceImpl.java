package com.forum.server;

import com.forum.client.AdminService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	private static final long serialVersionUID = 7552004959637612351L;

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
		// TODO Auto-generated method stub
		return 0;
	}

}
