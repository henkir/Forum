package com.forum.client;

import java.io.Serializable;

import com.google.gwt.dom.client.Text;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class AddTopicPanel extends Canvas {

	private VStack layout = new VStack();
	private RichTextEditor rtEditor;
	private HStack topicField = new HStack();
	private Label title = new Label(
			"<div class='threadTitle'>Create new Topic </div>");
	private DynamicForm form = new DynamicForm();
	private IButton submitButton = new IButton("Submit Topic");

	public AddTopicPanel(){
		
	}
	
	public AddTopicPanel(int catID) {
		// instance
		super();
		setCanDragReposition(false);
		setCanDragResize(false);
		setBackgroundColor("#b0f963");
		setBorder("1px solid #000000");
		setHeight(100);
		setWidth(200);

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
		//title
		title.setWidth(200);
		title.setHeight(45);
		
		//Button
		submitButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String topicName = form.getValue("textField").toString();
				String text = rtEditor.getValue();
				System.out.println(text);
				if(topicName.equals("")){
					SC.say("Topic name cannot be empty");
					form.setValue("textField", "");

				}
				if(text.equals("<br>")){
					SC.say("Post cannot be empty");
					rtEditor.setValue("");

				}
				
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
		layout.addMember(submitButton);

		addChild(layout);
	}

}
