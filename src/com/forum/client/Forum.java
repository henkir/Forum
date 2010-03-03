package com.forum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

/**
 * Forum is the main class in the project and is the entry point. The first
 * executed method is onModuleLoad.
 * 
 * @author henho106 + jonhe442
 * 
 */
public class Forum implements EntryPoint {
	final VerticalPanel panel = new VerticalPanel();
	Canvas forumCanvas = new Canvas();
	CategoryList catList = new CategoryList(forumCanvas);
	final Image banner = new Image("images/forum_banner.png");
	Button adminButton = new Button("Admin panel");
	Button forumButton = new Button("Forum panel");
	Label forumTitle = new Label("Forum");
	AdminPanel adminPanel = new AdminPanel();

	ButtonClickHandler buttonClickHandler = new ButtonClickHandler();

	public void onModuleLoad() {

		forumTitle.setStyleName("forumTitle");

		// the canvas
		forumCanvas.setWidth("100%");
		forumCanvas.setHeight("100%");
		forumCanvas.addChild(catList);
		forumCanvas.setBackgroundColor("#ffff00");
		// forumCanvas.draw();

		showForumPanel();

		RootPanel.get("main").add(panel);

	}

	private void showForumPanel() {
		panel.clear();
		System.out.println("forum");
		// Click handlers stop working after one click???
		adminButton = new Button("Admin panel");
		adminButton.addClickHandler(buttonClickHandler);
		panel.add(adminButton);
		panel.add(banner);
		panel.add(forumTitle);
		panel.add(forumCanvas);
	}

	private void showAdminPanel() {
		System.out.println("admin");
		panel.clear();
		forumButton = new Button("Forum panel");
		forumButton.addClickHandler(buttonClickHandler);
		panel.add(forumButton);
		panel.add(banner);
		panel.add(adminPanel);
	}

	private class ButtonClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			System.out.println("CLICK");
			if (event.getSource() == adminButton) {
				showAdminPanel();
			} else if (event.getSource() == forumButton) {
				showForumPanel();
			}
		}

	}
}
