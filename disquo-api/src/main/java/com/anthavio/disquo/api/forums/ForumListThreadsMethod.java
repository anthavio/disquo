package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorThreadsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QThread;
import com.anthavio.disquo.api.response.DisqusThread;

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
	protected ForumListThreadsMethod getB() {
		return this;
	}

}
