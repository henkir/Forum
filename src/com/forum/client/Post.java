package com.forum.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HStack;

public class Post extends Canvas {
	HStack layout = new HStack();

	public Post() {
		super();
		layout.addMember(new UserAvatar());
		addChild(layout);
	}

	class UserAvatar extends Label {
		public UserAvatar() {
			setHeight(75);
			setWidth(50);
			setPadding(10);
			setShowEdges(true);
			setIcon("war/images/mr_unknown.png");
		}

	}

}
