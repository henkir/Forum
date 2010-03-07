package com.forum.client;

import com.forum.client.admin.AdminPanel;
import com.forum.client.admin.AdminServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public class LoginForm extends DynamicForm {

	private AdminServiceAsync adminSvc;
	private AsyncCallback<Boolean> callbackLogin;
	private AdminPanel adminPanel;

	private TextItem usernameItem = new TextItem();
	private PasswordItem passwordItem = new PasswordItem();

	public LoginForm(AdminServiceAsync adminService, AdminPanel adminPanel) {
		this.adminSvc = adminService;
		this.adminPanel = adminPanel;
		createCallbacks();

		setDataSource(new LoginDataSource());
		setUseAllDataSourceFields(false);

		HeaderItem header = new HeaderItem();
		header.setDefaultValue("Log in");

		usernameItem.setName("username");

		usernameItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					validateAndSubmit();
				}
			}
		});

		passwordItem.setName("password");
		passwordItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					validateAndSubmit();
				}
			}
		});

		ButtonItem loginItem = new ButtonItem("loginButton", "Log in");
		loginItem.setWidth(70);

		loginItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (validate()) {
					submit();
				}
			}
		});

		setFields(header, usernameItem, passwordItem, loginItem);
	}

	private void createCallbacks() {
		callbackLogin = new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					adminPanel.reload();
				} else {
					SC.say("Mismatching cresidentials",
							"Username and password do not match.");
					usernameItem.getDisplayValue("");
					passwordItem.getDisplayValue("");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Failure", "Failed to log in.");
			}
		};
	}

	private void validateAndSubmit() {
		if (validate()) {
			submit();
		}
	}

	public void submit() {
		adminSvc.logIn(usernameItem.getDisplayValue(), passwordItem
				.getDisplayValue(), callbackLogin);
	}

}
