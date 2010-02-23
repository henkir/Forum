-- Here are some useful queries for selecting and updating data in the database.
-- Get the id, username and privilege level by supplying the username and password. If no match is found, the login is invalid.
SELECT id, username, priv_level FROM users WHERE username = 'username' AND password = SHA1('password');
-- Get the privilege level for a certain user given id.
SELECT priv_level FROM users WHERE id = 'id';
-- Get all the categories. (Can use SELECT * ...)
SELECT id, name, description FROM categories ORDER BY name;
-- Get all topics (with the authors) for a certain category.
SELECT t1.name, t1.time_created, t2.username AS author FROM topics AS t1 INNER JOIN users AS t2 ON t1.author_id = t2.id WHERE t1.category_id = 'id' ORDER BY t1.time_created;
-- Get all posts (with authors) for a certain topic.
SELECT t1.name, t1.time_posted, t1.post, t2.username AS author FROM posts AS t1 INNER JOIN users AS t2 ON t1.author_id = t2.id WHERE t1.topic_id = 'id' ORDER BY t1.time_posted;
-- Post a new post given a topic, user id, name and post.
INSERT INTO posts(topic_id, author_id, name, time_posted, post) VALUES ('topic_id', 'author_id', 'post name, if any', NOW(), 'the actual post');
-- Delete a post given an id.
DELETE FROM posts WHERE id = 'id';
-- Update a post given id and new content.
UPDATE posts SET post = 'post content' WHERE id = 'id';
