package com.forum.client;

import java.util.ArrayList;

import com.forum.client.data.CategoryData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class CategoryList extends Canvas {

	private ForumServiceAsync forumSvc = GWT.create(ForumService.class);
	private ArrayList<Label> labels = new ArrayList<Label>();
	private final Label title = new Label(
			"<div class='categoryHeader'> Forum Categories </div>");
	private boolean hidden = false;
	private Canvas parent;
	private Category currentCategory = null;

	// kommer ihåg vart
	private int currentHeight = 0;

	public CategoryList(Canvas parent) {
		super();
		this.parent = parent;
		setWidth(600);
		setHeight(10);
		setLeft(500);
		title.setTop(currentHeight);
		title.setHeight(30);
		title.setWidth(200);
		title.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (hidden) {
					unhide();
					killCategory();
				}
			}
		});
		addChild(title);
		currentHeight += title.getHeight();

		setCanDragReposition(false);
		setCanDragResize(false);
		setBorder("1px solid #000000");
		setBackgroundColor("#b0f963");

		getCategories();

	}

	private void getCategories() {
		AsyncCallback<CategoryData[]> callback = new AsyncCallback<CategoryData[]>() {

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("dark_doom");

			}

			@Override
			public void onSuccess(CategoryData[] result) {
				for (int i = 0; i < result.length; i++) {
					addCategory(new Category(result[i].getName(), result[i].getId(), parent));
				}
				

			}

		};
		forumSvc.getCategories(callback);
	}

	public void addCategory(final Category category) {
		final Label label = new Label("<div class='category'>"
				+ category.getName() + "</div>");
		label.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentCategory != category) {
					hide();
					if (currentCategory != null)
						killCategory();
					currentCategory = category;
					parent.addChild(category);
					category.unhide();
				}
			}
		});
		labels.add(label);
		label.setHeight(30);
		label.setWidth(getWidth());
		label.setAlign(Alignment.CENTER);
		setHeight(getHeight() + label.getHeight());
		label.setTop(currentHeight);
		currentHeight += label.getHeight();
		addChild(label);
	}

	public void killCategory() {
		currentCategory.hide();
		parent.removeChild(currentCategory);
		currentCategory = null;
	}

	public void hide() {

		animateRect(0, 0, 200, getHeight(), null, 1000);
		for (Label l : labels)
			l.setWidth(200);
		hidden = true;
	}

	public void unhide() {
		animateRect(500, 0, 600, getHeight(), null, 1000);
		for (Label l : labels)
			l.setWidth(600);
		hidden = false;
	}

}
