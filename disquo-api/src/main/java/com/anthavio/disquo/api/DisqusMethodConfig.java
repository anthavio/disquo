package com.anthavio.disquo.api;

import com.anthavio.disquo.api.ArgumentConfig.ArgType;

/**
 * Method configuration template
 * 
 * @author martin.vanek
 * 
 */
public class DisqusMethodConfig {

	public static enum Http {
		GET, POST;
	}

	public static enum Access {
		PUBLIC_KEY, SECRET_KEY;
	}

	public static enum Auth {
		NONE, ALLOWED, REQUIRED;
	}

	public static class ResponseConfig {

		private final Class<?> clazz;

		private final boolean multi;

		private final boolean cursor;

		private final OutputFormat[] formats;

		private ResponseConfig(Class<?> clazz, OutputFormat[] formats, boolean multi, boolean cursor) {
			this.clazz = clazz;
			this.formats = formats;
			this.multi = multi;
			this.cursor = cursor;
		}

		public Class<?> getClazz() {
			return this.clazz;
		}

		public OutputFormat[] getFormats() {
			return this.formats;
		}

		public boolean getMulti() {
			return this.multi;
		}

		public boolean getCursor() {
			return this.cursor;
		}

	}

	private final Http httpMethod;

	private final String urlFragment;

	private final Access[] accesibility;

	private final Auth authentication;

	private final ArgumentConfig[] arguments;

	private final ResponseConfig response;

	public DisqusMethodConfig(Http httpMethod, String urlFragment, Access[] accesibility, Auth authentication,
			ResponseConfig response, ArgumentConfig... arguments) {
		this.httpMethod = httpMethod;
		this.urlFragment = urlFragment;
		this.accesibility = accesibility;
		this.authentication = authentication;
		this.arguments = arguments;
		this.response = response;
	}

	public Http getHttpMethod() {
		return this.httpMethod;
	}

	public Access[] getAccesibility() {
		return this.accesibility;
	}

	public String getUrlFragment() {
		return this.urlFragment;
	}

	public Auth getAuthentication() {
		return this.authentication;
	}

	public ArgumentConfig[] getArguments() {
		return this.arguments;
	}

	public static class Analytics {
	}

	/**
	 * @author martin.vanek
	 * 
	 * http://disqus.com/api/docs/categories/
	 */
	public static class Category {

		/**
		 * XXX contrary documentation, it requires authentication
		 * 
		 * It requires user to have admin privileges on forum
		 * 
		 * When using application access_token, forum must be checked on application API Settings tab (http://disqus.com/api/applications/1926339/update/)
		 * to "You may authorize this account to have admin access to any forum you moderate." 
		 * 
		 * Because API does not have update/delete/remove method, only way is admin GUI (http://testforumshortname.disqus.com/admin/settings/advanced/)
		 */
		public static final DisqusMethodConfig create = //
		POST("/categories/create", Auth.REQUIRED, //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name). You must be a moderator on the selected forum.
				Req("title", ArgType.STRING), //
				Opt("default", ArgType.BOOLEAN)//
		);

		public static final DisqusMethodConfig details = //
		GET("/categories/details",//
				Req("category", ArgType.LONG)// Looks up a category by ID
		);

		/**
		 * Returns a list of categories within a forum.
		 */
		public static final DisqusMethodConfig list = //
		GET("/categories/list",//
				Req("forum", ArgType.STRING, true),// Looks up a forum by ID (aka short name)
				Opt("since_id", ArgType.INTEGER),//
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("order", ArgType.ORDER)// Choices:  asc, desc
		);

		/**
		 * Returns a list of posts within a category.
		 */
		public static final DisqusMethodConfig listPosts = //
		GET("/categories/listPosts",//XXX rss
				Req("category", ArgType.LONG),// Looks up a category by ID
				Opt("since", ArgType.TIMESTAMP),// Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),// Choices: forum, thread
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("query", ArgType.STRING),//
				Opt("include", ArgType.POST_STATE, true),// Choices:  unapproved, approved, spam, deleted, flagged
				Opt("order", ArgType.ORDER)// Choices:  asc, desc
		);

		/**
		 * Returns a list of threads within a category sorted by the date created.
		 */
		public static final DisqusMethodConfig listThreads = //
		GET("/categories/listThreads",//XXX rss
				Req("category", ArgType.LONG),// Looks up a category by ID
				Opt("since", ArgType.TIMESTAMP),// Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),// Choices: forum, author
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("order", ArgType.ORDER)// Choices:  asc, desc
		);
	}

	/**
	 * @author martin.vanek
	 * 
	 * Gives access to posts (comments).
	 * 
	 * http://disqus.com/api/docs/posts/
	 */
	public static class Posts {

		public static final DisqusMethodConfig approve = //
		POST("/posts/approve", Auth.REQUIRED,//
				Req("post", ArgType.LONG, true)// Looks up a post by ID, You must be a moderator on the selected post's forum.
		);

		/**
		 * Creates a new post.
		 * 
		 * - Public access is only supported when creating comments from authenticated users.
		 * - If you are authenticating a user, you cannot pass any of the author_ parameters.
		 * - If you specify ``state``, ``ip_address`, or ``date`` you must be a moderator.
		 * - If you specify ``state`` it will skip any pre-approval validation.
		 * 
		 * Anonymous comments are allowed under two conditions:
		 * 
		 * 1. You're using legacy auth, and your secret key
		 * 2. You're using your public key, you've come from a verified referrer, you're unauthenticated,
		 * and the forum you're attempting to create the post on is listed in the applications trusted forums.
		 * 
		 * To create an anonymous comment, simply pass author_email and author_name, and optionally the author_url parameter.
		 */
		public static final DisqusMethodConfig create = //
		POST("/posts/create", Auth.ALLOWED,//
				Req("message", ArgType.STRING),//
				Opt("parent", ArgType.LONG),// Looks up a post by ID
				Req("thread", ArgType.LONG),// Looks up a thread by ID //XXX contrary documentation, it IS required
				Opt("state", ArgType.POST_STATE),// Choices: unapproved, approved, spam, killed
				Opt("author_email", ArgType.EMAIL),//
				Opt("author_name", ArgType.STRING),//
				Opt("author_url", ArgType.STRING),//
				Opt("date", ArgType.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("ip_address", ArgType.STRING)//
		);

		/**
		 * http://disqus.com/api/docs/posts/details/
		 */
		public static final DisqusMethodConfig details = //
		GET("/posts/details", //
				Req("post", ArgType.LONG),//Looks up a post by ID
				Opt("related", ArgType.RELATED, true)// Choices: forum, thread
		);

		/**
		 * Returns the hierarchal tree of a post (all parents).
		 */
		@Deprecated
		public static final DisqusMethodConfig getContext = //XXX rss
		GET("/posts/getContext", //
				Req("post", ArgType.LONG),//Looks up a post by ID
				Opt("depth", ArgType.INTEGER),// 
				Opt("related", ArgType.RELATED, true)//Choices: forum, thread
		);

		/**
		 * Highlights the requested post(s).
		 */
		public static final DisqusMethodConfig highlight = //
		POST("/posts/highlight", Auth.REQUIRED,//
				Req("post", ArgType.LONG, true)//
		);

		/**
		 * Returns a list of posts ordered by the date created.
		 */
		public static final DisqusMethodConfig list = //XXX rss
		GET("/posts/list", //
				Opt("category", ArgType.LONG, true),//Looks up a category by ID
				Opt("thread", ArgType.LONG, true),//Looks up a thread by ID
				Opt("forum", ArgType.STRING, true),//Defaults to all forums you moderate. Use :all to retrieve all forums.
				Opt("since", ArgType.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: forum, thread
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				//Opt("offset", Type.INTEGER),// Deprecated: Please see documentation on cursors
				Opt("query", ArgType.STRING),//
				Opt("include", ArgType.POST_STATE, true),//Defaults to [ "approved" ], Choices: unapproved, approved, spam, deleted, flagged, highlighted 
				Opt("order", ArgType.ORDER)// Choices:  asc, desc

		);

		/**
		 * Returns a list of posts ordered by the number of likes recently.
		 * http://disqus.com/api/docs/posts/listPopular/
		 */
		@Deprecated
		public static final DisqusMethodConfig listPopular = //XXX rss
		GET("/posts/listPopular", //
				Opt("category", ArgType.LONG),//Looks up a category by ID
				Opt("interval", ArgType.STRING),//Defaults to "7d", Choices: 1h, 6h, 12h, 1d, 3d, 7d, 30d, 60d, 90d
				Opt("thread", ArgType.LONG, true),//Looks up a thread by ID
				Opt("forum", ArgType.STRING, true),//Defaults to all forums you moderate. Use :all to retrieve all forums.
				//Opt("since", Type.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: forum, thread
				//Opt("cursor", Type.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("offset", ArgType.INTEGER),// Deprecated: Please see documentation on cursors
				Opt("query", ArgType.STRING),//
				Opt("include", ArgType.POST_STATE, true),//Defaults to [ "approved" ], Choices: unapproved, approved, spam, deleted, flagged, highlighted 
				Opt("order", ArgType.ORDER)// Choices: popular, best
		);

		/**
		 * Deletes the requested post(s).
		 * http://disqus.com/api/docs/posts/remove/
		 */
		public static final DisqusMethodConfig remove = //
		POST("/posts/remove", Auth.REQUIRED,//
				Req("post", ArgType.LONG, true)//
		);

		/**
		 * Reports a post (flagging).
		 * http://disqus.com/api/docs/posts/report/
		 */
		public static final DisqusMethodConfig report = //
		POST("/posts/report", Auth.NONE,//
				Req("post", ArgType.LONG)//
		);

		/**
		 * Undeletes the requested post(s).
		 * http://disqus.com/api/docs/posts/restore/
		 */
		public static final DisqusMethodConfig restore = //
		POST("/posts/restore", Auth.REQUIRED,//
				Req("post", ArgType.LONG, true)//
		);

		/**
		 * Marks the requested post(s) as spam.
		 * http://disqus.com/api/docs/posts/spam/
		 */
		public static final DisqusMethodConfig spam = //
		POST("/posts/spam", Auth.REQUIRED,//
				Req("post", ArgType.LONG, true)//You must be a moderator on the selected post's forum.
		);

		/**
		 * Unhighlights the requested post(s).
		 * http://disqus.com/api/docs/posts/unhighlight/
		 */
		public static final DisqusMethodConfig unhighlight = //
		POST("/posts/unhighlight", Auth.REQUIRED,//
				Req("post", ArgType.LONG, true)//
		);

		/**
		 * Updates information on a post.
		 * http://disqus.com/api/docs/posts/update/
		 */
		@Deprecated
		public static final DisqusMethodConfig update = //
		POST("/posts/update", Auth.REQUIRED,//
				Req("post", ArgType.LONG),//
				Req("message", ArgType.STRING)//
		);

		/**
		 * Register a vote on a post.
		 * http://disqus.com/api/docs/posts/vote/
		 */
		public static final DisqusMethodConfig vote = //
		POST("/posts/vote", Auth.REQUIRED,//
				Req("post", ArgType.LONG),//
				Req("vote", ArgType.VOTE)//Choices: -1, 0, 1
		);
	}

	/**
	 * @author martin.vanek
	 *
	 * http://disqus.com/api/docs/users/
	 * 
	 */
	public static class Users {

		/**
		 * Updates username for the user, fails if username does not meet requirements, or is taken by another user.
		 * 
		 * XXX Contrary documentation, username is Required
		 */
		public static final DisqusMethodConfig checkUsername = //
		POST("/users/checkUsername", Auth.REQUIRED,//
				Req("username", ArgType.STRING)//Disqus username. Minimum length of 3. Maximum length of 30
		);

		/**
		 * Returns details of a user.
		 * 
		 * Get the details of the authenticated user if user paremeter is not sent.
		 */
		public static final DisqusMethodConfig details = //
		GET("/users/details", Auth.ALLOWED,//
				Opt("user", ArgType.ID_USER)// Looks up a user by ID. You may look up a user by username using the 'username' query type.
		);

		/**
		 * Follow a user.
		 */
		public static final DisqusMethodConfig follow = //
		POST("/users/follow", Auth.REQUIRED,//
				Req("target", ArgType.ID_USER)// Looks up a user by ID. You may look up a user by username using the 'username' query type.
		);

		/**
		 * Returns a list of forums a user has been active on.
		 */
		public static final DisqusMethodConfig listActiveForums = //
		GET("/users/listActiveForums", Auth.ALLOWED, //
				Opt("since_id", ArgType.STRING),//
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("user", ArgType.ID_USER),//Looks up a user by ID. You may look up a user by username using the 'username' query type.
				Opt("order", ArgType.ORDER)//Choices:  asc, desc
		);

		/**
		 * Returns a list of forums a user owns.
		 * 
		 * http://disqus.com/api/docs/users/listForums/
		 */
		public static final DisqusMethodConfig listForums = //
		GET("/users/listForums", Auth.ALLOWED,//
				Opt("user", ArgType.ID_USER),//Looks up a user by ID. You may look up a user by username using the 'username' query type.
				Opt("since_id", ArgType.STRING),//
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("order", ArgType.ORDER)//Choices:  asc, desc
		);

		/**
		 * Returns a list of posts made by the user.
		 * 
		 * http://disqus.com/api/docs/users/listPosts/
		 */
		public static final DisqusMethodConfig listPosts = //
		GET("/users/listPosts", Auth.ALLOWED,//
				Opt("user", ArgType.ID_USER),//Looks up a user by ID. You may look up a user by username using the 'username' query type.
				Opt("since", ArgType.STRING),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: forum, thread
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("include", ArgType.POST_STATE, true),//Choices: unapproved, approved, spam, deleted, flagged, highlighted
				Opt("order", ArgType.ORDER)//Choices:  asc, desc
		);

		/**
		 * Unfollow a user.
		 * 
		 * http://disqus.com/api/docs/users/unfollow/
		 */
		public static final DisqusMethodConfig unfollow = //
		POST("/users/unfollow", Auth.REQUIRED,//
				Req("target", ArgType.ID_USER)// Looks up a user by ID. You may look up a user by username using the 'username' query type.
		);
	}

	/**
	 * @author martin.vanek
	 * 
	 * http://disqus.com/api/docs/applications/
	 */
	public static class Applications {

		/**
		 * Returns the API usage per day for this application.
		 */
		public static final DisqusMethodConfig listUsage = //
		GET("/applications/listUsage", Auth.REQUIRED,//
				Opt("application", ArgType.LONG),//
				Opt("days", ArgType.INTEGER)//Maximum value of 30
		);
	}

	public static class Discovery {

	}

	/**
	 * @author martin.vanek
	 *
	 * http://disqus.com/api/docs/exports/
	 */
	public static class Exports {

		public static final DisqusMethodConfig exportForum = //
		POST("/exports/exportForum", Auth.REQUIRED,//
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("format", ArgType.STRING)//Choices: xml
		);
	}

	/**
	 * @author martin.vanek
	 *
	 * http://disqus.com/api/docs/imports/
	 */
	public static class Imports {

		public static final DisqusMethodConfig details = //
		GET("/imports/details", Auth.REQUIRED,//
				Req("group", ArgType.INTEGER),//
				Req("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		public static final DisqusMethodConfig list = //
		GET("/imports/list", Auth.REQUIRED,//
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("cursor", ArgType.STRING)//
		);
	}

	/**
	 * @author martin.vanek
	 * 
	 * http://disqus.com/api/docs/reactions/
	 */
	public static class Reactions {

		/**
		 * Returns reaction details
		 */
		public static final DisqusMethodConfig details = //
		GET("/reactions/details",//
				Req("reaction", ArgType.LONG),//Looks up a reaction by ID
				Req("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 * Returns a list of reactions.
		 * 
		 * XXX Both POST & GET
		 */
		public static final DisqusMethodConfig list = //
		GET("/reactions/list",//
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("since_id", ArgType.INTEGER),//
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("related", ArgType.RELATED, true),//Choices: forum, thread
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		/**
		 * Removes (hides) the requested reaction(s)
		 */
		public static final DisqusMethodConfig remove = //
		GET("/reactions/remove", Auth.REQUIRED,//
				Req("reaction", ArgType.LONG, true),//Looks up a reaction by ID
				Req("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 * Restores the requested reaction(s)
		 */
		public static final DisqusMethodConfig restore = //
		GET("/reactions/restore", Auth.REQUIRED,//
				Req("reaction", ArgType.LONG, true),//Looks up a reaction by ID
				Req("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);
	}

	/**
	 * @author martin.vanek
	 *
	 * thread
	 * Looks up a thread by ID
	 * You must be a moderator on the selected thread's forum.
	 * You may pass us the 'ident' query type instead of an ID by including 'forum'.
	 * You may pass us the 'link' query type to filter by URL. You must pass the 'forum' if you do not have the Pro API Access addon.
	 * 
	 * http://disqus.com/api/docs/threads/
	 */
	public static class Threads {

		/**
		 * Closes a thread.
		 */
		public static final DisqusMethodConfig close = //
		POST("/threads/close", Auth.REQUIRED,//
				Req("thread", ArgType.ID_THREAD, true),//Looks up a thread by ID
				Opt("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 * Creates a new thread.
		 */
		public static final DisqusMethodConfig create = //
		POST("/threads/create", Auth.REQUIRED,//
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Req("title", ArgType.STRING),//
				Opt("category", ArgType.LONG),//Looks up a category by ID
				Opt("url", ArgType.STRING),//Maximum length of 500
				Opt("date", ArgType.TIMESTAMP),//
				Opt("message", ArgType.STRING),//
				Opt("identifier", ArgType.STRING),//Maximum length of 300
				Opt("slug", ArgType.STRING)//Alpha-numeric slug. Maximum length of 200
		);

		/**
		 * Returns thread details.
		 */
		public static final DisqusMethodConfig details = //
		GET("/threads/details",//
				Req("thread", ArgType.ID_THREAD),//Looks up a thread by ID. You may pass us the 'ident' or 'link' query types instead of an ID by including 'forum'.
				Opt("related", ArgType.RELATED, true), //Choices: forum, author, category
				Opt("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 * Returns a list of threads sorted by the date created.
		 */
		public static final DisqusMethodConfig list = //XXX rss
		GET("/threads/list",//
				Opt("category", ArgType.LONG, true),//Looks up a category by ID
				Opt("forum", ArgType.STRING, true),//Looks up a forum by ID (aka short name)
				Opt("thread", ArgType.ID_THREAD, true),//Looks up a thread by ID. You may pass us the 'ident' or 'link' query types instead of an ID by including 'forum'.
				Opt("author", ArgType.ID_USER, true),//Looks up a user by ID
				Opt("since", ArgType.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: forum, author, category
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("include", ArgType.THREAD_STATE, true),//Choices: open, closed, killed. Defaults to [ "open", "closed" ]
				Opt("order", ArgType.ORDER)//Choices: asc, desc

		);

		/**
		 * Returns a list of posts within a thread.
		 */
		public static final DisqusMethodConfig listPosts = //XXX rss
		GET("/threads/listPosts",//
				Req("thread", ArgType.ID_THREAD),//Looks up a thread by ID. You may pass us the 'ident' or 'link' query types instead of an ID by including 'forum'.
				Opt("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("since", ArgType.TIMESTAMP),//
				Opt("related", ArgType.RELATED, true),//Choices: forum, thread
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("query", ArgType.STRING),//
				Opt("include", ArgType.POST_STATE, true),//Choices: unapproved, approved, spam, deleted, flagged. Defaults to [ "approved" ]
				Opt("order", ArgType.ORDER)//Choices: asc, desc

		);

		/**
		 * Returns a list of threads sorted by hotness (date and likes).
		 */
		@Deprecated
		public static final DisqusMethodConfig listHot = //
		GET("/threads/listHot",//
				Opt("category", ArgType.LONG, true),//Looks up a category by ID
				Opt("forum", ArgType.STRING, true),//Looks up a forum by ID (aka short name)
				Opt("author", ArgType.ID_USER, true),//Looks up a user by ID You may look up a user by username using the 'username' query type.
				Opt("related", ArgType.RELATED, true),//Choices: forum, author, category
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("include", ArgType.THREAD_STATE, true)//Choices: open, closed, killed. Defaults to [ "open", "closed" ]
		);

		/**
		 * Returns a list of threads sorted by number of posts made since ``interval``.
		 */
		@Deprecated
		public static final DisqusMethodConfig listPopular = //
		GET("/threads/listPopular",//
				Opt("category", ArgType.LONG, true),//Looks up a category by ID
				Opt("interval", ArgType.STRING), //Defaults to "7d"	Choices: 1h, 6h, 12h, 1d, 3d, 7d, 30d, 90d
				Opt("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("related", ArgType.RELATED, true),//Choices: forum, author, category
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("with_top_post", ArgType.BOOLEAN) //Defaults to false
		);

		/**
		 * Returns a list of reactions for a given thread.
		 */
		public static final DisqusMethodConfig listReactions = //
		GET("/threads/listReactions",//
				Req("thread", ArgType.ID_THREAD),//Looks up a thread by ID. You may pass us the 'ident' or 'link' query types instead of an ID by including 'forum'.
				Opt("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT)//
		);

		/**
		 * Opens a thread.
		 */
		public static final DisqusMethodConfig open = //
		POST("/threads/open", Auth.REQUIRED,//
				Req("thread", ArgType.ID_THREAD, true),//Looks up a thread by ID
				Opt("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 * Removes a thread.
		 */
		public static final DisqusMethodConfig remove = //
		POST("/threads/remove", Auth.REQUIRED,//
				Req("thread", ArgType.ID_THREAD, true),//Looks up a thread by ID
				Opt("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 * Opens a thread.
		 */
		public static final DisqusMethodConfig restore = //
		POST("/threads/restore", Auth.REQUIRED,//
				Req("thread", ArgType.ID_THREAD, true),//Looks up a thread by ID
				Opt("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 * Returns an unsorted set of threads given a list of ids.
		 */
		public static final DisqusMethodConfig set = //
		GET("/threads/set",//
				Req("thread", ArgType.ID_THREAD, true),//Looks up a thread by ID. You may pass us the 'ident' or 'link' query types instead of an ID by including 'forum'.
				Opt("related", ArgType.RELATED, true),//Choices: forum, author, category
				Opt("forum", ArgType.STRING)//Looks up a forum by ID (aka short name)
		);

		/**
		 */
		public static final DisqusMethodConfig subscribe = //
		POST("/threads/subscribe", Auth.ALLOWED,//
				Req("thread", ArgType.ID_THREAD),//Looks up a thread by ID
				Opt("email", ArgType.EMAIL)//
		);

		/**
		 */
		public static final DisqusMethodConfig unsubscribe = //
		POST("/threads/unsubscribe", Auth.ALLOWED,//
				Req("thread", ArgType.ID_THREAD),//Looks up a thread by ID
				Opt("email", ArgType.EMAIL)//
		);

		/**
		 * Updates information on a thread.
		 */
		public static final DisqusMethodConfig update = //
		POST("/threads/update", Auth.REQUIRED,//
				Req("thread", ArgType.ID_THREAD),//You must be the author of the post or a moderator on the applicable forum.
				Opt("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("title", ArgType.STRING),//Maximum length of 200
				Opt("category", ArgType.INTEGER),//Looks up a category by ID
				Opt("url", ArgType.STRING),//Maximum length of 500
				Opt("author", ArgType.ID_USER),//You may look up a user by username using the 'username' query type.
				Opt("message", ArgType.STRING),//
				Opt("identifier", ArgType.STRING),//Maximum length of 300
				Opt("slug", ArgType.STRING)//Alpha-numeric slug. Maximum length of 200
		);

		/**
		 * Register a vote on a thread.
		 * 
		 * POST vote=1&thread:ident=my-identifier&forum=bobross
		 */
		public static final DisqusMethodConfig vote = //
		POST("/threads/vote", Auth.ALLOWED,//
				Req("vote", ArgType.VOTE),//Choices: -1, 0, 1
				Req("thread", ArgType.ID_THREAD),//Looks up a thread by ID. You may pass us the 'ident' or 'link' query types instead of an ID by including 'forum'.
				Opt("forum", ArgType.STRING)//when thread ident
		);

	}

	/**
	 * @author martin.vanek
	 *
	 * http://disqus.com/api/docs/blacklists/
	 */
	public static class Blacklists {

		/**
		 * Adds an entry to the blacklist.
		 */
		public static final DisqusMethodConfig add = //
		POST("/blacklists/add", Auth.REQUIRED,//
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("domain", ArgType.STRING, true),//Domain Name
				Opt("word", ArgType.STRING, true),//Maximum length of 200
				Opt("retroactive", ArgType.BOOLEAN),//Defaults to false
				Opt("notes", ArgType.STRING),//Defaults to "". Maximum length of 50
				Opt("ip", ArgType.STRING, true),//IP address in CIDR notation
				Opt("user", ArgType.ID_USER, true),//Looks up a user by ID. You may look up a user by username using the 'username' query type.
				Opt("email", ArgType.EMAIL, true)//
		);

		/**
		 * Returns a list of all blacklist entries.
		 */
		public static final DisqusMethodConfig list = //
		GET("/blacklists/list", Auth.REQUIRED,//
				Req("forum", ArgType.STRING, true),//Looks up a forum by ID (aka short name)
				Opt("since", ArgType.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: forum
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("since_id", ArgType.INTEGER),//
				Opt("query", ArgType.STRING),//
				Opt("type", ArgType.FILTER_TYPE, true),//Choices: domain, word, ip, user, thread_slug, email
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		/**
		 * Removes an entry to the blacklist.
		 */
		public static final DisqusMethodConfig remove = //
		POST("/blacklists/remove", Auth.REQUIRED,//
				Req("forum", ArgType.STRING, true),//Looks up a forum by ID (aka short name)
				Opt("domain", ArgType.STRING, true),//Domain Name
				Opt("word", ArgType.STRING, true),//Maximum length of 200
				Opt("ip", ArgType.STRING, true),//IP address in CIDR notation
				Opt("user", ArgType.ID_USER, true),//Looks up a user by ID. You may look up a user by username using the 'username' query type.
				Opt("email", ArgType.EMAIL, true)//
		);
	}

	/**
	 * @author martin.vanek
	 *
	 * http://disqus.com/api/docs/forums/
	 */
	public static class Forums {

		public static final DisqusMethodConfig addModerator = //
		POST("/forums/addModerator", Auth.REQUIRED,//
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).	You must be a moderator on the selected forum.
				Req("user", ArgType.ID_USER)//Looks up a user by ID. You may look up a user by username using the 'username' query type.
		);

		public static final DisqusMethodConfig create = //
		POST("/forums/create", Auth.REQUIRED,//
				Req("website", ArgType.STRING),//
				Req("name", ArgType.STRING),//
				Req("short_name", ArgType.STRING)//
		);

		public static final DisqusMethodConfig details = //
		GET("/forums/details", //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).
				Opt("related", ArgType.RELATED, true)//Choices: author
		);

		public static final DisqusMethodConfig listCategories = //
		GET("/forums/listCategories", //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).
				Opt("related", ArgType.RELATED, true),//Choices: author
				Opt("since_id", ArgType.INTEGER),//
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		public static final DisqusMethodConfig listModerators = //
		GET("/forums/listModerators", Auth.REQUIRED, //
				Req("forum", ArgType.STRING)//Looks up a forum by ID (aka short name).
		);

		public static final DisqusMethodConfig listMostActiveUsers = //
		GET("/forums/listMostActiveUsers", //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		public static final DisqusMethodConfig listMostLikedUsers = //
		GET("/forums/listMostLikedUsers", //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		public static final DisqusMethodConfig listPosts = //
		GET("/forums/listPosts", //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).
				Opt("since", ArgType.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: thread
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("query", ArgType.STRING),//
				Opt("include", ArgType.POST_STATE, true),//Choices: unapproved, approved, spam, deleted, flagged
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		public static final DisqusMethodConfig listThreads = //
		GET("/forums/listThreads", //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).
				Opt("thread", ArgType.ID_THREAD, true),//
				Opt("since", ArgType.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: forum, author
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("query", ArgType.STRING),//
				Opt("include", ArgType.THREAD_STATE, true),//Defaults to [ "open", "closed" ].	Choices: open, closed, killed
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		public static final DisqusMethodConfig listUsers = //
		GET("/forums/listUsers", //
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name).
				Opt("since_id", ArgType.INTEGER),//
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		/**
		 * Removes a moderator from a forum.
		 */
		public static final DisqusMethodConfig removeModerator = //
		POST("/forums/removeModerator", Auth.REQUIRED,//
				Req("forum", ArgType.STRING),//
				Req("moderator", ArgType.ID_USER)//Looks up a user by ID. You may look up a user by username using the 'username' query type.
		);
	}

	/**
	 * @author martin.vanek
	 * 
	 * Gives access to comment moderation reporting data.
	 * - Requires the Reports Add-on.
	 * 
	 * http://disqus.com/api/docs/reports/
	 */
	public static class Reports {

		public static final DisqusMethodConfig domains = //
		GET("/reports/domains", //
				Opt("limit", ArgType.LIMIT),//Defaults to 25. Maximum value of 100
				Opt("page", ArgType.INTEGER),//Defaults to 0
				Opt("forum", ArgType.STRING, true)//Looks up a forum by ID (aka short name). You must be a moderator on the selected forum.
		);

		public static final DisqusMethodConfig threads = //
		GET("/reports/threads", //
				Opt("limit", ArgType.LIMIT),//Defaults to 25. Maximum value of 100
				Opt("page", ArgType.INTEGER),//Defaults to 0
				Opt("forum", ArgType.STRING, true)//Looks up a forum by ID (aka short name). You must be a moderator on the selected forum.
		);

	}

	/**
	 * @author martin.vanek
	 * 
	 * http://disqus.com/api/docs/trends/
	 */
	public static class Trends {

		/**
		 * Returns a list of trending threads.
		 */
		public static final DisqusMethodConfig listThreads = //XXX rss
		GET("/trends/listThreads", //
				Opt("limit", ArgType.LIMIT),//Defaults to 10. Maximum value of 10
				Opt("related", ArgType.RELATED, true),//Choices: forum, author, category
				Opt("forum", ArgType.STRING, true)//Looks up a forum by ID (aka short name)
		);
	}

	/**
	 * @author martin.vanek
	 *
	 * http://disqus.com/api/docs/whitelists/
	 */
	public static class Whitelists {

		/**
		 * Adds an entry to the whitelist.
		 */
		public static final DisqusMethodConfig add = //
		POST("/whitelists/add", Auth.REQUIRED,//
				Req("forum", ArgType.STRING),//Looks up a forum by ID (aka short name)
				Opt("notes", ArgType.STRING),//Defaults to "". Maximum length of 50
				Opt("user", ArgType.ID_USER, true),//Looks up a user by ID. You may look up a user by username using the 'username' query type.
				Opt("email", ArgType.EMAIL, true)//
		);

		/**
		 * Returns a list of all whitelist entries.
		 */
		public static final DisqusMethodConfig list = //
		GET("/whitelists/list", Auth.REQUIRED,//
				Req("forum", ArgType.STRING, true),//Looks up a forum by ID (aka short name)
				Opt("since", ArgType.TIMESTAMP),//Unix timestamp (or ISO datetime standard)
				Opt("related", ArgType.RELATED, true),//Choices: forum
				Opt("cursor", ArgType.STRING),//
				Opt("limit", ArgType.LIMIT),// Defaults to 25, Maximum value of 100
				Opt("since_id", ArgType.INTEGER),//
				Opt("query", ArgType.STRING),//
				Opt("type", ArgType.FILTER_TYPE, true),//Choices: email, user
				Opt("order", ArgType.ORDER)//Choices: asc, desc
		);

		/**
		 * Removes an entry to the whitelist.
		 */
		public static final DisqusMethodConfig remove = //
		POST("/whitelists/remove", Auth.REQUIRED,//
				Req("forum", ArgType.STRING, true),//Looks up a forum by ID (aka short name)
				Opt("domain", ArgType.STRING, true),//Domain Name
				Opt("user", ArgType.ID_USER, true),//Looks up a user by ID. You may look up a user by username using the 'username' query type.
				Opt("email", ArgType.EMAIL, true)//
		);
	}

	public static final OutputFormat[] JSON_JSONP = new OutputFormat[] { OutputFormat.json, OutputFormat.jsonp };

	public static final OutputFormat[] JSON_JSONP_RSS = new OutputFormat[] { OutputFormat.json, OutputFormat.jsonp,
			OutputFormat.rss };

	public static final Access[] PUBLIC_SECRET = new Access[] { Access.PUBLIC_KEY, Access.SECRET_KEY };

	/**
	 * POST
	 */
	private static DisqusMethodConfig POST(String urlFragment, Auth authentication, ArgumentConfig... arguments) {
		return Method(Http.POST, urlFragment, JSON_JSONP, PUBLIC_SECRET, authentication, arguments);
	}

	/**
	 * POST anonymous
	 */
	private static DisqusMethodConfig POST(String urlFragment, ArgumentConfig... arguments) {
		return POST(urlFragment, Auth.NONE, arguments);
	}

	/**
	 * GET
	 */
	private static DisqusMethodConfig GET(String urlFragment, Auth authentication, ArgumentConfig... arguments) {
		return Method(Http.GET, urlFragment, JSON_JSONP, PUBLIC_SECRET, authentication, arguments);
	}

	/**
	 * GET anonymous
	 */
	private static DisqusMethodConfig GET(String urlFragment, ArgumentConfig... arguments) {
		return GET(urlFragment, Auth.NONE, arguments);
	}

	private static DisqusMethodConfig Method(Http httpMethod, String urlFragment, OutputFormat[] outputFormats,
			Access[] accesibility, Auth authentication, ArgumentConfig... arguments) {
		//FIXME propagate to method configuartion
		Class responseClazz = Object.class;
		ResponseConfig response = new ResponseConfig(responseClazz, outputFormats, true, true);
		//add keys and authentication parameters
		int alen = arguments.length;
		ArgumentConfig[] argsPlus = new ArgumentConfig[alen + 4];
		System.arraycopy(arguments, 0, argsPlus, 0, alen);
		argsPlus[alen] = ArgumentConfig.API_KEY;
		argsPlus[alen + 1] = ArgumentConfig.SECRET_KEY;
		argsPlus[alen + 2] = ArgumentConfig.ACCESS_TOKEN;
		argsPlus[alen + 3] = ArgumentConfig.REMOTE_AUTH;
		return new DisqusMethodConfig(httpMethod, urlFragment, accesibility, authentication, response, argsPlus);
	}

	/**
	 * Optional single value argument
	 */
	private static ArgumentConfig Opt(String name, ArgType<?, ?> type) {
		return Arg(name, type, false, false);
	}

	/**
	 * Optional multi value argument
	 */
	private static ArgumentConfig Opt(String name, ArgType<?, ?> type, boolean multi) {
		return Arg(name, type, false, multi);
	}

	/**
	 * Required single value argument
	 */
	private static ArgumentConfig Req(String name, ArgType<?, ?> type) {
		return Arg(name, type, true, false);
	}

	/**
	 * Required multi value argument
	 */
	private static ArgumentConfig Req(String name, ArgType<?, ?> type, boolean multi) {
		return Arg(name, type, true, multi);
	}

	private static ArgumentConfig Arg(String name, ArgType<?, ?> type, boolean required, boolean multi) {
		return new ArgumentConfig(name, type, required, multi);
	}
}
