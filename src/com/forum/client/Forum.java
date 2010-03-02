package com.forum.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.*;  
import com.smartgwt.client.widgets.events.*;  
/**
 * Forum is the main class in the project and is the entry point. The first
 * executed method is onModuleLoad.
 * 
 * @author henho106 + jonhe442
 * 
 */
public class Forum implements EntryPoint {
	Canvas canvas = new Canvas();
	CategoryList catList = new CategoryList(canvas);
	public void onModuleLoad() {
		// Test of admin panel.
		new AdminPanel();
		new RegistrationForm().draw();
		
		
		//the forum itself
		
		//the canvas
		canvas.setWidth100();
		canvas.setHeight100();
		canvas.addChild(catList);
		canvas.draw();
		
		
		
		RootPanel.get("main").add(canvas);
		
		
	}
}
