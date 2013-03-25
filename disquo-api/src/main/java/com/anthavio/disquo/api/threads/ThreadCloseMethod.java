package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadCloseMethod extends BaseMultiThreadMethod<DisqusId> {

	public ThreadCloseMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.close);
	}

}
