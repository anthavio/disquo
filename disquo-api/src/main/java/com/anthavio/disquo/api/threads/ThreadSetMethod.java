package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * @author martin.vanek
 * 
 * Returns an unsorted set of threads given a list of ids.
 *
 */
public class ThreadSetMethod extends BaseMultiThreadMethod<ThreadSetMethod, DisqusThread> {

	public ThreadSetMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.set);
	}

	public ThreadSetMethod addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	@Override
	protected ThreadSetMethod getB() {
		return this;
	}

}
