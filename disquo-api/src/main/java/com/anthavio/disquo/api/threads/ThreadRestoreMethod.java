package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadRestoreMethod extends BaseMultiThreadMethod<DisqusId> {

	public ThreadRestoreMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.restore);
	}

}
