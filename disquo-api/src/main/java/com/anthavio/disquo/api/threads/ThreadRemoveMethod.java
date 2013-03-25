package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadRemoveMethod extends BaseMultiThreadMethod<DisqusId> {

	public ThreadRemoveMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.remove);
	}

}
