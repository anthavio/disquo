package com.anthavio.disquo.simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.anthavio.disquo.api.DisqusApplicationKeys;
import com.anthavio.disquo.api.DisqusServerException;
import com.anthavio.disquo.api.response.DisqusCategory;
import com.anthavio.disquo.api.response.DisqusForum;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.response.DisqusUser;

/**
 * 
 * @author martin.vanek
 *
 */
@Service
public class StoreService {

	private AtomicLong categoryCounter = new AtomicLong();

	private AtomicLong threadCounter = new AtomicLong();

	private AtomicLong postCounter = new AtomicLong();

	private AtomicLong userCounter = new AtomicLong();

	private Map<Long, DisqusCategory> allCategories = new HashMap<Long, DisqusCategory>();

	private Map<String, ForumWithThreads> allForums = new HashMap<String, ForumWithThreads>();

	//All Threads in the World
	private Map<Long, ThreadWithPosts> allThreads = new HashMap<Long, ThreadWithPosts>();

	//All Posts in the World
	private Map<Long, DisqusPost> allPosts = new HashMap<Long, DisqusPost>();

	private Map<String, DisqusApplication> applications = new HashMap<String, DisqusApplication>();

	private Map<String, DisqusUser> users = new HashMap<String, DisqusUser>();

	public StoreService() {

		//createInitialData();
	}

	public DisqusForum getForum(String forum) {
		return allForums.get(forum).forum;
	}

	public DisqusThread getThread(long threadId) {
		return allThreads.get(threadId).thread;
	}

	public DisqusPost getPost(long postId) {
		return allPosts.get(postId);
	}

	public void createForum(DisqusForum forum) {
		ForumWithThreads forumx = allForums.get(forum.getId());
		if (forumx != null) {
			throw new DisqusServerException(400, 2, "Invalid argument, 'short_name' : Forum of this name already exists");
		}
		allForums.put(forum.getId(), new ForumWithThreads(forum));
	}

	public void createThread(DisqusThread thread) {
		long threadId = threadCounter.incrementAndGet();
		thread.setId(threadId);

		ThreadWithPosts threadX = new ThreadWithPosts(thread);
		allThreads.put(threadId, threadX);
	}

	public List<DisqusThread> listThreads(String forumId) {
		ForumWithThreads forumX = allForums.get(forumId);
		Collection<DisqusThread> threads = forumX.threads.values();
		return new ArrayList<DisqusThread>(threads);
	}

	public DisqusPost createPost(DisqusPost post) {
		ThreadWithPosts threadx = allThreads.get(post.getThread());

		DisqusForum forum = (DisqusForum) threadx.thread.getForum();
		post.setForum(forum);

		long postId = postCounter.incrementAndGet();
		post.setId(postId);
		allPosts.put(postId, post);
		//this has 10+ second delay
		threadx.posts.put(postId, post);

		return post;
	}

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
	/*
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
