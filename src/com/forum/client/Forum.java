package com.forum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;

/**
 * Forum is the main class in the project and is the entry point. The first
 * executed method is onModuleLoad.
 * 
 * @author henho106 + jonhe442
 * 
 */
public class Forum implements EntryPoint {
	VerticalPanel panel = new VerticalPanel();
	Canvas forumCanvas = new Canvas();
	CategoryList catList = new CategoryList(forumCanvas);

	public void onModuleLoad() {
		Image banner = new Image("images/forum_banner.png");
		Label forumTitle = new Label("Forum");
		forumTitle.setStyleName("forumTitle");

		// the panel
		panel.setWidth("150px");
		panel.add(banner);
		panel.add(forumTitle);
		panel.add(forumCanvas);

		// the canvas
		forumCanvas.setWidth("100%");
		forumCanvas.setHeight("100%");
		forumCanvas.addChild(catList);
		forumCanvas.setBackgroundColor("#ffff00");
		// forumCanvas.draw();

		RootPanel.get("main").add(panel);

	}
}
