package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseForumMethod<B extends DisqusMethod<?, T>, T> extends DisqusMethod<B, T> {

	public BaseForumMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setForum(String forum) {
		addParam("forum", forum);
		return getSelf();
	}

}
