package com.forum.client;

import java.util.ArrayList;

import com.forum.client.data.ForumService;
import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.SessionHandler;
import com.forum.client.data.TopicData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class Category extends Canvas {

	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	private boolean hidden = true;
	private String name = "";
	private Label title;
	private Canvas parent;
	private ArrayList<Label> labels = new ArrayList<Label>();
	private ArrayList<Integer> topics = new ArrayList<Integer>();
	private IButton addThreadButton = new IButton("Add Topic");
	private int catid;
	private int currentHeight = 0;
	private ForumThread currentThread = null;

	public Category(String name, int id, final Canvas parent) {
		super();

		setCanDragReposition(false);
		setCanDragResize(false);
		setWidth(600);
		setHeight(10);
		setBackgroundColor("#b0f963");
		setBorder("1px solid #000000");
		this.name = name;
		this.parent = parent;
		this.catid = id;
		// title label
		title = new Label("<div class='categoryTitle'>" + name
				+ " Topics</div>");
		title.setTop(currentHeight);
		title.setHeight(25);
		title.setWidth(200);
		title.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (hidden) {
					unhide();
					killThread();
				}
			}
		});
		addChild(title);
		currentHeight += title.getHeight();
		// button
		addThreadButton.setTop(currentHeight);
		currentHeight += addThreadButton.getHeight();
		addThreadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (!hidden) {
					AddTopicPanel panel = new AddTopicPanel(catid, parent,
							getThis());

					hide();

				}
			}
		});
		forumSvc.isLoggedIn(SessionHandler.getSessionId(),
				new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							addChild(addThreadButton);
						}
					}
				});

		getTopics();
	}

	public void addThread(final ForumThread topic) {
		final Label label = new Label("<div class='category'>"
				+ topic.getName() + "</div>");
		label.addClickHandler(new ClickHandler() {
			// fixa lite här
			@Override
			public void onClick(ClickEvent event) {
				if (currentThread != topic) {
					topic.draw();
					hide();
					currentThread = topic;
				}
			}
		});
		labels.add(label);
		label.setHeight(30);
		label.setWidth(getWidth());
		setHeight(getHeight() + label.getHeight());
		label.setTop(currentHeight);
		currentHeight += label.getHeight();
		addChild(label);
	}

	public String getName() {
		return name;
	}

	private Category getThis() {
		return this;
	}

	private void getTopics() {
		AsyncCallback<TopicData[]> callback = new AsyncCallback<TopicData[]>() {

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("detta sög ju");

			}

			@Override
			public void onSuccess(TopicData[] result) {
				for (int i = 0; i < result.length; i++) {

					if (!topics.contains(result[i].getId())) {
						addThread(new ForumThread(result[i].getId(), result[i]
								.getCategoryID(), result[i].getAuthorID(),
								result[i].getName(), result[i].getDate(),
								parent));
						topics.add(result[i].getId());
					}
				}

			}
		};
		forumSvc.getThreads(catid, callback);
	}

	public void hide() {
		animateRect(250, 0, 200, getHeight(), null, 1000);
		for (Label l : labels)
			l.setWidth(200);
		// setVisible(false);
		if (currentThread != null)
			killThread();
		hidden = true;
	}

	private void killThread() {
		currentThread.kill();
		currentThread = null;
	}

	public void setTopic(ForumThread topic) {
		topic.draw();
		currentThread = topic;
	}

	public void unhide() {

		animateRect(500, 0, 600, getHeight(), null, 1000);
		getTopics();
		for (Label l : labels)
			l.setWidth(600);
		setVisible(true);
		hidden = false;
	}

}
