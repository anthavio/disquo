package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
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
