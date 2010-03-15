package com.forum.client;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;

public class WaitWindow extends Window {

	public WaitWindow(String message) {
		super();
		setWidth(200);
		setHeight(100);
		setShowMinimizeButton(false);
		setShowCloseButton(false);
		setIsModal(true);
		setShowModalMask(true);
		setTitle("Please wait...");
		Label label = new Label(message);
		addItem(label);
		centerInPage();
	}

}
