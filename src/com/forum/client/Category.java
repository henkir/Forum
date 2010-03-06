package com.forum.client;

import java.util.ArrayList;

import org.apache.tools.ant.taskdefs.FixCRLF.AddAsisRemove;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class Category extends Canvas {

	private boolean hidden = true;
	private String name = "";
	private Label title;
	private Canvas parent;
	private ArrayList<Label> labels = new ArrayList<Label>();
	private IButton addThreadButton = new IButton("Add Topic");

	private int currentHeight = 0;
	private ForumThread currentThread = null;

	public Category(String name, final Canvas parent) {
		super();
		setCanDragReposition(false);
		setCanDragResize(false);
		setWidth(600);
		setHeight(10);
		setBackgroundColor("#b0f963");
		setBorder("1px solid #000000");
		this.name = name;
		this.parent = parent;

		// title label
		title = new Label("<div class='categoryTitle'>" + name
				+ " TopiXX</div>");
		title.setTop(currentHeight);
		title.setHeight(25);
		title.setWidth(200);
		title.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (hidden) {
					unhide();
					killThread();
				}
			}
		});
		addChild(title);
		currentHeight += title.getHeight();
		// button
		addThreadButton.setTop(currentHeight);
		currentHeight += addThreadButton.getHeight();
		addThreadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (!hidden) {
					AddTopicPanel panel = new AddTopicPanel();
					panel.setTop(0);
					panel.setLeft(500);
					hide();
					parent.addChild(panel);
				}
			}
		});
		addChild(addThreadButton);
	}

	private void killThread() {
		currentThread.kill();
		currentThread = null;
	}

	public void addThread(final ForumThread thread) {
		final Label label = new Label("<div class='category'>"
				+ thread.getName() + "</div>");
		label.addClickHandler(new ClickHandler() {
			// fixa lite här
			@Override
			public void onClick(ClickEvent event) {
				if (currentThread != thread) {
					thread.draw();
					hide();
					currentThread = thread;
				}
			}
		});
		labels.add(label);
		label.setHeight(30);
		label.setWidth(getWidth());
		setHeight(getHeight() + label.getHeight());
		label.setTop(currentHeight);
		currentHeight += label.getHeight();
		addChild(label);
	}

	public String getName() {
		return name;
	}

	public void hide() {
		animateRect(250, 0, 200, getHeight(), null, 1000);
		for (Label l : labels)
			l.setWidth(200);
		// setVisible(false);
		if (currentThread != null)
			killThread();
		hidden = true;
	}

	public void unhide() {
		animateRect(500, 0, 600, getHeight(), null, 1000);
		for (Label l : labels)
			l.setWidth(600);
		setVisible(true);
		hidden = false;
	}

}
