package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadOpenMethod extends BaseMultiThreadMethod<ThreadOpenMethod, DisqusId> {

	public ThreadOpenMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.open);
	}

	@Override
	protected ThreadOpenMethod getSelf() {
		return this;
	}

}
