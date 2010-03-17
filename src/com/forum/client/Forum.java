package com.forum.client;

import com.forum.client.admin.AdminPanel;
import com.forum.client.data.ForumService;
import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.Privileges;
import com.forum.client.data.SessionHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Forum is the main class in the project and is the entry point. The first
 * executed method is onModuleLoad.
 * 
 * @author henho106 + jonhe442
 * 
 */
public class Forum implements EntryPoint {
	private class ButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == adminButton) {
				showAdminPanel();
			} else if (event.getSource() == forumButton) {
				showForumPanel();
			} else if (event.getSource() == loginButton) {
				showLoginWindow();
			} else if (event.getSource() == registerButton) {
				showRegisterWindow();
			} else if (event.getSource() == logoutButton) {
				logout();
			}
		}

	}

	ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	final VLayout panel = new VLayout(0);
	HStack topPanel = new HStack(5);
	Canvas forumCanvas = new Canvas();
	CategoryList catList;
	final Image banner = new Image("images/forum_banner.png");
	IButton loginButton = new IButton("Log in");
	IButton registerButton = new IButton("Register");
	IButton adminButton = new IButton("Admin panel");
	IButton forumButton = new IButton("Forum panel");
	IButton logoutButton = new IButton("Log out");
	Privileges currentPrivs = null;
	WaitWindow waitWindow;

	AsyncCallback<String> loginCallback;
	AsyncCallback<Boolean> registerCallback;

	ButtonClickHandler buttonClickHandler = new ButtonClickHandler();

	public void onModuleLoad() {

		loginCallback = new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				final String sid = result;
				forumSvc.isLoggedIn(sid, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say("Failure", "There was a failure.");
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							waitWindow = new WaitWindow("Logging in");
							waitWindow.draw();
							SessionHandler.setSessionId(sid);
							updatePrivileges();
						} else {
							SC.say("Wrong password",
									"Username and password do not match.");
						}
					}
				});

			}

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Failure", "Failed logging in.");
			}
		};

		registerCallback = new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					SC
							.say("Registered",
									"You are now registered. Log in by clicking the Log in button.");
				} else {
					SC.say("Failure", "There was a failure registering.");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Failure", "There was a failure registering.");
			}
		};

		updatePrivileges();

		RootPanel.get("main").add(panel);

	}

	private void updatePrivileges() {
		forumSvc.hasPrivileges(Privileges.MODERATOR, SessionHandler
				.getSessionId(), new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Failure", "There was a failure.");
			}

			@Override
			public void onSuccess(Integer result) {
				if (result == 2) {
					currentPrivs = Privileges.MODERATOR;
				} else if (result == 1) {
					currentPrivs = Privileges.MEMBER;
				} else {
					currentPrivs = null;
				}
				if (waitWindow != null) {
					waitWindow.hide();
				}
				showForumPanel();
			}
		});
	}

	private void logout() {
		waitWindow = new WaitWindow("Logging out");
		waitWindow.show();
		forumSvc.logOut(SessionHandler.getSessionId(),
				new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say("Failure", "There was a failure logging out.");
					}

					@Override
					public void onSuccess(String result) {
						SessionHandler.setSessionId(result);
						updatePrivileges();
					}
				});
	}

	private void showForumPanel() {
		panel.destroy();
		catList = new CategoryList(forumCanvas);

		forumCanvas.setWidth("100%");
		forumCanvas.setHeight("100%");
		forumCanvas.addChild(catList);
		forumCanvas.setBackgroundColor("#ffff00");

		loginButton = new IButton("Log in");
		logoutButton = new IButton("Log out");
		registerButton = new IButton("Register");
		adminButton = new IButton("Admin panel");

		topPanel = new HStack(5);
		topPanel.setBackgroundColor("#ffff00");
		topPanel.setHeight(loginButton.getHeight());
		if (currentPrivs == null) {
			topPanel.addMember(loginButton);
			topPanel.addMember(registerButton);
		} else {
			topPanel.addMember(logoutButton);
		}
		if (currentPrivs == Privileges.MODERATOR) {
			topPanel.addMember(adminButton);
		}

		loginButton.addClickHandler(buttonClickHandler);
		registerButton.addClickHandler(buttonClickHandler);
		logoutButton.addClickHandler(buttonClickHandler);

		adminButton.addClickHandler(buttonClickHandler);

		panel.setHeight100();
		panel.setWidth100();
		panel.setVPolicy(LayoutPolicy.NONE);
		panel.addMember(topPanel);
		panel.addMember(banner);
		panel.addMember(forumCanvas);
		panel.draw();
	}

	private void showAdminPanel() {
		panel.destroy();
		forumButton = new IButton("Forum panel");
		forumButton.addClickHandler(buttonClickHandler);
		logoutButton = new IButton("Log out");
		logoutButton.addClickHandler(buttonClickHandler);
		topPanel = new HStack(5);
		topPanel.addMember(logoutButton);
		topPanel.addMember(forumButton);
		topPanel.setHeight(logoutButton.getHeight());
		topPanel.setBackgroundColor("#ffff00");
		panel.addMember(topPanel);
		panel.addMember(banner);
		panel.addMember(new AdminPanel());
		panel.draw();
	}

	private void showLoginWindow() {
		LoginWindow window = new LoginWindow(forumSvc, loginCallback);
		window.draw();
	}

	private void showRegisterWindow() {
		RegisterWindow window = new RegisterWindow(forumSvc, registerCallback);
		window.draw();
	}

}
