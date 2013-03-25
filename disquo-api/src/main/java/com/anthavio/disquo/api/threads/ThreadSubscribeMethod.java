package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadSubscribeMethod extends BaseSingleThreadMethod<Object> {

	public ThreadSubscribeMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.subscribe);
	}

	public ThreadSubscribeMethod setEmail(String email) {
		addParam("email", email);
		return this;
	}

}
