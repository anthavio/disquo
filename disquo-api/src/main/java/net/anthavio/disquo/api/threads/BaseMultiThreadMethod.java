package net.anthavio.disquo.api.threads;

import java.util.List;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QThread;


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
