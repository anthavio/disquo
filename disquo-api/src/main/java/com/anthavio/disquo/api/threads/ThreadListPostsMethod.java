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
public class ThreadListPostsMethod extends DisqusCursorPostsMethod<ThreadListPostsMethod, DisqusPost> {

	public ThreadListPostsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.listPosts);
	}

	public ThreadListPostsMethod setThread(QThread thread) {
		addParam("thread", thread);
		return this;
	}

	public ThreadListPostsMethod setThread(int thread) {
		this.setThread(QThread.build(thread));
		return this;
	}

	public ThreadListPostsMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ThreadListPostsMethod getB() {
		return this;
	}

}
