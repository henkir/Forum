package com.forum.client;

import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.RegisterDataSource;
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

public class RegisterWindow extends Window {
	private ForumServiceAsync forumSvc;
	private AsyncCallback<Boolean> callback;
	private DynamicForm form = new DynamicForm();
	private TextItem usernameItem = new TextItem();
	private PasswordItem passwordItem = new PasswordItem();
	private PasswordItem password2Item = new PasswordItem();

	public RegisterWindow(ForumServiceAsync forumSvc,
			AsyncCallback<Boolean> callback) {
		super();
		this.forumSvc = forumSvc;
		this.callback = callback;

		setTitle("Log in");
		setShowMinimizeButton(false);
		setIsModal(true);
		setShowModalMask(true);
		centerInPage();

		form.setDataSource(new RegisterDataSource());

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

		password2Item.setName("password2");
		password2Item.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					submit();
				}
			}

		});

		ButtonItem registerItem = new ButtonItem("registerButton", "Register");
		registerItem.setWidth(70);

		registerItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				submit();
			}

		});

		form.setFields(usernameItem, passwordItem, password2Item, registerItem);

		addItem(form);
		setAutoSize(true);
	}

	private void submit() {
		if (form.validate()) {
			forumSvc.register(usernameItem.getDisplayValue(), passwordItem
					.getDisplayValue(), callback);
			hide();
		}
	}
}
