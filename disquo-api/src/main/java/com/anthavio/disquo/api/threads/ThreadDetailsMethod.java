package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusThread;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadDetailsMethod extends BaseSingleThreadMethod<ThreadDetailsMethod, DisqusThread> {

	public ThreadDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.details);
	}

	public ThreadDetailsMethod addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	@Override
	protected ThreadDetailsMethod getB() {
		return this;
	}

}
