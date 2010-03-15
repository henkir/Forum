package com.forum.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class RegisterDataSource extends DataSource {

	public RegisterDataSource() {
		DataSourceTextField usernameField = new DataSourceTextField("username",
				"Username", 30, true);
		DataSourcePasswordField passwordField = new DataSourcePasswordField(
				"password", "Password", 50, true);
		DataSourcePasswordField password2Field = new DataSourcePasswordField(
				"password2", "Confirm password", 50, true);

		RegExpValidator usernameValidator = new RegExpValidator(
				"^[a-zA-Z0-9_]{5,20}$");
		usernameValidator.setErrorMessage("Username is not good enough");
		RegExpValidator passwordValidator = new RegExpValidator(
				"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
		passwordValidator.setErrorMessage("Password is not good enough");
		MatchesFieldValidator password2Validator = new MatchesFieldValidator();
		password2Validator.setOtherField("passwordField");
		password2Validator.setErrorMessage("Passwords do not match");
		usernameField.setValidators(usernameValidator);
		passwordField.setValidators(passwordValidator);
		// password2Field.setValidators(password2Validator);
		setFields(usernameField, passwordField, password2Field);
	}
}
