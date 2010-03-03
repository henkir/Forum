package com.forum.client;

import java.util.ArrayList;

import com.smartgwt.client.widgets.Canvas;

public class ForumThread {

	private String name;
	private Canvas parent;
	private Canvas head, tail;
	private ArrayList<Post> posts = new ArrayList<Post>();

	public ForumThread(String name, Canvas parent) {
		this.name = name;
		this.parent = parent;
		// headsection
		head = new Canvas();
		head.setContents(name);
		head.setHeight(30);
		head.setWidth(500);
		head.setBackgroundColor("#b0f963");
		head.setCanDragReposition(false);
		head.setCanDragResize(false);
		// tailsection
		tail = new Canvas();
		// bodysection
		addPost(new Post());

	}

	private void addPost(Post post) {
		posts.add(post);
	}

	public String getName() {
		return name;
	}

	public void draw(){
		head.setLeft(1000);
		parent.addChild(head);
		for(Post p : posts){
			p.setTop(50);
			p.setLeft(1000);
			parent.addChild(p);
		}
	}

	public void hide() {

	}

	public void unhide() {

	}
}
