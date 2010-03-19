package com.forum.client;

import java.io.Serializable;
import java.util.ArrayList;

import com.forum.client.data.ForumService;
import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.PostData;
import com.forum.client.data.SessionHandler;
import com.forum.client.data.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * A structure for a topic in the forum
 * 
 * @author jonas
 * 
 */
public class ForumTopic implements Serializable {
	// service instance
	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	private static final long serialVersionUID = 1L;
	// name of the topic
	private String name;
	// parent that holds the components
	private Canvas parent;
	// the heading and the component for adding posts
	private Canvas head, tail;
	// the layout for the add post component
	private VStack tailLayout;
	// the text editor for add post component
	private RichTextEditor rtEditor;
	// button for submitting post
	private IButton submitButton;
	// label for heading
	private Label title;
	// posts of the topic
	private ArrayList<Post> posts = new ArrayList<Post>();
	// keeps track of where to put the next component
	private int currentHeight = 0;
	// ID of the topic
	private int id;
	// ID of the category the topic belongs to
	private int categoryID;
	// the author of the topic
	private int authorID;
	// the date the topic was created
	private String date;
	// IDs of the posts
	private ArrayList<Long> postID = new ArrayList<Long>();

	/**
	 * Constructor taking all the info from the database
	 * 
	 * @param id
	 *            Topic ID
	 * @param catID
	 *            Category ID
	 * @param auID
	 *            Authors ID
	 * @param name
	 *            Topics name
	 * @param date
	 *            Date of creation
	 * @param parent
	 *            Where the component will lie
	 */
	public ForumTopic(int id, int catID, int auID, String name, String date,
			Canvas parent) {
		this.id = id;
		this.categoryID = catID;
		this.authorID = auID;
		this.name = name;
		this.date = date;
		this.parent = parent;
		initThread();
	}

	/**
	 * Costructor that takes all the info from the database plus the initial
	 * posts text
	 * 
	 * @param id
	 *            Topic ID
	 * @param catID
	 *            Category ID
	 * @param auID
	 *            Author ID
	 * @param name
	 *            Topic name
	 * @param date
	 *            Date of creation
	 * @param parent
	 *            Where the component will lie
	 * @param initPost
	 *            The text of the initial post
	 */
	public ForumTopic(int id, int catID, int auID, String name, String date,
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

	/**
	 * Adds a post to the topic
	 * 
	 * @param post
	 *            The text of the post to be added
	 */
	private void addPost(final String post) {
		final AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Integer result) {
				redraw();
			}
		};
		forumSvc.getUser(SessionHandler.getSessionId(),
				new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say("Failure", "There was a failure.");
					}

					@Override
					public void onSuccess(final User result) {
						int userId = result.getId();
						forumSvc.addPost(post, id, userId, callback);

					}
				});

	}

	public void redraw() {
		for (int i = 0; i < posts.size(); i++) {
			parent.removeChild(posts.get(i));
		}
		postID.clear();
		posts.clear();
		getPost();
		Timer timer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				draw();

			}

		};
		timer.schedule(500);

	}

	/**
	 * Draws the component
	 */
	public void draw() {

		head.setLeft(500);
		currentHeight += head.getHeight() + 10;
		parent.addChild(head);
		head.setVisible(true);
		for (Post p : posts) {
			p.setTop(currentHeight);
			if (p.getHeight() != null)
				currentHeight += p.getHeight() + 10;
			else
				currentHeight += 210;
			p.setLeft(500);
			parent.addChild(p);
			p.setVisible(true);
		}
		tail.setTop(currentHeight);
		tail.setLeft(500);
		tail.setVisible(true);
		forumSvc.isLoggedIn(SessionHandler.getSessionId(),
				new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							parent.addChild(tail);
						}
					}
				});
		currentHeight = 0;
	}

	/**
	 * Gets the name of the topic
	 * 
	 * @return Topic name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets topic ID
	 * 
	 * @return topic ID
	 */
	public int getID() {
		return id;
	}

	/**
	 * Gets the posts belonging to the topic
	 */
	private void getPost() {
		AsyncCallback<PostData[]> callback = new AsyncCallback<PostData[]>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(PostData[] result) {
				boolean remove;

				for (int i = 0; i < postID.size(); i++) {
					remove = true;
					for (int j = 0; j < result.length; j++) {
						if (postID.get(i) == result[i].getId()) {
							remove = false;
							break;
						}
						if (remove) {
							postID.remove(i);
							posts.remove(i);
						}
					}
				}

				for (int i = 0; i < result.length; i++) {

					if (!postID.contains(result[i].getId())) {
						posts.add(new Post(result[i].getId(), result[i]
								.getTopicID(), result[i].getAuthorID(),
								result[i].getDate(), result[i].getText(),
								getInstance()));
						postID.add(result[i].getId());
					}
				}

			}
		};
		forumSvc.getPosts(id, callback);
	}

	private ForumTopic getInstance() {
		return this;
	}

	/**
	 * Initializes all the components of the topic
	 */
	private void initThread() {
		// headsection
		head = new Canvas();
		title = new Label("<div class='threadTitle'>" + name + "</div>");
		title.setWidth(200);
		title.setHeight(30);
		head.addChild(title);
		// head.setContents(name);
		head.setHeight(30);
		head.setWidth(550);
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
		final Label postLabel = new Label("<h3>Post a reply</h3>");
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

		submitButton = new IButton("Submit!");
		submitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!rtEditor.getValue().equals("<br>")) {
					addPost(rtEditor.getValue());
					rtEditor.setValue("");
					// /currentHeight = 0;
					// redraw();
				} else {
					SC.say("Post cannot be empty");
				}
			}
		});
		tailLayout.addMember(submitButton);
		tail.addChild(tailLayout);
		// bodysection
		getPost();

	}

	/**
	 * Removes the topic
	 */
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
