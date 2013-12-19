package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;

/**
 * Sinlge input, Single output
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseSinglePostMethod<B extends DisqusMethod<?, T>, T> extends DisqusMethod<B, T> {

	public BaseSinglePostMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setPost(long post) {
		addParam("post", post);
		return getB();
	}

}
