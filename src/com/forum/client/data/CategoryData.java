package com.forum.client.data;

import java.io.Serializable;

public class CategoryData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6947233962770339525L;
	String name;
	int id;

	
	public CategoryData(){
		
	}
	public CategoryData(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
