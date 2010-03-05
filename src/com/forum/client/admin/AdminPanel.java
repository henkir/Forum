package com.forum.client.admin;

import com.forum.client.LoginForm;
import com.forum.client.Privileges;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * A subclass of Canvas that verifies that a user is authorized and shows an
 * admin panel.
 * 
 * @author henrik
 * 
 */
public class AdminPanel extends Canvas {

	/**
	 * Admin service connection.
	 */
	private AdminServiceAsync adminSvc = GWT.create(AdminService.class);

	/**
	 * Callback for authorizing the user.
	 */
	private AsyncCallback<Integer> callbackAuthorize;

	/**
	 * A list of users.
	 */
	private final UserListGrid users = new UserListGrid(adminSvc);
	/**
	 * A list of categories.
	 */
	private final CategoryListGrid categories = new CategoryListGrid(adminSvc);
	/**
	 * This is shown when the user is being authorized.
	 */
	private final Label authorizing = new Label("Authorizing...");
	/**
	 * This is shown when the objects are loading, should be nothing.
	 */
	private final Label loading = new Label("Loading...");
	/**
	 * Label for user editing.
	 */
	private Label usersEdit = new Label("Edit users");
	/**
	 * Label for category editing.
	 */
	private Label categoriesEdit = new Label("Edit categories");
	/**
	 * Deletes the selected user in the list.
	 */
	private final IButton deleteUser = new IButton("Delete");

	/**
	 * Checks if a user is logged in and if he has privileges to access the
	 * admin panel. If so, then show it. Otherwise, display a message.
	 */
	public AdminPanel() {
		addChild(authorizing);
		setHeight(400);
		setWidth100();
		createCallbacks();

		adminSvc.hasPrivileges(Privileges.MODERATOR, callbackAuthorize);
	}

	/**
	 * Creates an animated notification window.
	 * 
	 * @param title
	 *            the title of the window
	 * @return the window
	 */
	private Window createNotificationWindow(String title) {
		Window window = new Window();
		window.setWidth(4);
		window.setHeight(3);
		window.setPadding(5);
		window.setOverflow(Overflow.HIDDEN);
		window.setAnimateTime(800);
		window.setIsModal(true);
		window.setShowMinimizeButton(false);
		window.setShowModalMask(true);
		window.setTitle(title);
		window.centerInPage();
		window.setLeft(window.getLeft() - 200);
		window.setTop(window.getTop() - 150);

		return window;
	}

	/**
	 * Initializes all the components.
	 */
	private void init() {

		// Edit users

		users.addSelectionChangedHandler(new SelectionChangedHandler() {

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				System.out.println(event.getState());
				if (event.getState()) {
					deleteUser.setDisabled(false);
				} else {
					deleteUser.setDisabled(true);
				}
			}
		});

		// Label
		usersEdit.setStyleName("adminTitle");
		usersEdit.setAutoHeight();
		usersEdit.setWidth(250);

		// Delete button
		deleteUser.setDisabled(true);
		deleteUser.setIcon("delete.png");
		deleteUser.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				users.deleteSelectedUser();
			}
		});
		// Edit categories
		categoriesEdit.setStyleName("adminTitle");
		categoriesEdit.setAutoHeight();
		categoriesEdit.setWidth(250);

		removeChild(loading);
		addChild(createLayout());
	}

	/**
	 * Create the layout of the panel.
	 * 
	 * @return the created layout
	 */
	private VLayout createLayout() {
		VLayout layout = new VLayout();
		layout.setWidth(550);

		VLayout usersLayout = new VLayout(5);
		HLayout editUsersLayout = new HLayout();
		HStack editUsersStack = new HStack(5);
		VStack editUsersVertical = new VStack();
		usersLayout.setHeight(280);
		usersLayout.addMember(usersEdit);
		editUsersLayout.addMember(users);
		editUsersLayout.addMember(editUsersStack);
		editUsersVertical.addMember(deleteUser);
		editUsersStack.addMember(editUsersVertical);
		usersLayout.addMember(editUsersLayout);

		VLayout categoriesLayout = new VLayout(5);
		categoriesLayout.setHeight(280);
		categoriesLayout.addMember(categoriesEdit);
		categoriesLayout.addMember(categories);

		layout.addMember(usersLayout);
		layout.addMember(categoriesLayout);

		return layout;
	}

	/**
	 * Creates the callbacks used.
	 */
	private void createCallbacks() {

		callbackAuthorize = new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
				removeChild(authorizing);
				if (result == 2) {
					addChild(loading);
					showPanel();
				} else if (result == 1) {
					showNotAuthorized();
				} else {
					showLogin();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("Failure4: " + caught.getMessage());
			}
		};
	}

	/**
	 * Shows the admin panel.
	 */
	private void showPanel() {
		init();
		users.getUsers();
		categories.getCategories();
	}

	/**
	 * Shows a window saying that the user is not authorized to be in the admin
	 * area.
	 */
	private void showNotAuthorized() {
		Label notAuthorizedLabel = new Label(
				"You are not authorized to view this area.");
		notAuthorizedLabel.setAutoHeight();
		IButton returnButton = new IButton("Go back");
		Window window = createNotificationWindow("Not authorized");
		window.addItem(notAuthorizedLabel);
		window.addItem(returnButton);
		window.show();
	}

	/**
	 * Shows a login window with textboxes for username and password.
	 */
	private void showLogin() {
		Label instructionLabel = new Label(
				"This area requires authorization. Please log in.");
		instructionLabel.setAutoHeight();
		IButton returnButton = new IButton("Go back");
		VLayout layout = new VLayout(10);
		layout.addMember(instructionLabel);
		layout.addMember(new LoginForm());
		layout.addMember(returnButton);
		Window window = createNotificationWindow("Log in");
		window.addItem(layout);
		window.draw();
		window.animateResize(300, 200);
	}

}
