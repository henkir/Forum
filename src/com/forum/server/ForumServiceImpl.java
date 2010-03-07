package com.forum.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.forum.client.Category;
import com.forum.client.ForumService;
import com.forum.client.ForumThread;
import com.forum.client.Post;
import com.forum.client.data.CategoryData;
import com.forum.client.data.PostData;
import com.forum.client.data.TopicData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.smartgwt.client.widgets.Canvas;

public class ForumServiceImpl extends RemoteServiceServlet implements
		ForumService {
	
	private DatabaseConnection connection = null;
	
	public ForumServiceImpl() {
		
		 connection = new DatabaseConnection(true);
	}

	@Override
	public int addPost(String text, int thrID, int authID) {
		
		String query = "INSERT INTO posts(topic_id,author_id,time_posted,post) VALUES ("
				+ thrID + ", " + authID + ", NOW(), '" + text + "');";
		Statement statement = connection.getStatement();
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int addThread(String name, int catID, int authID) {
		String query = "INSERT INTO topics(category_id, author_id, name, time_created) VALUES ("
			+ catID + ", " + authID + ", '" + name + "', NOW());";
		Statement statement = connection.getStatement();
		try{
			statement.executeUpdate(query);
		}catch (SQLException e) {
			
		}
		return 0;
	}

	@Override
	public CategoryData[] getCategories() {
		ArrayList<CategoryData> result = new ArrayList<CategoryData>();
		ResultSet rs;
		String query = "SELECT id, name, description FROM categories ORDER BY position;";
		Statement statement = connection.getStatement();
		try {
			rs = statement.executeQuery(query);
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
			
				result.add(new CategoryData(name, id));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return result.toArray(new CategoryData[0]);
	}

	@Override
	public PostData[] getPosts(int threadID) {
		
		ArrayList<PostData> result = new ArrayList<PostData>();
		ResultSet rs;
		String query = "SELECT t1.id, t1.time_posted, t1.post, t2.id AS author FROM posts AS t1 INNER JOIN users AS t2 ON t1.author_id = t2.id WHERE t1.topic_id = '" + threadID +"' ORDER BY t1.time_posted;";
		Statement statement = connection.getStatement();
		try {
			rs = statement.executeQuery(query);
			while(rs.next()){
				String date = rs.getString("time_posted");
				String text = rs.getString("post");
				long id = rs.getLong("id");
				int authID = rs.getInt("author");
			
				result.add(new PostData(id, threadID, authID, date, text));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result.toArray(new PostData[0]);
	}

	@Override
	public TopicData[] getThreads(int categoryID) {
		ArrayList<TopicData> result = new ArrayList<TopicData>();
		ResultSet rs;
		String query = "SELECT t1.id, t1.name, t1.time_created,t1.category_id, t2.id AS author FROM topics AS t1 INNER JOIN users AS t2 ON t1.author_id = t2.id WHERE t1.category_id = '" + categoryID +"' ORDER BY t1.time_created;";
		Statement statement = connection.getStatement();
		try {
			rs = statement.executeQuery(query);
			while(rs.next()){
				String date = rs.getString("time_created");
				String name = rs.getString("name");
				int id = rs.getInt("id");
				int catID = rs.getInt("category_id");
				int authID = rs.getInt("author");
				result.add(new TopicData(id, catID, authID, name, date));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return result.toArray(new TopicData[0]);
	}

}
