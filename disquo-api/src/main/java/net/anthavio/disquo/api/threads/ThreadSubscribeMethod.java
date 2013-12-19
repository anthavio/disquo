package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadSubscribeMethod extends BaseSingleThreadMethod<ThreadSubscribeMethod, Object> {

	public ThreadSubscribeMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.subscribe);
	}

	public ThreadSubscribeMethod setEmail(String email) {
		addParam("email", email);
		return this;
	}

	@Override
	protected ThreadSubscribeMethod getB() {
		return this;
	}

}
