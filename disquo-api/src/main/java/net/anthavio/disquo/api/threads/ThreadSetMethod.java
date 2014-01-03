package net.anthavio.disquo.api.threads;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusThread;

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
	protected ThreadSetMethod getSelf() {
		return this;
	}

}
