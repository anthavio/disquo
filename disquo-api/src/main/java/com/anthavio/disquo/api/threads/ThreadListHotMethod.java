package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorPostsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * Returns a list of threads sorted by hotness (date and likes).
 * 
 * @author martin.vanek
 *
 */
public class ThreadListHotMethod extends DisqusCursorPostsMethod<ThreadListHotMethod, DisqusThread> {

	public ThreadListHotMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.listHot);
	}

	public ThreadListHotMethod addCategory(long... category) {
		addParam("category", category);
		return this;
	}

	public ThreadListHotMethod addForum(String... forum) {
		addParam("forum", forum);
		return this;
	}

	public ThreadListHotMethod addAuthor(String... author) {
		addParam("author", author);
		return this;
	}

	@Override
	protected ThreadListHotMethod getB() {
		return this;
	}

}
