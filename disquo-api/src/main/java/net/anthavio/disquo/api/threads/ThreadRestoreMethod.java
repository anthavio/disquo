package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadRestoreMethod extends BaseMultiThreadMethod<ThreadRestoreMethod, DisqusId> {

	public ThreadRestoreMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.restore);
	}

	@Override
	protected ThreadRestoreMethod getSelf() {
		return this;
	}

}
