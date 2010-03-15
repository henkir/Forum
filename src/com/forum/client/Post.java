package com.forum.client;

import java.io.Serializable;

import com.forum.client.data.ForumService;
import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class Post extends Canvas implements Serializable {

	// id som retuneras

	/**
	 * 
	 */
	private static final long serialVersionUID = -4293489942482413197L;
	private HStack layout = new HStack();
	private String text;
	private String topicName = "henkes penis";
	private Img avatar = new Img("mr_unknown.png");
	private HTMLFlow textDisplay = new HTMLFlow("");
	private long id;
	private int threadID;
	private int authorID;
	private String date;
	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	private String userName;
	private VStack avatarLayout = new VStack();
	private Label nameLabel;

	public Post(long id, int thID, int auID, String date, String text) {
		super();
		this.text = text;
		this.authorID = auID;
		this.id = id;
		this.threadID = thID;
		this.date = date;
		AsyncCallback<User> callback = new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				if (result != null)
					userName = result.getName();
				else
					userName = "Default User";

				init();
			}
		};

		String sid = Cookies.getCookie("sid");

		forumSvc.getUser(sid, callback);

	}

	private void init() {

		setCanDragReposition(false);
		setCanDragResize(false);
		setWidth(300);
		setHeight(200);
		setBackgroundColor("#b0f963");
		setBorder("1px solid #000000");
		nameLabel = new Label(userName);

		nameLabel.setWidth(50);
		nameLabel.setHeight(20);
		avatarLayout.setLayoutMargin(10);
		avatarLayout.setMembersMargin(5);
		avatarLayout.addMember(nameLabel);
		avatarLayout.addMember(avatar);

		layout.setWidth(500);
		layout.setHeight(200);
		layout.setLayoutMargin(10);
		layout.setMembersMargin(5);
		layout.addMember(avatarLayout);
		addChild(layout);
		// html part
		textDisplay.setContents("<h2> Re:" + topicName + "</h2> <p>" + text
				+ " </p>");
		layout.addMember(textDisplay);
	}

}
