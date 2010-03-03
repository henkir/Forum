package com.forum.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

public class AdminPanel extends VerticalPanel {

	private AdminServiceAsync adminSvc = GWT.create(AdminService.class);

	final ListGrid categories = new ListGrid();
	final VerticalPanel panel = new VerticalPanel();

	/**
	 * Checks if a user is logged in and if he has privileges to access the
	 * admin panel. If so, then show it. Otherwise, display a message.
	 */
	public AdminPanel() {

		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
				if (result == 2) {
					showPanel();
				} else if (result == 1) {
					showNotAuthorized();
				} else {
					showLogin();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("Failure: ");
			}
		};

		adminSvc.hasPrivileges(5, callback);

	}

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

	private void initComponents() {
		ListGridField categoryField = new ListGridField("Name", 80);
		ListGridField descriptionField = new ListGridField("Description", 100);
		categories.setCanReorderRecords(true);
		categories.setFields(categoryField, descriptionField);
	}

	private void showPanel() {

		initComponents();

		AsyncCallback<User[]> callbackUsers = new AsyncCallback<User[]>() {

			@Override
			public void onSuccess(User[] result) {
				for (User user : result) {

				}
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure: " + caught.getMessage());
			}
		};

		AsyncCallback<AdminCategory[]> callbackCategories = new AsyncCallback<AdminCategory[]>() {

			@Override
			public void onSuccess(AdminCategory[] result) {
				ListGridRecord record;
				for (AdminCategory category : result) {
					record = new ListGridRecord();
					record.setAttribute("Name", category.getName());
					record.setAttribute("Description", category
							.getDescription());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Failure: " + caught.getMessage());
			}
		};

		// add(categories);
		// addChild(panel);

		// adminSvc.getUsers(callbackUsers);
		// adminSvc.getCategories(callbackCategories);
	}

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
