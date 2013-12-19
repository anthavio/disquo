package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QThread;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseSingleThreadMethod<B extends DisqusMethod<?, T>, T> extends DisqusMethod<B, T> {

	public BaseSingleThreadMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setThread(QThread thread) {
		addParam("thread", thread);
		return getB();
	}

	public B setThread(long thread) {
		return setThread(QThread.build(thread));
	}

	public B setForum(String forum) {
		addParam("forum", forum);
		return getB();
	}

}
