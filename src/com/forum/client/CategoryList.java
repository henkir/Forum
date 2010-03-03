package com.forum.client;

import java.util.ArrayList;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class CategoryList extends Canvas {

	private ArrayList<Category> categories = new ArrayList<Category>();
	private boolean hidden = false;
	private Canvas parent;
	private Category currentCategory;

	public CategoryList(Canvas parent) {
		super();
		this.parent = parent;
		setWidth(600);
		setHeight(100);
		setLeft(500);
		setContents("<div class='categoryHeader'>Forum Categories</div>");

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
		currentCategory = new Category();
		addCategory(currentCategory);
		Canvas grph = currentCategory.getGraphics();
		grph.setTop(25);
		addChild(currentCategory.getGraphics());
	}

	public void addCategory(Category category) {
		categories.add(category);
		setHeight(getHeight() + 10);
		parent.addChild(category);
	}

	public void killCategory() {
		parent.removeChild(currentCategory);
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
