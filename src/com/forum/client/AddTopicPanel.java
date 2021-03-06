package com.forum.client;

import com.forum.client.data.ForumService;
import com.forum.client.data.ForumServiceAsync;
import com.forum.client.data.SessionHandler;
import com.forum.client.data.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * A component for adding topics
 * 
 * @author henho106 + jonhe442
 * 
 */
public class AddTopicPanel extends Canvas {
	// service
	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	// layout of component
	private VStack layout = new VStack();
	// editor for the initial post
	private RichTextEditor rtEditor;
	// layout for topicfield
	private HStack topicField = new HStack();
	// label for heading
	private Label title = new Label(
			"<div class='threadTitle'>Create new Topic </div>");
	// A dynamic form for setting the topic name
	private DynamicForm form = new DynamicForm();
	// button for submitting the new post
	private IButton submitButton = new IButton("Submit Topic");
	// button for canceling
	private IButton cancelButton = new IButton("Cancel");
	// Category ID
	private int catID;
	// Reference to the parent and the category
	private Canvas parent, cat;

	/**
	 * Constructor for the component
	 * 
	 * @param catID
	 *            ID of the category
	 * @param parent
	 *            Parent where the component will lie
	 * @param cat
	 *            The category component
	 */
	public AddTopicPanel(final int catID, final Canvas parent,
			final Category cat) {
		// instance
		super();
		this.catID = catID;
		this.parent = parent;
		this.cat = cat;
		setCanDragReposition(false);
		setCanDragResize(false);
		setBackgroundColor("#b0f963");
		setBorder("1px solid #000000");
		setHeight(100);
		setWidth(200);
		setTop(0);
		setLeft(500);

		// topicfield
		form = new DynamicForm();
		form.setGroupTitle("Topic Title");
		form.setBackgroundColor("#b0f963");
		form.setIsGroup(true);
		form.setWidth(300);
		form.setHeight(30);
		form.setNumCols(2);
		form.setColWidths(60, "*");
		form.setPadding(5);

		TextItem subjectItem = new TextItem();
		subjectItem.setTitle("Title");
		subjectItem.setWidth("*");
		subjectItem.setName("textField");
		subjectItem.setValue("");

		form.setFields(subjectItem);
		// title
		title.setWidth(200);
		title.setHeight(45);

		// Button
		submitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String topicName = form.getValue("textField").toString();
				final String text = rtEditor.getValue();
				if (topicName.equals("")) {
					SC.say("Topic name cannot be empty");
					form.setValue("textField", "");

				} else if (text.equals("<br>")) {
					SC.say("Post cannot be empty");
					rtEditor.setValue("");

				} else {
					AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

						@Override
						public void onFailure(Throwable caught) {
							System.err.println("fan");

						}

						@Override
						public void onSuccess(Integer result) {

							final int topicID = result;
							forumSvc.getUser(SessionHandler.getSessionId(),
									new AsyncCallback<User>() {

										@Override
										public void onFailure(Throwable caught) {
											SC.say("Failure",
													"There was a failure.");
										}

										@Override
										public void onSuccess(final User result) {
											int userId = result.getId();
											forumSvc
													.addPost(
															text,
															topicID,
															userId,
															new AsyncCallback<Integer>() {

																@Override
																public void onFailure(
																		Throwable caught) {

																}

																@Override
																public void onSuccess(
																		Integer result) {

																	cat
																			.setTopic(topicID);

																}
															});

										}
									});

							parent.removeChild(getThis());

						}

					};
					forumSvc.addThread(topicName, catID, catID, callback);

				}

			}
		});

		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				parent.removeChild(getThis());
				cat.unhide();
				destroy();
			}
		});

		// editor
		rtEditor = new RichTextEditor();
		rtEditor.setHeight(150);
		rtEditor.setWidth(530);
		rtEditor.setCanDragResize(false);
		rtEditor.setShowEdges(true);
		rtEditor.setBackgroundColor("#b0f963");
		rtEditor.setValue("");

		// layout
		layout.setLayoutMargin(10);
		layout.setMembersMargin(5);
		layout.setWidth(500);
		layout.setHeight(200);
		layout.addMember(title);
		layout.addMember(form);
		layout.addMember(rtEditor);
		HStack buttonLayout = new HStack(5);
		buttonLayout.addMember(cancelButton);
		buttonLayout.addMember(submitButton);
		layout.addMember(buttonLayout);

		addChild(layout);
		parent.addChild(this);
	}

	/**
	 * Help for nested classes
	 * 
	 * @return The component
	 */
	private Canvas getThis() {
		return this;
	}

}
