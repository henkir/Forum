package com.forum.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * A data source for registering a new user.
 * 
 * @author henho106 + jonhe442
 * 
 */
public class RegisterDataSource extends DataSource {

	/**
	 * Creates a new RegisterDataSource.
	 */
	public RegisterDataSource() {
		// Create fields
		DataSourceTextField usernameField = new DataSourceTextField("username",
				"Username", 30, true);
		DataSourcePasswordField passwordField = new DataSourcePasswordField(
				"password", "Password", 50, true);
		DataSourcePasswordField password2Field = new DataSourcePasswordField(
				"password2", "Confirm password", 50, true);

		// Create username validator
		RegExpValidator usernameValidator = new RegExpValidator(
				"^[a-zA-Z0-9_]{5,20}$");
		usernameValidator.setErrorMessage("Username is not good enough");
		// Create password validator
		RegExpValidator passwordValidator = new RegExpValidator(
				"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
		passwordValidator.setErrorMessage("Password is not good enough");
		// Create confirm password validator
		MatchesFieldValidator password2Validator = new MatchesFieldValidator();
		password2Validator.setOtherField("password");
		password2Validator.setErrorMessage("Passwords do not match");
		// Set validators
		usernameField.setValidators(usernameValidator);
		passwordField.setValidators(passwordValidator);
		password2Field.setValidators(password2Validator);
		// Set which fields to use
		setFields(usernameField, passwordField, password2Field);
	}
}
