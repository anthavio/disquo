package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorThreadsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QThread;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadListMethod extends DisqusCursorThreadsMethod<ThreadListMethod, DisqusThread> {

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

	@Override
	protected ThreadListMethod getB() {
		return this;
	}

}
