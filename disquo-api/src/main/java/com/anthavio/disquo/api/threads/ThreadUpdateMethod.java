package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadUpdateMethod extends BaseSingleThreadMethod<ThreadUpdateMethod, DisqusThread> {

	public ThreadUpdateMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.update);
	}

	public ThreadUpdateMethod setCategory(long category) {
		addParam("category", category);
		return this;
	}

	public ThreadUpdateMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public ThreadUpdateMethod setTitle(String title) {
		addParam("title", title);
		return this;
	}

	public ThreadUpdateMethod setUrl(String url) {
		addParam("url", url);
		return this;
	}

	public ThreadUpdateMethod setAuthor(long author) {
		addParam("author", author);
		return this;
	}

	public ThreadUpdateMethod setMessage(String message) {
		addParam("message", message);
		return this;
	}

	public ThreadUpdateMethod setIdentifier(String identifier) {
		addParam("identifier", identifier);
		return this;
	}

	public ThreadUpdateMethod setSlug(String slug) {
		addParam("slug", slug);
		return this;
	}

	@Override
	protected ThreadUpdateMethod getB() {
		return this;
	}

}
