package net.anthavio.disquo.api.posts;

import java.util.Date;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.response.DisqusPost;


/**
 * Creates a new post.

- Public access is only supported when creating comments from authenticated users.
- If you are authenticating a user, you cannot pass any of the author_ parameters.
- If you specify ``state``, ``ip_address`, or ``date`` you must be a moderator.
- If you specify ``state`` it will skip any pre-approval validation.

Anonymous comments are allowed under two conditions:

1. You're using legacy auth, and your secret key
2. You're using your public key, you've come from a verified referrer, you're unauthenticated,
and the forum you're attempting to create the post on is listed in the applications trusted forums.

To create an anonymous comment, simply pass author_email and author_name, and optionally
the author_url parameter.

 * @author martin.vanek
 *
 */
public class PostCreateMethod extends DisqusMethod<PostCreateMethod, DisqusPost> {

	public PostCreateMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.create);
	}

	public PostCreateMethod setMessage(String message) {
		addParam("message", message);
		return this;
	}

	public PostCreateMethod setParent(long parent) {
		addParam("parent", parent);
		return this;
	}

	public PostCreateMethod setThread(long thread) {
		addParam("thread", thread);
		return this;
	}

	public PostCreateMethod setState(PostState state) {
		addParam("state", state);
		return this;
	}

	public PostCreateMethod setDate(Date date) {
		addParam("date", date);
		return this;
	}

	public PostCreateMethod setIpAddress(String ip_address) {
		addParam("ip_address", ip_address);
		return this;
	}

	public PostCreateMethod setAuthor(AnonymousData anonymous) {
		addParam("author_name", anonymous.getUsername());
		addParam("author_email", anonymous.getEmail());
		return this;
	}

	public PostCreateMethod setAuthorEmail(String author_email) {
		addParam("author_email", author_email);
		return this;
	}

	public PostCreateMethod setAuthorName(String author_name) {
		addParam("author_name", author_name);
		return this;
	}

	public PostCreateMethod setAuthorUrl(String author_url) {
		addParam("author_url", author_url);
		return this;
	}

	public static class AnonymousData {

		private String username;

		private String email;

		public AnonymousData(String username, String email) {
			this.username = username;
			this.email = email;
		}

		protected AnonymousData() {
			//
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

	@Override
	protected PostCreateMethod getSelf() {
		return this;
	}

}
