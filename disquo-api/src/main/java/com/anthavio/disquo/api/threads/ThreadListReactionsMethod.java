package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorPostsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QThread;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadListReactionsMethod extends DisqusCursorPostsMethod<DisqusPost> {

	public ThreadListReactionsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.listPosts);
	}

	public ThreadListReactionsMethod setThread(QThread thread) {
		addParam("thread", thread);
		return this;
	}

	public ThreadListReactionsMethod setThread(long thread) {
		this.setThread(QThread.build(thread));
		return this;
	}

	public ThreadListReactionsMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}
