package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadUnsubscribeMethod extends BaseSingleThreadMethod<ThreadUnsubscribeMethod, Object> {

	public ThreadUnsubscribeMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.unsubscribe);
	}

	public ThreadUnsubscribeMethod setEmail(String email) {
		addParam("email", email);
		return this;
	}

	@Override
	protected ThreadUnsubscribeMethod getB() {
		return this;
	}

}
