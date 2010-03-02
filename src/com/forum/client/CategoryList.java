package com.forum.client;

import java.util.ArrayList;

import com.google.gwt.layout.client.Layout.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class CategoryList extends Canvas {

	private ArrayList<Category> categories = new ArrayList<Category>();
	private boolean hidden = false;
	private Canvas parent;
	private Category currenCategory;

	public CategoryList(Canvas parent) {
		super();
		this.parent = parent;
		setWidth(600);
		setHeight(100);
		setLeft(500);
		setContents("<h2> <center>Forum Categories</center></h2>");
		
		setCanDragReposition(false);
		setCanDragResize(false);
		setBorder("1px solid #000000");
		setBackgroundColor("#b0f963");
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (hidden) {
					unhide();
					killCategory();
				} else {
					hide();
					categories.get(0).unhide();
				}
			}
		});
		currenCategory = new Category();
		addCategory(currenCategory);
		Canvas grph = currenCategory.getGraphics();
		grph.setTop(25);
		addChild(currenCategory.getGraphics());
	}

	public void addCategory(Category category) {
		categories.add(category);
		setHeight(getHeight() + 10);
		parent.addChild(category);
	}
	
	public void killCategory(){
		parent.removeChild(currenCategory);
	}

	public void hide() {
		animateRect(0, 0, 200, getHeight(), null, 1000);
		hidden = true;
	}

	public void unhide() {
		animateRect(500, 0, 600, getHeight(), null, 1000);
		hidden = false;
	}

}
