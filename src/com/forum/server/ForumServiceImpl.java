package com.forum.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.forum.client.ForumService;
import com.forum.client.ForumThread;
import com.forum.client.Post;
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
	public String[] getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post[] getPosts(int threadID) {
		// TODO Auto-generated method stub
		ArrayList<Post> result = new ArrayList<Post>();
		ResultSet rs;
		String query = "SELECT t1.time_posted, t1.post, t2.username AS author FROM posts AS t1 INNER JOIN users AS t2 ON t1.author_id = t2.id WHERE t1.topic_id = '" + threadID +"' ORDER BY t1.time_posted;";
		Statement statement = connection.getStatement();
		try {
			rs = statement.executeQuery(query);
			while(rs.next()){
				String date = rs.getString("date");
				String text = rs.getString("post");
			
				//result.add(new Post());
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ForumThread[] getThreads(int categoryID, Canvas parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
