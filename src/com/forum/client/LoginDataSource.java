package com.forum.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.XMLTools;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.DSDataFormat;

public class LoginDataSource extends DataSource {

	public LoginDataSource() {
		setDataFormat(DSDataFormat.JSON);
		setDataURL("data/dataIntegration/json/serverValidationErrors/serverResponse.js");
		DataSourceTextField userNameField = new DataSourceTextField("userName",
				"Username", 50, true);
		DataSourcePasswordField passwordField = new DataSourcePasswordField(
				"password", "Password", 20, true);
		setFields(userNameField, passwordField);
	}

	@Override
	protected void transformResponse(DSResponse response, DSRequest request,
			Object jsonData) {

		JSONArray value = XMLTools.selectObjects(jsonData, "/response/status");
		String status = ((JSONString) value.get(0)).stringValue();
		if (!status.equals("success")) {
			response.setStatus(RPCResponse.STATUS_VALIDATION_ERROR);
			JSONArray errors = XMLTools.selectObjects(jsonData,
					"/response/errors");
			response.setErrors(errors.getJavaScriptObject());
		}
	}

}
