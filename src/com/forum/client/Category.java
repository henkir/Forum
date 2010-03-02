package com.forum.client;




import com.smartgwt.client.widgets.Canvas;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;



public class Category extends Canvas {

	private boolean hidden = true;
	private Canvas gfx;
	private String name = "BögSEX";
	

	public Category() {
		super();
		setContents("<h2> <center>" + name + " TopiXX</center></h2>");
		setCanDragReposition(false);
		setCanDragResize(false);
		setBorder("1px solid #000000");
		setBackgroundColor("#b0f963");
		gfx = makePanel();

		hide();
	}

	public Canvas getGraphics() {
		return gfx;
	}

	public void hide() {
		animateRect(0, 0, 0, 0, null, 1000);
		setVisible(false);
		hidden = true;
	}

	public void unhide() {
		animateRect(500, 10, 600, getHeight(), null, 1000);
		setVisible(true);
		hidden = false;
	}

	public Canvas makePanel() {
		Canvas result = new Canvas();
		result.setContents("<center><link> " + name + " </link></center>");
		result.setWidth(600);
		result.setHeight(15);
		result.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (hidden) {
					unhide();

				} else {
					hide();

				}
			}
		});

		return result;
	}

}
