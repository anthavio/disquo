package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadCloseMethod extends BaseMultiThreadMethod<ThreadCloseMethod, DisqusId> {

	public ThreadCloseMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.close);
	}

	@Override
	protected ThreadCloseMethod getB() {
		return this;
	}

}
