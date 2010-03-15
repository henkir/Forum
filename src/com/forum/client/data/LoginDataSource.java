package com.forum.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class LoginDataSource extends DataSource {

	public LoginDataSource() {
		DataSourceTextField userNameField = new DataSourceTextField("username",
				"Username", 30, true);
		DataSourcePasswordField passwordField = new DataSourcePasswordField(
				"password", "Password", 50, true);
		setFields(userNameField, passwordField);
	}

}
