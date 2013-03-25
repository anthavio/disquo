package com.anthavio.disquo.api;

import java.util.Date;

import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.ArgumentConfig.ThreadState;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class DisqusCursorThreadsMethod<T> extends DisqusCursorMethod<T> {

	public DisqusCursorThreadsMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public DisqusCursorThreadsMethod<T> setSince(Date since) {
		addParam("since", since);
		return this;
	}

	public DisqusCursorThreadsMethod<T> addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	public DisqusCursorThreadsMethod<T> addInclude(ThreadState... include) {
		addParam("include", include);
		return this;
	}

}
