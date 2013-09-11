package com.anthavio.disquo.api.threads;

import java.io.Serializable;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorPostsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.threads.ThreadListPopularMethod.DisqusThreadWithPostsInInterval;

/**
 * 
 * Returns a list of threads sorted by number of posts made since ``interval``.
 * 
 * @author martin.vanek
 * 
 */
public class ThreadListPopularMethod extends
		DisqusCursorPostsMethod<ThreadListPopularMethod, DisqusThreadWithPostsInInterval> {

	public ThreadListPopularMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.listPopular);
	}

	public ThreadListPopularMethod setCategory(long category) {
		addParam("category", category);
		return this;
	}

	public ThreadListPopularMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public ThreadListPopularMethod setInterval(String interval) {
		char unit = interval.charAt(interval.length() - 1);
		if (unit != 'h' && unit != 'd') {
			throw new IllegalArgumentException("Interval unit must be 'h' or 'd' " + interval);
		}
		String number = interval.substring(0, interval.length() - 1);
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException nfx) {
			throw new IllegalArgumentException("Interval length must be number " + interval);
		}
		addParam("interval", interval);
		return this;
	}

	public ThreadListPopularMethod setWithTopPost(boolean with_top_post) {
		addParam("with_top_post", with_top_post);
		return this;
	}

	@Override
	protected ThreadListPopularMethod getB() {
		return this;
	}

	public static class DisqusThreadWithPostsInInterval extends DisqusThread implements Serializable {

		private static final long serialVersionUID = 1L;
		private Integer postsInInterval;

		public Integer getPostsInInterval() {
			return this.postsInInterval;
		}

		public void setPostsInInterval(Integer postsInInterval) {
			this.postsInInterval = postsInInterval;
		}

	}

}
