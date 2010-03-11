-- Drop database if it exists so we can start over.
DROP DATABASE IF EXISTS forum;
-- Create the database for us to work in and select it.
CREATE DATABASE forum;
USE forum;
-- All suitable tables should be connected with foreign keys for easier maintenance. Requires InnoDB as engine.
-- Create the users table which contains IDs, usernames, passwords and privileges.
-- Security should be improved with salt, and more information could be stored.
-- Privilege levels could be moved to a separate table for descriptions, more levels, etc.
CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(20) NOT NULL UNIQUE,
                    password CHAR(40) NOT NULL,
                    priv_level TINYINT DEFAULT 0
                    ) ENGINE=MyISAM;
-- Create the table for all categories.
CREATE TABLE categories (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(20) NOT NULL UNIQUE,
                         description VARCHAR(100) NOT NULL,
                         position INT DEFAULT 0
                         ) ENGINE=MyISAM;
-- Create the table for topics, they will reference to a category that they belong to.
-- Could add additional information.
CREATE TABLE topics (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                     category_id INT NOT NULL,
                     author_id INT NOT NULL,
                     name VARCHAR(30) NOT NULL,
		     time_created DATETIME
                     ) ENGINE=MyISAM;
-- Create the table for posts, they will reference to a topic that they belong to, and an ID to the author.
-- Could add additional information about last edit, by whom, etc.
CREATE TABLE posts (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    topic_id INT NOT NULL,
                    author_id INT NOT NULL,
                    name VARCHAR(30) DEFAULT '',
                    time_posted DATETIME NOT NULL,
                    post TEXT NOT NULL
                    ) ENGINE=MyISAM;



-- Create a new user for the forum. Comment out the next two lines if the user is already created.
-- CREATE USER 'forum_user';
-- SET PASSWORD FOR 'forum_user' = PASSWORD('forum_pass');
-- GRANT SELECT, INSERT, UPDATE, DELETE ON forum.* TO 'forum_user';



-- Create test data.
-- Create a normal user with test:test.
INSERT INTO users(username, password) VALUES ('test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3');
-- Create a super admin with highest privileges, admin:admin.
INSERT INTO users(username, password, priv_level) VALUES ('admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 2);
-- Create a moderator with medium privileges, moder:moder.
INSERT INTO users(username, password, priv_level) VALUES ('moder', 'b36c04f4f2496b9525646ec01ff51e2007e79a2c', 1);
-- Create some categories.
INSERT INTO categories(name, description) VALUES ('Music', 'Discussions about music go here.');
INSERT INTO categories(name, description) VALUES ('Politics', 'Discussions about politics go here.');
INSERT INTO categories(name, description) VALUES ('General', 'If your topic does not fit in any other category, this is where it should go.');
-- Create some topics
INSERT INTO topics(category_id, author_id, name, time_created) VALUES (1, 1, 'Your favorite music genre', NOW());
INSERT INTO topics(category_id, author_id, name, time_created) VALUES (1, 2, 'Your favorite instrument', NOW());
INSERT INTO topics(category_id, author_id, name, time_created) VALUES (1, 3, 'The best music', NOW());
INSERT INTO topics(category_id, author_id, name, time_created) VALUES (2, 1, 'Idealogy', NOW());
INSERT INTO topics(category_id, author_id, name, time_created) VALUES (3, 3, 'Favorite food', NOW());
-- Create some posts
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (1, 1, NOW(), 'What is your favorite music genre?');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (1, 2, NOW(), 'I like jazz.');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (1, 3, NOW(), 'I prefer smooth ballads.');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (2, 1, NOW(), 'What is your favorite instrument? I like guitar.');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (2, 1, NOW(), 'I like the piano.');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (3, 3, NOW(), 'The best music is my music.');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (4, 1, NOW(), 'What ideology is the best?');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (4, 3, NOW(), 'Dont ask.');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (5, 3, NOW(), 'My favorite food is pizza. What is yours?');
INSERT INTO posts(topic_id, author_id, time_posted, post) VALUES (5, 1, NOW(), 'I like mashed potatoes.');
