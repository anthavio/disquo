package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorPostsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QThread;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadListReactionsMethod extends DisqusCursorPostsMethod<ThreadListReactionsMethod, DisqusPost> {

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

	@Override
	protected ThreadListReactionsMethod getB() {
		return this;
	}

}
