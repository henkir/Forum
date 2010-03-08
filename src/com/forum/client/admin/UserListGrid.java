package com.forum.client.admin;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;

/**
 * A subclass of ListGrid specialized in displaying users and allowing the user
 * to edit and delete users.
 * 
 * @author henrik
 * 
 */
public class UserListGrid extends ListGrid {

	/**
	 * Admin service connection.
	 */
	private AdminServiceAsync adminSvc;

	/**
	 * Callback for getting the privilege level of the user.
	 */
	private AsyncCallback<Privileges> callbackInit;
	/**
	 * Callback for getting an array of all users.
	 */
	private AsyncCallback<User[]> callbackGetUsers;
	/**
	 * Callback for setting users.
	 */
	private AsyncCallback<Boolean> callbackSetUsers;
	private String sid;

	/**
	 * Creates a new UserListGrid that uses the given service to communicate
	 * with the server.
	 * 
	 * @param adminService
	 *            the service to use
	 */
	public UserListGrid(AdminServiceAsync adminService, String sid) {
		super();
		this.adminSvc = adminService;
		this.sid = sid;

		init();

		createCallbacks();
		adminSvc.getPrivileges(sid, callbackInit);
	}

	/**
	 * Creates the callbacks used.
	 */
	private void createCallbacks() {
		callbackInit = new AsyncCallback<Privileges>() {

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("Failure1: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Privileges result) {
				init(result);
			}

		};

		callbackGetUsers = new AsyncCallback<User[]>() {

			@Override
			public void onFailure(Throwable caught) {
				setEmptyMessage("Failed getting users");
				System.out.println("Failure2: " + caught.getMessage());
			}

			@Override
			public void onSuccess(User[] result) {
				if (result != null) {
					setEmptyMessage("No users");
					selectAllRecords();
					removeSelectedData();
					ListGridRecord record;
					for (final User user : result) {
						record = new ListGridRecord();
						record.setAttribute("userName", user.getName());
						record.setAttribute("userPrivileges", user
								.getPrivileges().toString());
						record.setAttribute("userChanged", false);
						addData(record);

					}
				} else {
					setEmptyMessage("Failed getting users");
				}
			}
		};

		callbackSetUsers = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Update failure", "Failed to update order of users.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					adminSvc.getUsers(null, callbackGetUsers);
					SC.say("Update failure", "Failed to update users.");
				} else {
					SC.say("Update successful", "Successfully updated users.");
				}
			}
		};

	}

	/**
	 * Gets all users with the same privileges or less from the server.
	 */
	public void getUsers() {
		adminSvc.getUsers(sid, callbackGetUsers);
	}

	/**
	 * Initializes the component without knowing the privileges of the user.
	 */
	private void init() {
		setCanRemoveRecords(true);
		setWidth(240);
		setHeight(250);
		setCanEdit(true);
		setEditByCell(true);
		setEditEvent(ListGridEditEvent.CLICK);
		setEmptyMessage("Loading...");
	}

	/**
	 * Initializes the component, setting various properties. The privileges
	 * decide how high a user can set a users privileges. At the end, it gets
	 * all users from server.
	 * 
	 * @param privileges
	 *            the users privileges
	 */
	private void init(Privileges privileges) {

		ListGridField usernameField = new ListGridField("userName", "Username",
				100);
		usernameField.setCanEdit(false);
		ListGridField privilegeField = new ListGridField("userPrivileges",
				"Privileges", 100);
		String[] privilegeValues = new String[Privileges.getInteger(privileges) + 1];
		Privileges[] orderedPrivileges = Privileges.getOrderedArray();
		for (int i = 0; i < privilegeValues.length; i++) {
			privilegeValues[i] = orderedPrivileges[i].toString();
		}
		privilegeField.setValueMap(privilegeValues);
		ListGridField changedField = new ListGridField("userChanged");
		changedField.setHidden(true);
		setFields(usernameField, privilegeField, changedField);
		getUsers();

		addEditCompleteHandler(new EditCompleteHandler() {

			@Override
			public void onEditComplete(EditCompleteEvent event) {
				ListGridRecord record = getRecord(event.getRowNum());
				record.setAttribute("userChanged", true);
			}
		});
	}

	/**
	 * Saves the current users.
	 */
	public void saveUsers() {
		ListGridRecord[] records = getRecords();
		User[] users = new User[records.length];
		User user;
		String name;
		Privileges privileges;
		boolean changed;

		for (int i = 0; i < records.length; i++) {
			name = records[i].getAttribute("userName");
			privileges = Privileges.valueOf(records[i].getAttribute(
					"userPrivileges").toUpperCase());
			changed = records[i].getAttributeAsBoolean("userChanged");
			user = new User(name, privileges, changed);
			users[i] = user;
		}

		adminSvc.setUsers(users, sid, callbackSetUsers);
	}

}
