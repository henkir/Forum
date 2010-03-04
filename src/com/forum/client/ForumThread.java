package com.forum.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.RichTextArea;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;

public class ForumThread {

	private String name;
	private Canvas parent;
	private Canvas head, tail;
	private VStack tailLayout;
	private RichTextEditor rtEditor;
	private Button submitButton;
	private Label title;
	private ArrayList<Post> posts = new ArrayList<Post>();
	private int currentHeight = 0;

	public ForumThread(final String name, Canvas parent) {
		this.name = name;
		this.parent = parent;
		// headsection
		head = new Canvas();
		title = new Label("<div class='threadTitle'>" + name + "</div>");
		title.setWidth(200);
		title.setHeight(30);
		head.addChild(title);
		//head.setContents(name);
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
		
		tailLayout.addMember(new Label("<h3>Posta en post kanske?</h3>"));
		
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
				addPost(new Post(name,rtEditor.getValue()));
				kill();
				currentHeight = 0;
				draw();
				rtEditor.setValue("");
			}
		});
		tailLayout.addMember(submitButton);
		tail.addChild(tailLayout);
		// bodysection
		addPost(new Post(name,"jag gillar henkesex best!"));

	}

	private void addPost(Post post) {
		posts.add(post);
	}

	public String getName() {
		return name;
	}

	public void draw(){
		head.setLeft(500);
		currentHeight += head.getHeight() + 10;
		parent.addChild(head);
		head.setVisible(true);
		for(Post p : posts){
			p.setTop(currentHeight);
			currentHeight += p.getHeight() + 10;
			p.setLeft(500);
			parent.addChild(p);
			p.setVisible(true);
		}
		tail.setTop(currentHeight);
		tail.setLeft(500);
		parent.addChild(tail);
		
		
	}

	public void kill(){
		parent.removeChild(head);
		head.setVisible(false);
		for(Post p : posts){
			p.setVisible(false);
			parent.removeChild(p);
		}
	}
}
