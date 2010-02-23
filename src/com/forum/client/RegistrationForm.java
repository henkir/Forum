package com.forum.client;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;

public class RegistrationForm extends DynamicForm {

	public RegistrationForm() {
		setDataSource(new RegistrationDataSource());
		setUseAllDataSourceFields(true);

		HeaderItem header = new HeaderItem();
		header.setDefaultValue("Registration Form");

		PasswordItem passwordItem = new PasswordItem();
		passwordItem.setName("password");

		PasswordItem passwordItem2 = new PasswordItem();
		passwordItem2.setName("password2");
		passwordItem2.setTitle("Password Again");
		passwordItem2.setRequired(true);
		passwordItem2.setLength(20);

		MatchesFieldValidator matchesValidator = new MatchesFieldValidator();
		matchesValidator.setOtherField("password");
		matchesValidator.setErrorMessage("Passwords do not match");
		passwordItem2.setValidators(matchesValidator);

		CheckboxItem acceptItem = new CheckboxItem();
		acceptItem.setName("acceptTerms");
		acceptItem.setTitle("I accept the terms of use.");
		acceptItem.setRequired(true);
		acceptItem.setWidth(150);

		ButtonItem registerItem = new ButtonItem();
		registerItem.setTitle("Register");
		registerItem.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// TODO actually register
				validate(false);
			}
		});

		setFields(header, passwordItem, passwordItem2, acceptItem, registerItem);
	}

}
