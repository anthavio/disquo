package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadRemoveMethod extends BaseMultiThreadMethod<ThreadRemoveMethod, DisqusId> {

	public ThreadRemoveMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.remove);
	}

	@Override
	protected ThreadRemoveMethod getSelf() {
		return this;
	}

}
