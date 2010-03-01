package com.forum.client;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class RegistrationDataSource extends DataSource {

	public RegistrationDataSource() {
		DataSourceTextField firstNameField = new DataSourceTextField(
				"username", "Username", 30, true);
		DataSourcePasswordField passwordField = new DataSourcePasswordField(
				"password", "Password", 50, true);

		setFields(firstNameField, passwordField);
	}

}
