package com.anthavio.disquo.api.threads;

import java.util.List;

import com.anthavio.disquo.api.Disqus;
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
public abstract class BaseMultiThreadMethod<T> extends DisqusMethod<List<T>> {

	public BaseMultiThreadMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public BaseMultiThreadMethod<T> setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public BaseMultiThreadMethod<T> addThread(QThread... thread) {
		addParam("thread", thread);
		return this;
	}

	public BaseMultiThreadMethod<T> addThread(long... thread) {
		addParam("thread", thread);
		return this;
	}

}
