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
import com.smartgwt.client.widgets.grid.ListGridRecord;
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
	 * Saves changes to users.
	 */
	private final IButton saveUsers = new IButton("Save");
	/**
	 * Undoes changes to users.
	 */
	private final IButton undoUsers = new IButton("Undo");
	/**
	 * Saves changes to categories.
	 */
	private final IButton saveCategories = new IButton("Save");
	/**
	 * Undoes all changes to categories.
	 */
	private final IButton undoCategories = new IButton("Undo");
	/**
	 * Adds a category.
	 */
	private final IButton addCategory = new IButton("Add");
	/**
	 * Spacer label for increasing distances.
	 */
	private Label spacerUsers = new Label();
	/**
	 * Spacer label for increasing distances.
	 */
	private Label spacerCategories = new Label();

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
	 * Creates the callbacks used.
	 */
	private void createCallbacks() {

		callbackAuthorize = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("Failure4: " + caught.getMessage());
			}

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
		};
	}

	/**
	 * Create the layout of the panel.
	 * 
	 * @return the created layout
	 */
	private VLayout createLayout() {
		spacerCategories.setHeight(30);
		spacerCategories.setWidth(30);
		spacerUsers.setHeight(30);
		spacerUsers.setWidth(30);
		VLayout layout = new VLayout();
		layout.setWidth(550);

		VLayout usersLayout = new VLayout();
		HStack usersHStack = new HStack(5);
		VStack usersEditStack = new VStack(5);

		usersLayout.setHeight(280);
		usersLayout.addMember(usersEdit);
		usersLayout.addMember(usersHStack);
		usersHStack.addMember(users);
		usersHStack.addMember(usersEditStack);
		usersEditStack.addMember(undoUsers);
		usersEditStack.addMember(spacerUsers);
		usersEditStack.addMember(saveUsers);

		VLayout categoriesLayout = new VLayout();
		HStack categoriesHStack = new HStack(5);
		VStack categoriesEditStack = new VStack(5);
		categoriesLayout.setHeight(280);
		categoriesLayout.addMember(categoriesEdit);
		categoriesLayout.addMember(categoriesHStack);
		categoriesHStack.addMember(categories);
		categoriesHStack.addMember(categoriesEditStack);
		categoriesEditStack.addMember(addCategory);
		categoriesEditStack.addMember(undoCategories);
		categoriesEditStack.addMember(spacerCategories);
		categoriesEditStack.addMember(saveCategories);

		layout.addMember(usersLayout);
		layout.addMember(categoriesLayout);

		return layout;
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

		// Label
		usersEdit.setStyleName("adminTitle");
		usersEdit.setAutoHeight();
		usersEdit.setWidth(250);

		// Delete button
		saveUsers.setIcon("save.png");
		saveUsers.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				users.saveUsers();
			}
		});

		undoUsers.setIcon("undo.png");
		undoUsers.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				users.getUsers();
			}
		});
		// Edit categories
		categoriesEdit.setStyleName("adminTitle");
		categoriesEdit.setAutoHeight();
		categoriesEdit.setWidth(250);

		addCategory.setIcon("add.png");
		addCategory.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = new ListGridRecord();
				record.setAttribute("categoryId", 0);
				record.setAttribute("categoryName", "New category");
				record.setAttribute("categoryDescription", "");
				categories.addData(record);
			}
		});

		saveCategories.setIcon("save.png");
		saveCategories.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				categories.saveCategories();
			}
		});

		undoCategories.setIcon("undo.png");
		undoCategories.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				categories.getCategories();
			}
		});

		removeChild(loading);
		addChild(createLayout());
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
	 * Shows the admin panel.
	 */
	private void showPanel() {
		init();
	}

}
