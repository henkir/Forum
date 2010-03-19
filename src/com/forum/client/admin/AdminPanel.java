package com.forum.client.admin;

import com.forum.client.LoginWindow;
import com.forum.client.data.ForumService;
import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.Privileges;
import com.forum.client.data.SessionHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;

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
	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);

	/**
	 * Callback for authorizing the user.
	 */
	private AsyncCallback<Integer> callbackAuthorize;

	private AsyncCallback<String> callbackLogin;

	/**
	 * The admin panel that is shown when the user is authorized.
	 */
	private AuthorizedAdminPanel authorizedAdminPanel;

	/**
	 * This is shown when the user is being authorized.
	 */
	private final Label authorizing = new Label("Authorizing...");
	/**
	 * This is shown when the objects are loading, should be nothing.
	 */
	private final Label loading = new Label("Loading...");

	/**
	 * Window for displaying information if the user is not logged in or not
	 * authorized.
	 */
	private Window window;

	/**
	 * Checks if a user is logged in and if he has privileges to access the
	 * admin panel. If so, then show it. Otherwise, display a message.
	 */
	public AdminPanel() {
		setBackgroundColor("#ffff00");
		addChild(authorizing);
		setHeight(400);
		setWidth100();
		createCallbacks();

		forumSvc.hasPrivileges(Privileges.MODERATOR, SessionHandler
				.getSessionId(), callbackAuthorize);
	}

	/**
	 * Creates the callbacks used.
	 */
	private void createCallbacks() {
		callbackLogin = new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Failure", "Failed to log in.");
			}

			@Override
			public void onSuccess(String result) {
				if (result != null) {
					reload(result);
				} else {
					SC.say("Mismatching cresidentials",
							"Username and password do not match.");
				}
			}
		};
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
		window.setShowMinimizeButton(false);
		window.setShowModalMask(true);
		window.setTitle(title);
		window.centerInPage();
		window.setLeft(window.getLeft() - 200);
		window.setTop(window.getTop() - 150);

		return window;
	}

	/**
	 * Reloads the admin panel.
	 * 
	 * @param sid
	 *            the new session ID
	 */
	public void reload(String sid) {
		if (authorizedAdminPanel != null) {
			removeChild(authorizedAdminPanel);
		}
		SessionHandler.setSessionId(sid);
		forumSvc.hasPrivileges(Privileges.MODERATOR, sid, callbackAuthorize);
		if (window != null) {
			window.hide();
		}
	}

	/**
	 * Shows a login window with textboxes for username and password.
	 */
	private void showLogin() {
		LoginWindow loginWindow = new LoginWindow(forumSvc, callbackLogin);
		loginWindow.draw();
	}

	/**
	 * Shows a window saying that the user is not authorized to be in the admin
	 * area.
	 */
	private void showNotAuthorized() {
		Label notAuthorizedLabel = new Label(
				"You are not authorized to view this area.");
		notAuthorizedLabel.setAutoHeight();
		window = createNotificationWindow("Not authorized");
		window.addItem(notAuthorizedLabel);
		window.draw();
		window.animateResize(300, 200);
	}

	/**
	 * Shows the admin panel.
	 */
	private void showPanel() {
		authorizedAdminPanel = new AuthorizedAdminPanel(this, forumSvc);
		removeChild(loading);
		addChild(authorizedAdminPanel);
	}

}
