package com.forum.client;

import java.io.Serializable;
import java.util.ArrayList;

import com.forum.client.data.PostData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;

public class ForumThread implements Serializable {

	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	private static final long serialVersionUID = 1L;
	private String name;
	private Canvas parent;
	private Canvas head, tail;
	private VStack tailLayout;
	private RichTextEditor rtEditor;
	private Button submitButton;
	private Label title;
	private ArrayList<Post> posts = new ArrayList<Post>();
	private int currentHeight = 0;
	private int id;
	private int categoryID;
	private int authorID;
	private String date;
	private ArrayList<Long> postID = new ArrayList<Long>();

	public ForumThread(int id, int catID, int auID, String name, String date,
			Canvas parent) {
		this.id = id;
		this.categoryID = catID;
		this.authorID = auID;
		this.name = name;
		this.date = date;
		this.parent = parent;
		initThread();
	}

	public ForumThread(int id, int catID, int auID, String name, String date,
			Canvas parent, String initPost) {
		this.id = id;
		this.categoryID = catID;
		this.authorID = auID;
		this.name = name;
		this.date = date;
		this.parent = parent;
		addPost(initPost);
		initThread();
	}

	public ForumThread(final String name, Canvas parent) {
		this.name = name;
		this.parent = parent;
		initThread();

	}

	private void addPost(String post) {
		AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Integer result) {
				getPost();
				currentHeight = 0;
				draw();
				rtEditor.setValue("");
			}
		};
		forumSvc.addPost(post, id, authorID, callback);

	}

	public void draw() {

		head.setLeft(500);
		currentHeight += head.getHeight() + 10;
		parent.addChild(head);
		head.setVisible(true);
		for (Post p : posts) {
			p.setTop(currentHeight);
			currentHeight += p.getHeight() + 10;
			p.setLeft(500);
			parent.addChild(p);
			p.setVisible(true);
		}
		tail.setTop(currentHeight);
		tail.setLeft(500);
		tail.setVisible(true);
		parent.addChild(tail);

	}

	public String getName() {
		return name;
	}

	private void getPost() {
		AsyncCallback<PostData[]> callback = new AsyncCallback<PostData[]>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(PostData[] result) {

				for (int i = 0; i < result.length; i++) {

					if (!postID.contains(result[i].getId())) {
						posts.add(new Post(result[i].getId(), result[i]
								.getTopicID(), result[i].getAuthorID(),
								result[i].getDate(), result[i].getText()));
						postID.add(result[i].getId());
					}
				}

			}
		};
		forumSvc.getPosts(id, callback);
	}

	private void initThread() {
		// headsection
		head = new Canvas();
		title = new Label("<div class='threadTitle'>" + name + "</div>");
		title.setWidth(200);
		title.setHeight(30);
		head.addChild(title);
		// head.setContents(name);
		head.setHeight(30);
		head.setWidth(500);
		head.setBackgroundColor("#b0f963");
		head.setBorder("1px solid #000000");
		head.setCanDragReposition(false);
		head.setCanDragResize(false);
		// tailsection
		tail = new Canvas();
		tail.setWidth(550);
		tail.setBackgroundColor("#b0f963");
		tail.setBorder("1px solid #000000");
		tailLayout = new VStack();
		tailLayout.setMembersMargin(5);
		tailLayout.setLayoutMargin(10);
		final Label postLabel = new Label("<h3>Post a post here</h3>");
		postLabel.setWidth(200);
		postLabel.setHeight(30);
		tailLayout.addMember(postLabel);

		rtEditor = new RichTextEditor();
		rtEditor.setHeight(150);
		rtEditor.setWidth(530);
		rtEditor.setCanDragResize(false);
		rtEditor.setShowEdges(true);
		rtEditor.setBackgroundColor("#b0f963");
		tailLayout.addMember(rtEditor);

		submitButton = new Button("Submit!");
		submitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				addPost(rtEditor.getValue());
				rtEditor.setValue("");
			}
		});
		tailLayout.addMember(submitButton);
		tail.addChild(tailLayout);
		// bodysection
		getPost();

	}

	public void kill() {
		currentHeight = 0;
		parent.removeChild(head);
		head.setVisible(false);
		for (Post p : posts) {
			p.setVisible(false);
			parent.removeChild(p);
		}
		parent.removeChild(tail);
		tail.setVisible(false);
	}
}
