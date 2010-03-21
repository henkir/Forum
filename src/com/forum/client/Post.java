package com.forum.client;

import java.io.Serializable;

import com.forum.client.data.ForumService;
import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.Privileges;
import com.forum.client.data.SessionHandler;
import com.forum.client.data.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * Component for showing a post
 * 
 * @author henho106 + jonhe442
 * 
 */
public class Post extends Canvas implements Serializable {

	private static final long serialVersionUID = -4293489942482413197L;
	// layout
	private HStack layout = new HStack();
	// Text of the post
	private String text;
	// Image of the user avatar
	private Img avatar = new Img("mr_unknown.png");
	// the display of the posts text
	private HTMLFlow textDisplay = new HTMLFlow("");
	// post id
	private long id;
	private int threadID;
	private int authorID;
	private String date;
	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	private String userName;
	private VStack avatarLayout = new VStack();
	private Label nameLabel;

	private IButton editPost = new IButton("Edit");
	private IButton removePost = new IButton("Remove");
	private ForumTopic topic;

	/**
	 * Constructor taking all the values from the database
	 * 
	 * @param id
	 *            Post ID
	 * @param thID
	 *            Topics ID
	 * @param auID
	 *            Authors ID
	 * @param date
	 *            Date posted
	 * @param text
	 *            Text of the post
	 */
	public Post(long id, int thID, int auID, String date, String text,
			ForumTopic topic) {
		super();
		this.text = text;
		this.authorID = auID;
		this.id = id;
		this.threadID = thID;
		this.date = date;
		this.topic = topic;
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

		forumSvc.getUser(authorID, callback);

	}

	/**
	 * Initializes the component
	 */
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

		layout.setWidth(550);
		layout.setHeight(200);
		layout.setLayoutMargin(10);
		layout.setMembersMargin(5);
		layout.addMember(avatarLayout);
		addChild(layout);
		// html part
		textDisplay.setContents("<p>" + text + " </p>");
		layout.addMember(textDisplay);

		editPost.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				EditWindow window = new EditWindow(id, text, topic, forumSvc);
				window.draw();
			}
		});

		removePost.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SC.ask("Remove", "Do you want to remove this post?",
						new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									forumSvc.removePost(id,
											new AsyncCallback<Boolean>() {

												@Override
												public void onFailure(
														Throwable caught) {
													SC
															.say("Failure",
																	"There was a failure.");
												}

												@Override
												public void onSuccess(
														Boolean result) {
													if (result) {
														topic.redraw();
													}
												}
											});
								}
							}
						});

			}
		});

		forumSvc.hasPrivileges(Privileges.MODERATOR, SessionHandler
				.getSessionId(), new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Failure", "There was a failure.");
			}

			@Override
			public void onSuccess(Integer result) {
				if (result == 2) {
					avatarLayout.addMember(editPost);
					avatarLayout.addMember(removePost);
				}
			}
		});
	}

}
