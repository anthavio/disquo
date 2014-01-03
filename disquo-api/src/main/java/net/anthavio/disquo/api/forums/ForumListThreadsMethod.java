package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorThreadsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QThread;
import net.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListThreadsMethod extends DisqusCursorThreadsMethod<ForumListThreadsMethod, DisqusThread> {

	public ForumListThreadsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listThreads);
	}

	public ForumListThreadsMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public ForumListThreadsMethod addThread(QThread... thread) {
		addParam("thread", thread);
		return this;
	}

	@Override
	protected ForumListThreadsMethod getSelf() {
		return this;
	}

}
