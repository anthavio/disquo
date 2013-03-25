package com.anthavio.disquo.api.model;

import java.io.Serializable;
import java.util.List;

import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * @author martin.vanek
 * 
 */
public class DisqusThreadAndPosts implements Serializable {

	public static final DisqusThreadAndPosts ERROR = new DisqusThreadAndPosts(
			true, false);
	public static final DisqusThreadAndPosts DISQUS_DISABLED = new DisqusThreadAndPosts(
			false, true);

	private static final long serialVersionUID = 1L;

	private final DisqusThread thread;

	private final DisqusResponse<List<DisqusPost>> postsResponse;

	private final boolean error;
	private final boolean disqusDisabled;

	public DisqusThreadAndPosts(DisqusThread thread,
			DisqusResponse<List<DisqusPost>> postsResponse) {
		this.thread = thread;
		this.postsResponse = postsResponse;
		this.error = false;
		this.disqusDisabled = false;
	}

	private DisqusThreadAndPosts(boolean error, boolean disqusDisabled) {
		this.thread = null;
		this.postsResponse = null;
		this.error = error;
		this.disqusDisabled = disqusDisabled;
	}

	public DisqusThread getThread() {
		return this.thread;
	}

	public DisqusResponse<List<DisqusPost>> getPostsResponse() {
		return this.postsResponse;
	}

	/**
	 * @return true on error.
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @return true if disqusDisabled
	 */
	public boolean isDisqusDisabled() {
		return disqusDisabled;
	}

}
