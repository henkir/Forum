package com.forum.client;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class LoginForm extends DynamicForm {

	public LoginForm() {
		setDataSource(new LoginDataSource());
		setUseAllDataSourceFields(true);

		HeaderItem header = new HeaderItem();
		header.setDefaultValue("Log in");

		PasswordItem passwordItem = new PasswordItem();
		passwordItem.setName("password");

		ButtonItem loginButton = new ButtonItem("Login");
		loginButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (validate()) {
					// TODO actually login
					submit();
				}
			}
		});
		setFields(header, loginButton);
	}

}
