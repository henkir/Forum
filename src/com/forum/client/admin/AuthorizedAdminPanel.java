package com.forum.client.admin;

import com.forum.client.ForumServiceAsync;
import com.forum.client.SessionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AuthorizedAdminPanel extends Canvas {

	/**
	 * Admin service connection.
	 */
	private ForumServiceAsync forumSvc;
	private AdminPanel adminPanel;

	/**
	 * A list of users.
	 */
	private UserListGrid users;
	/**
	 * A list of categories.
	 */
	private CategoryListGrid categories;
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

	private final IButton logOut = new IButton("Log out");
	/**
	 * Spacer label for increasing distances.
	 */
	private Label spacerUsers = new Label();
	/**
	 * Spacer label for increasing distances.
	 */
	private Label spacerCategories = new Label();

	public AuthorizedAdminPanel(AdminPanel adminPanel,
			ForumServiceAsync forumService) {
		this.adminPanel = adminPanel;
		this.forumSvc = forumService;
		init();
	}

	/**
	 * Initializes all the components.
	 */
	private void init() {
		logOut.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				forumSvc.logOut(SessionHandler.getSessionId(),
						new AsyncCallback<String>() {

							@Override
							public void onSuccess(String result) {
								adminPanel.reload(result);
							}

							@Override
							public void onFailure(Throwable caught) {
								SC.say("Failure", "Failed to log out.");
							}
						});
			}
		});

		users = new UserListGrid(forumSvc);
		categories = new CategoryListGrid(forumSvc);
		// Edit users

		// Label
		usersEdit.setStyleName("adminTitle");
		usersEdit.setAutoHeight();
		usersEdit.setWidth(250);

		// Delete button
		saveUsers.setIcon("save.png");
		// saveUsers.setDisabled(true);
		saveUsers.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				users.saveUsers();
				// saveUsers.setDisabled(true);
				// undoUsers.setDisabled(true);
			}
		});

		undoUsers.setIcon("undo.png");
		// undoUsers.setDisabled(true);
		undoUsers.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				users.getUsers();
				// saveUsers.setDisabled(true);
				// undoUsers.setDisabled(true);
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
		// saveCategories.setDisabled(true);
		saveCategories.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				categories.saveCategories();
				// saveCategories.setDisabled(true);
				// undoCategories.setDisabled(true);
			}
		});

		undoCategories.setIcon("undo.png");
		// undoCategories.setDisabled(true);
		undoCategories.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				categories.getCategories();
				// saveCategories.setDisabled(true);
				// undoCategories.setDisabled(true);
			}
		});

		users.addEditCompleteHandler(new EditCompleteHandler() {

			@Override
			public void onEditComplete(EditCompleteEvent event) {
				saveUsers.setDisabled(false);
				undoUsers.setDisabled(false);
			}
		});

		categories.addEditCompleteHandler(new EditCompleteHandler() {

			@Override
			public void onEditComplete(EditCompleteEvent event) {
				saveCategories.setDisabled(false);
				undoCategories.setDisabled(false);
			}
		});

		addChild(createLayout());
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

		layout.addMember(logOut);
		layout.addMember(usersLayout);
		layout.addMember(categoriesLayout);

		return layout;
	}

}
