package com.forum.client;

import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.LoginDataSource;
import com.forum.client.data.SessionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public class LoginWindow extends Window {

	private ForumServiceAsync forumSvc;
	private AsyncCallback<String> callback;
	private DynamicForm form = new DynamicForm();
	private TextItem usernameItem = new TextItem();
	private PasswordItem passwordItem = new PasswordItem();

	public LoginWindow(ForumServiceAsync forumSvc,
			AsyncCallback<String> callback) {
		super();
		this.forumSvc = forumSvc;
		this.callback = callback;

		setTitle("Log in");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();

		form.setDataSource(new LoginDataSource());

		usernameItem.setName("username");

		usernameItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					submit();
				}
			}

		});

		passwordItem.setName("password");
		passwordItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					submit();
				}
			}

		});

		ButtonItem loginItem = new ButtonItem("loginButton", "Log in");
		loginItem.setWidth(70);

		loginItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				submit();
			}

		});

		form.setFields(usernameItem, passwordItem, loginItem);

		addItem(form);
		setAutoSize(true);
	}

	private void submit() {
		if (form.validate()) {
			forumSvc
					.logIn(usernameItem.getDisplayValue(), passwordItem
							.getDisplayValue(), SessionHandler.getSessionId(),
							callback);
			hide();
		}
	}

}
