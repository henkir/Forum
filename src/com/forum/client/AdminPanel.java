package com.forum.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

public class AdminPanel extends Canvas {

	private AdminServiceAsync adminSvc = GWT.create(AdminService.class);

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

	private void showPanel() {

	}

	private void showNotAuthorized() {
		Label notAuthorizedLabel = new Label(
				"You are not authorized to view this area.");
		notAuthorizedLabel.setAutoHeight();
		IButton returnButton = new IButton("Go back");
		// TODO fix so button returns to front page
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
		// TODO fix so button returns to front page
		VLayout layout = new VLayout(10);
		layout.addMember(instructionLabel);
		layout.addMember(new LoginForm());
		layout.addMember(returnButton);
		Window window = createNotificationWindow("Log in");
		window.addItem(layout);
		// window.draw();
		// window.animateResize(300, 200);
	}

}
