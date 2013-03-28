package com.anthavio.disquo.simulator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.UnhandledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.anthavio.disquo.api.DisqusServerException;
import com.anthavio.disquo.api.response.DisqusForum;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusThread;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DbDataStore {

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private ObjectMapper jackson;

	private RowMapper<DisqusForum> forumMapper;

	private RowMapper<DisqusThread> threadMapper;

	private RowMapper<DisqusPost> postMapper;

	private Map<String, DisqusForum> forums = new HashMap<String, DisqusForum>();

	private Map<Long, DisqusThread> threads = new HashMap<Long, DisqusThread>();

	private Map<Long, DisqusPost> posts = new HashMap<Long, DisqusPost>();

	private String object2Json(Object o) {
		try {
			return jackson.writeValueAsString(o);
		} catch (JsonProcessingException jpx) {
			throw new UnhandledException(jpx);
		}
	}

	private <T> T json2Object(String json, Class<T> klass) {
		try {
			return jackson.readValue(json, klass);
		} catch (IOException iox) {
			throw new UnhandledException(iox);
		}
	}

	public DisqusForum getForum(String forumId) {
		DisqusForum forum = forums.get(forumId);
		if (forum == null) {
			String sql = "SELECT * FROM DQ_FORUM WHERE ID = ?";
			forum = jdbc.queryForObject(sql, forumMapper, forumId);
			if (forum != null)
				forums.put(forumId, forum);
		}
		return forum;
	}

	public DisqusThread getThread(long threadId) {
		DisqusThread thread = threads.get(threadId);
		if (thread == null) {
			String sql = "SELECT * FROM DQ_THREAD WHERE ID = ?";
			thread = jdbc.queryForObject(sql, threadMapper, threadId);
			if (thread != null) {
				threads.put(threadId, thread);
			}
		}
		return thread;
	}

	public DisqusPost getPost(long postId) {
		DisqusPost post = posts.get(postId);
		if (post == null) {
			String sql = "SELECT * FROM DQ_POST WHERE ID = ?";
			post = jdbc.queryForObject(sql, postMapper, postId);
			if (post != null) {
				posts.put(postId, post);
			}
		}
		return post;

	}

	public DisqusForum createForum(DisqusForum forum) {
		DisqusForum colision = getForum(forum.getId());
		if (colision != null) {
			throw new DisqusServerException(400, 2, "Invalid argument, 'short_name' : Forum of this name already exists");
		}
		String sql = "INSERT INTO DQ_FORUM (ID, NAME, FOUNDER, STATE, JSON) VALUES (?, ?, ?, ?, ?)";
		String json = object2Json(forum);
		jdbc.update(sql, forum.getId(), forum.getName(), forum.getFounder(), 0, json);
		return forum;
	}

	public DisqusThread createThread(DisqusThread thread) {
		long threadId = jdbc.queryForLong("NEXT VALUE FOR SEQ_THREAD");
		thread.setId(threadId);
		String sql = "INSERT INTO DQ_THREAD (ID, FORUM, CATEGORY, AUTHOR, TITLE, CREATED, STATE, JSON) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String json = object2Json(thread);
		jdbc.update(sql, threadId, thread.getForum(), thread.getCategory(), thread.getAuthor(), thread.getTitle(),
				thread.getCreatedAt(), 0, json);
		return thread;
	}

	/*
		public List<DisqusThread> listThreads(String forumId) {
			ForumWithThreads forumX = allForums.get(forumId);
			Collection<DisqusThread> threads = forumX.threads.values();
			return new ArrayList<DisqusThread>(threads);
		}
	*/
	public DisqusPost createPost(DisqusPost post) {
		long postId = jdbc.queryForLong("NEXT VALUE FOR SEQ_POST");
		post.setId(postId);
		String sql = "INSERT INTO DQ_POST (ID, PARENT, THREAD, FORUM, AUTHOR, CREATED, STATE, JSON) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String json = object2Json(post);
		jdbc.update(sql, postId, post.getParent(), post.getThread(), post.getForum(), post.getAuthor(),
				post.getCreatedAt(), 0, json);
		return post;
	}
	/*
		public List<DisqusPost> listPosts(long threadId) {
			Collection<DisqusPost> posts = allThreads.get(threadId).posts.values();
			return new ArrayList<DisqusPost>(posts);
		}

		public DisqusPost postDetail(long postId) {
			return this.allPosts.get(postId);

		}

		public DisqusUser getUserByEmail(String author_email) {
			return users.get(author_email);
		}

		public DisqusUser getUserByAccessToken(String access_token) {
			return users.get(access_token);
		}

		public DisqusApplication getApplicationByApiKey(String api_key) {
			return applications.get(api_key);
		}

		public DisqusApplication getApplicationBySecretKey(String secret_key) {
			Collection<DisqusApplication> values = applications.values();
			for (DisqusApplication app : values) {
				if (app.getKeys().getSecretKey().equals(secret_key)) {
					return app;
				}
			}
			return null;
		}

		public static class DisqusApplication {

			private final DisqusUser user;

			private final DisqusApplicationKeys keys;

			public DisqusApplication(DisqusUser user, DisqusApplicationKeys keys) {
				this.user = user;
				this.keys = keys;
			}

			public DisqusUser getUser() {
				return user;
			}

			public DisqusApplicationKeys getKeys() {
				return keys;
			}

		}

		private class ForumWithThreads {

			private DisqusForum forum;
			//Threads of this Forum
			private Map<Long, DisqusThread> threads = new HashMap<Long, DisqusThread>();

			public ForumWithThreads(DisqusForum forum) {
				this.forum = forum;
			}
		}

		private class ThreadWithPosts {

			private DisqusThread thread;
			//Posts of this Thread
			private Map<Long, DisqusPost> posts = new HashMap<Long, DisqusPost>();

			public ThreadWithPosts(DisqusThread thread) {
				this.thread = thread;
			}
		}

		public DisqusUser createUser(String author_email, String author_name, String author_url) {
			long userId = userCounter.incrementAndGet();
			DisqusUser user = new DisqusUser();
			users.put(author_email, user);

			user.setId(userId);
			user.setEmail(author_email);
			user.setEmailHash(null);//TODO

			user.setJoinedAt(new Date());
			user.setUsername(author_name);
			user.setName(author_name);
			user.setProfileUrl(author_url);
			user.setUrl(author_url);

			user.setIsAnonymous(true);
			user.setIsFollowedBy(false);
			user.setIsFollowing(false);
			user.setIsPrimary(false);

			user.setNumFollowers(0);
			user.setNumFollowing(0);
			user.setNumLikesReceived(0);
			user.setNumPosts(0);
			user.setRemote(null);
			user.setReputation(1.2f);
			return user;
		}

		private void createInitialData() {
			TestInputData tidNature = TestInputData.load("disqus-nature.properties");
			initData(tidNature);

			TestInputData tidDajc = TestInputData.load("disqus-dajc.properties");
			initData(tidDajc);
		}

		private void initData(TestInputData tidata) {
			DisqusApplicationKeys keys = tidata.getApplicationKeys();

			DisqusUser admin = tidata.getAdmin();
			//can be found by 2 different keys - email or access_token
			users.put(admin.getEmail(), admin);
			users.put(keys.getAccessToken(), admin);

			DisqusApplication application = new DisqusApplication(admin, keys);
			applications.put(keys.getApiKey(), application);

			DisqusUser ssoUser1 = tidata.getSsoUser1();
			users.put(ssoUser1.getEmail(), ssoUser1);

			DisqusUser ssoUser2 = tidata.getSsoUser2();
			users.put(ssoUser2.getEmail(), ssoUser2);

			DisqusForum forum = new DisqusForum();
			forum.setId(tidata.getForum());
			forum.setName(tidata.getForum());
			forum.setFounder(admin.getId());
			forum.setAuthor(admin);

			ForumWithThreads forumx = new ForumWithThreads(forum);
			allForums.put(forum.getId(), forumx);

			DisqusCategory category = new DisqusCategory();
			category.setForum(forum.getId());
			category.setId(0l); //Hardcoded id!
			category.setIsDefault(true);
			category.setOrder(1);
			category.setTitle("Default");
			allCategories.put(category.getId(), category);

			DisqusThread thread = new DisqusThread();
			thread.setId(tidata.getLongThreadId());
			thread.setForum(forum);
			thread.setAuthor(admin);

			thread.setTitle("Test Thread");
			thread.setCreatedAt(new Date());
			thread.setCategory(category);

			thread.setInitialValues(); //integer and boolean values

			ThreadWithPosts threadx = new ThreadWithPosts(thread);
			allThreads.put(thread.getId(), threadx);
			forumx.threads.put(thread.getId(), thread);

			DisqusPost post = new DisqusPost();
			post.setId(0l); //Hardcoded id!
			post.setForum(forum);
			post.setAuthor(admin);
			post.setThread(thread);
			String message = "Trololo!";
			post.setMessage("<p>" + message + "</p>");
			post.setRaw_message(message);
			post.setIpAddress("127.0.0.1");
			post.setCreatedAt(new Date());

			post.setInitialValues(); //integer and boolean values

			allPosts.put(post.getId(), post);
			threadx.posts.put(post.getId(), post);
		}
	*/
}
