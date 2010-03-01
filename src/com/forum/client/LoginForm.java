package com.forum.client;

import com.smartgwt.client.types.Alignment;
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

	public LoginForm() {
		setDataSource(new LoginDataSource());
		setUseAllDataSourceFields(false);

		HeaderItem header = new HeaderItem();
		header.setDefaultValue("Log in");

		TextItem usernameItem = new TextItem();
		usernameItem.setName("username");

		usernameItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					validateAndSubmit();
				}
			}
		});

		PasswordItem passwordItem = new PasswordItem();
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
		loginItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				validateAndSubmit();
			}
		});
		setAlign(Alignment.RIGHT);
		setFields(header, usernameItem, passwordItem, loginItem);
	}

	private void validateAndSubmit() {
		if (validate()) {
			// TODO actually login
			submit();
		}
	}

}
