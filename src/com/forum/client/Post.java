package com.forum.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HStack;

public class Post extends Canvas {
	
	//id som retuneras
	
	private HStack layout = new HStack();
	private String text;
	private String topicName = "henkes penis";
	private Img avatar = new Img("mr_unknown.png");
	private HTMLFlow textDisplay = new HTMLFlow("jag gillar henrik<3");
	private long id;
	private int threadID;
	private int authorID;
	private String date;
	
	public Post(String topicName, String text) {
		super();
		this.topicName = topicName;
		this.text = text;
		setCanDragReposition(false);
		setCanDragResize(false);
		setWidth(300);
		setHeight(200);
		setBackgroundColor("#b0f963");
		setBorder("1px solid #000000");
		layout.setWidth(500);
		layout.setHeight(200);
		layout.setLayoutMargin(10);
		layout.setMembersMargin(5);
		//layout.addMember(new UserAvatar());
		layout.addMember(avatar);
		addChild(layout);
		//html part
		textDisplay.setContents("<h2> Re:" + topicName + "</h2> <p>" + text +" </p>");
		layout.addMember(textDisplay);
	}
	
	public Post(long id, int thID, int auID,String date, String text){
		super();
		//this.topicName = topicName;
		this.text = text;
		this.authorID = auID;
		this.id = id;
		this.threadID = thID;
		this.date = date;
		setCanDragReposition(false);
		setCanDragResize(false);
		setWidth(300);
		setHeight(200);
		setBackgroundColor("#b0f963");
		setBorder("1px solid #000000");
		layout.setWidth(500);
		layout.setHeight(200);
		layout.setLayoutMargin(10);
		layout.setMembersMargin(5);
		layout.addMember(avatar);
		addChild(layout);
		//html part
		//textDisplay.setContents("<h2> Re:" + topicName + "</h2> <p>" + text +" </p>");
		//layout.addMember(textDisplay);
	}

	class UserAvatar extends Label {
		public UserAvatar() {
			setHeight(150);
			setWidth(100);
			setPadding(10);
			setShowEdges(true);
			
		}

	}

}
