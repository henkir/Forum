package com.forum.client;

import com.forum.client.data.ForumServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;

/**
 * A Window containing an editor for editing a post.
 * 
 * @author henrik
 * 
 */
public class EditWindow extends Window {

	private ForumServiceAsync forumSvc;
	private RichTextEditor rtEditor = new RichTextEditor();
	private IButton save = new IButton("Save");
	private IButton cancel = new IButton("Cancel");
	private HStack buttonLayout = new HStack(5);
	private long id;
	private String text;
	private ForumTopic topic;

	/**
	 * Creates a new Window that is centered in the page.
	 * 
	 * @param postId
	 *            the ID of the Post
	 * @param postText
	 *            the text of the Post
	 * @param postTopic
	 *            the Topic that the Post is in
	 * @param forumService
	 *            for making AsyncCallbacks
	 */
	public EditWindow(long postId, String postText, ForumTopic postTopic,
			ForumServiceAsync forumService) {
		this.id = postId;
		this.text = postText;
		this.topic = postTopic;
		this.forumSvc = forumService;
		// Set properties
		setMembersMargin(3);
		setIsModal(true);
		setShowModalMask(true);
		setTitle("Edit post");
		setWidth(550);
		setHeight(210);
		centerInPage();

		// Editor
		rtEditor.setWidth(530);
		rtEditor.setHeight(150);
		rtEditor.setValue(text);
		addItem(rtEditor);
		// Buttons
		buttonLayout.addMember(cancel);
		buttonLayout.addMember(save);
		addItem(buttonLayout);

		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});

		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				forumSvc.updatePost(id, rtEditor.getValue(),
						new AsyncCallback<Boolean>() {

							@Override
							public void onSuccess(Boolean result) {
								if (result) {
									hide();
									topic.redraw();
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								SC.say("Failure", "There was a failure.");
							}
						});
			}
		});
	}

}
