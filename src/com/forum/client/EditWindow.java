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

public class EditWindow extends Window {

	private ForumServiceAsync forumSvc;
	private RichTextEditor rtEditor = new RichTextEditor();
	private IButton save = new IButton("Save");
	private IButton cancel = new IButton("Cancel");
	private HStack buttonLayout = new HStack(5);
	private long id;
	private String text;
	private ForumThread topic;

	public EditWindow(long postId, String postText, ForumThread postTopic,
			ForumServiceAsync forumService) {
		this.id = postId;
		this.text = postText;
		this.topic = postTopic;
		this.forumSvc = forumService;
		setMembersMargin(3);
		setIsModal(true);
		setShowModalMask(true);
		setTitle("Edit post");
		rtEditor.setWidth(530);
		rtEditor.setHeight(150);
		rtEditor.setContents(postText);
		addItem(rtEditor);
		buttonLayout.addMember(cancel);
		buttonLayout.addMember(save);
		addItem(buttonLayout);
		setWidth(550);
		setHeight(210);
		centerInPage();

		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});

		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				forumSvc.updatePost(id, text, new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							// topic.redraw();
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
