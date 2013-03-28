package com.anthavio.disquo.api.threads;

import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorMethod;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QThread;

/**
 * 
 * When QThread String ident is used, then forum shortname must be provided as well
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseMultiThreadMethod<B extends DisqusMethod<?, List<T>>, T> extends DisqusCursorMethod<B, T> {

	public BaseMultiThreadMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setForum(String forum) {
		addParam("forum", forum);
		return getB();
	}

	public B addThread(QThread... thread) {
		addParam("thread", thread);
		return getB();
	}

	public B addThread(long... thread) {
		addParam("thread", thread);
		return getB();
	}

}
