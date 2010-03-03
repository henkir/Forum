package com.forum.client;

import java.util.ArrayList;

import com.smartgwt.client.widgets.Canvas;

public class ForumThread{

	private String name;
	private Canvas parent;
	private Canvas head, tail;
	private ArrayList<Post> posts = new ArrayList<Post>();
	
	public ForumThread(String name, Canvas parent){
		this.name = name;
		this.parent = parent;
		//headsection
		
		
		
		//tailsection
	}
	
	
	public String getName(){
		return name;
	}
	
	public void hide(){
		
		
	}
	
	public void unhide(){
		
	}
}
