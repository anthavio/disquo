package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;


/**
 * 
 * @author martin.vanek
 *
 */
public abstract class DisqusCursorThreadsMethod<B extends DisqusMethod<?, List<T>>, T> extends DisqusCursorMethod<B, T> {

	public DisqusCursorThreadsMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setSince(Date since) {
		addParam("since", since);
		return getB();
	}

	public B addRelated(Related... related) {
		addParam("related", related);
		return getB();
	}

	public B addInclude(ThreadState... include) {
		addParam("include", include);
		return getB();
	}

}
