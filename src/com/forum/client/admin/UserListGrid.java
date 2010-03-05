package com.forum.client.admin;

import com.forum.client.Privileges;
import com.forum.client.User;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RowEditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.RowEditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.RowEditorExitEvent;
import com.smartgwt.client.widgets.grid.events.RowEditorExitHandler;

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
	 * Callback for deleting a user.
	 */
	private AsyncCallback<Boolean> callbackDeleteUser;
	/**
	 * Callback for getting an array of all users.
	 */
	private AsyncCallback<User[]> callbackGetUsers;
	/**
	 * Callback for updating a user.
	 */
	private AsyncCallback<Boolean> callbackUpdateUser;

	/**
	 * The privileges of the currently selected user.
	 */
	private Privileges currentPrivileges;

	/**
	 * Creates a new UserListGrid that uses the given service to communicate
	 * with the server.
	 * 
	 * @param adminService
	 *            the service to use
	 */
	public UserListGrid(AdminServiceAsync adminService) {
		super();
		this.adminSvc = adminService;

		createCallbacks();

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
		setWidth(220);
		setHeight(250);
		setCanEdit(true);
		setEditByCell(true);
		setEditEvent(ListGridEditEvent.CLICK);
		setEmptyMessage("Loading...");

		ListGridField usernameField = new ListGridField("Username", 100);
		usernameField.setCanEdit(false);
		ListGridField privilegeField = new ListGridField("Privileges", 100);
		String[] privilegeValues = new String[Privileges.getInteger(privileges) + 1];
		Privileges[] orderedPrivileges = Privileges.getOrderedArray();
		for (int i = 0; i < privilegeValues.length; i++) {
			privilegeValues[i] = orderedPrivileges[i].toString();
		}
		privilegeField.setValueMap(privilegeValues);
		setFields(usernameField, privilegeField);
		getUsers();
	}

	/**
	 * Deletes the selected user. It first displays a confirmation window, then
	 * contacts the server to delete the user. If it was successful, the users
	 * are re-read from the server. If it failed, a notification will be
	 * displayed.
	 */
	public void deleteSelectedUser() {
		ListGridRecord record = getSelectedRecord();

		String username = record.getAttribute("Username");
		Privileges privileges = Privileges.valueOf(record.getAttribute(
				"Privileges").toUpperCase());

		final User user = new User(username, privileges);

		SC.confirm("Delete user", "Do you really want to delete '" + username
				+ "'?", new BooleanCallback() {

			@Override
			public void execute(Boolean value) {
				if (value) {
					adminSvc.deleteUser(user, callbackDeleteUser);
				}
			}
		});
	}

	/**
	 * Gets all users with the same privileges or less from the server.
	 */
	public void getUsers() {
		adminSvc.getUsers(callbackGetUsers);
	}

	/**
	 * Creates the callbacks used.
	 */
	private void createCallbacks() {
		callbackInit = new AsyncCallback<Privileges>() {

			@Override
			public void onSuccess(Privileges result) {
				init(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("Failure1: " + caught.getMessage());
			}

		};

		callbackDeleteUser = new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					adminSvc.getUsers(callbackGetUsers);
				} else {
					SC.say("User deletion",
							"There was an error deleting the user.");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure");
				SC
						.say("User deletion",
								"There was an error deleting the user.");
			}

		};

		callbackGetUsers = new AsyncCallback<User[]>() {

			@Override
			public void onSuccess(User[] result) {
				setEmptyMessage("No users");
				ListGridRecord record;
				for (final User user : result) {
					record = new ListGridRecord();
					record.setAttribute("Username", user.getName());
					record.setAttribute("Privileges", user.getPrivileges()
							.toString());
					addData(record);

					addRowEditorEnterHandler(new RowEditorEnterHandler() {

						@Override
						public void onRowEditorEnter(RowEditorEnterEvent event) {
							currentPrivileges = Privileges.MEMBER;
							System.out.println("enter");
						}
					});
					addRowEditorExitHandler(new RowEditorExitHandler() {

						@Override
						public void onRowEditorExit(RowEditorExitEvent event) {
							if (!event.isCancelled()) {
								System.out.println("exit");
								Record record = event.getRecord();

								String username = record
										.getAttribute("Username");
								Privileges privileges = Privileges
										.valueOf(record.getAttribute(
												"Privileges").toUpperCase());
								if (currentPrivileges != privileges) {
									final User user = new User(username,
											privileges);
									adminSvc.updateUser(user,
											callbackUpdateUser);
								}
							}
						}
					});
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				setEmptyMessage("Failed getting users");
				System.out.println("Failure2: " + caught.getMessage());
			}
		};

		callbackUpdateUser = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Update failed", "Failed to update user.");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (!result) {
					SC.say("Update failed", "Failed to update user.");
				}
			}

		};

	}

}
