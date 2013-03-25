package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QThread;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseSingleThreadMethod<T> extends DisqusMethod<T> {

	public BaseSingleThreadMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public BaseSingleThreadMethod<T> setThread(QThread thread) {
		addParam("thread", thread);
		return this;
	}

	public BaseSingleThreadMethod<T> setThread(long thread) {
		return setThread(QThread.build(thread));
	}

	public BaseSingleThreadMethod<T> setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}
