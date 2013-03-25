package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorThreadsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QThread;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadListMethod extends DisqusCursorThreadsMethod<DisqusThread> {

	public ThreadListMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.list);
	}

	public ThreadListMethod addCategory(long... category) {
		addParam("category", category);
		return this;
	}

	public ThreadListMethod addThread(QThread... thread) {
		addParam("thread", thread);
		return this;
	}

	public ThreadListMethod addForum(String... forum) {
		addParam("forum", forum);
		return this;
	}

	public ThreadListMethod addAuthor(QUser... author) {
		addParam("author", author);
		return this;
	}

	public ThreadListMethod addThread(long thread) {
		addParam("thread", thread);
		return this;
	}

}
